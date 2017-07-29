package com.adamkoch.cards;

import com.sun.tools.javac.code.Types;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public abstract class Player {
    private static final Logger LOGGER = LogManager.getLogger(Player.class);
    protected List<Card> hand;
    private int index = 0;
    private List<Card> discardPile;
    private Iterator<Card> handIterator;
    private String name;
    private boolean theDealer;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
        discardPile = new ArrayList<>();
    }

    public Hand getHand() {
        return new Hand(hand);
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public List<Card> getDiscardPile() {
        return discardPile;
    }

    public void shuffleDiscardPileIntoHand() {
        Collections.shuffle(discardPile);
        hand = new ArrayList<>(discardPile);
        discardPile.clear();
    }

    public boolean hasAnotherCard() {
        if (handIterator == null) {
            handIterator = hand.iterator();
        }
        if (!handIterator.hasNext()) {
            final int size = getDiscardPile().size();
            if (size == 0) {
                hand.clear();
                throw new RuntimeException("player " + name + " lost");
            }
            LOGGER.info("player " + name + " ran out but has " + size + " cards in his " +
                    "discard pile");
            shuffleDiscardPileIntoHand();
            handIterator = hand.iterator();
        }
        return handIterator.hasNext();
    }

    public Card getNextCard() {
        if (!hasAnotherCard()) {
            throw new RuntimeException("ran out of cards");
        }
        final Card card = handIterator.next();
        handIterator.remove();
        return card;
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
        return name + ": " + hand;
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
        List<Card> cards = new ArrayList<>();

        for (Card card : playableCards) {
            if (playerHand.contains(card)) {
                cards.add(card);
            }
        }

        return cards;
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


}
