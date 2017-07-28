package com.adamkoch.cards;

import com.sun.tools.javac.code.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Player {
    protected List<Card> hand;
    private int index = 0;
    private List<Card> discardPile;
    private Iterator<Card> handIterator;
    private String name;
    private boolean theDealer;
    protected boolean swapped;
    protected Card previousCard;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
        discardPile = new ArrayList<>();
    }

    public Iterator<Card> getHand() {
        return hand.iterator();
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
                System.out.println();
                throw new RuntimeException("player " + name + " lost");
            }
            System.out.println("player " + name + " ran out but has " + size + " cards in his " +
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
        return "Player{" +
                "hand=" + hand +
                ", name='" + name + '\'' +
                ", dealer='" + theDealer + '\'' +
                ", swapped='" + swapped + '\'' +
                ", previousCard='" + previousCard + '\'' +
                '}';
    }

    public Card getCard() {
        return hand.isEmpty() ? null : hand.get(0);
    }

    public void setCard(Card card) {
        previousCard = getCard();
        hand.clear();
        hand.add(card);
        swapped = true;
    }

    public boolean wasSwapped() {
        return swapped;
    }

    public Card previousCard() {
        return previousCard;
    }

    public void clearHand() {
        hand.clear();
        reset();
    }

    public void notDealer() {
        theDealer = false;
    }

    public void reset() {
        previousCard = null;
        swapped = false;
    }
}
