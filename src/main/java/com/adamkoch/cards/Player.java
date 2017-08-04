package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public abstract class Player {
    private static final Logger LOGGER = LogManager.getLogger(Player.class);
    protected List<Card> hand;
    protected String name;
    private int index = 0;
    private boolean theDealer;
    private int coinsLeft = 3;
    protected Card discardCard;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    public Hand getHand() {
        return new Hand(hand);
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }


    public int getHandSize() {
        return hand.size();
    }

    public String getName() {
        return name;
    }

    public Dealer setAsDealer(Deck deck) {
        final Dealer dealer = new Dealer(deck);
        dealer.setPlayer(this);
        theDealer = true;
        return dealer;
    }

    public boolean isDealer() {
        return theDealer;
    }

    @Override
    public String toString() {
        return name + "(" + coinsLeft + "): " + hand + "=" + total();
    }

    public Card getCard() {
        return hand.isEmpty() ? null : hand.get(0);
    }

    public void setCard(Card card) {
        hand.clear();
        hand.add(card);
    }

    public void clearHand() {
        hand.clear();
    }

    public void notDealer() {
        theDealer = false;
    }

    public Card play7OfHearts() {
        final Card card = new Card(Suit.HEARTS, Rank.SEVEN);
        hand.remove(card);
        return card;
    }

    public void removeFromHand(Card card) {
        hand.remove(card);
    }

    public Card determineCardToPlay(List<? extends Card> possiblePlays) {
        List<Card> intersection = intersect(possiblePlays, getHand().cards());
        return determineCardToPlay(intersection, getHand().cards());
    }


    private List<Card> intersect(List<? extends Card> playableCards, List<? extends Card> playerHand) {
        return playableCards.parallelStream().filter(playerHand::contains).collect(Collectors.toList());
    }

    private Card determineCardToPlay(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
        if (cardsThatCanPlay.isEmpty()) {
            return null;
        }
        else {
            final List<Card> rankedCards = rank(cardsThatCanPlay, hand);
            return rankedCards.get(0);
        }
    }

    protected abstract List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand);

    public void pay() {
        coinsLeft--;
    }

    public boolean stillInGame() {
        return coinsLeft > -1;
    }

    public int coinsLeft() {
        return coinsLeft;
    }

    public abstract Card chooseWhichCardToDiscard(DrawPile drawPile, DiscardPile discardPile);

    public void drawFromDrawPile(DrawPile drawPile, DiscardPile discardPile) {
        Card drawCard = drawPile.draw();
        LOGGER.debug(getName() + " draws card: " + drawCard);
        hand.add(drawCard);
    }

    public boolean has31() {
        Suit firstSuit = hand.get(0).getSuit();
        boolean has31 = false;
        if (hand.stream().allMatch(card -> card.getSuit() == firstSuit)) {
            if (contains2TensAndAnAce(hand)) {
                has31 = true;
            }
        }

        LOGGER.debug("name = " + name + ", hand = " + hand + ", total = " + total() + ", has31 = " + has31);
        return has31;
    }

    private boolean contains2TensAndAnAce(List<Card> hand) {
        int numberOfTens = 0;
        int numberOfAces = 0;
        for (Card card : hand) {
            switch (card.getRank()) {
                case ACE:
                    numberOfAces++;
                    break;
                case TEN:
                case JACK:
                case QUEEN:
                case KING:
                    numberOfTens++;
                    break;
            }
        }

        return numberOfAces == 1 && numberOfTens == 2;
    }

    public void resetHandAndCoins() {
        clearHand();
        coinsLeft = 3;
    }

    public int total() {
        return Calculator.totalCards(hand);
    }

    public abstract boolean decidesToKnock(GameContext gameContext);

//    public boolean chooseCardFromDiscardPile(DrawPile drawPile, DiscardPile discardPile) {
//
//        Card topDiscardedCard = discardPile.topCard();
//        if (Determiner.cardWouldImproveHand(topDiscardedCard, hand)) {
//
//            hand.add(topDiscardedCard);
//
//            final Card cardToDiscard = chooseWhichCardToDiscard(drawPile, discardPile);
//
//            final boolean equals = topDiscardedCard.equals(cardToDiscard);
//            if (!equals) {
//                LOGGER.debug(getName() + " takes discard card " + topDiscardedCard);
//                discardCard = cardToDiscard;
//            }
//            return !equals;
//        }
//
//        return false;
//    }
}
