package com.adamkoch.cards;

import java.util.List;

/**
 * <p>Created by aakoch on 2017-07-27.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Hand {
    private final List<Card> cards;

    Hand(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> cards() {
        return cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Hand hand = (Hand) o;

        return this.cards != null ? this.cards.equals(hand.cards) : hand.cards == null;
    }

    @Override
    public int hashCode() {
        return this.cards != null ? this.cards.hashCode() : 0;
    }
}
