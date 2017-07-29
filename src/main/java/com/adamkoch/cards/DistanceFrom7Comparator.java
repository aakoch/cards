package com.adamkoch.cards;

import java.util.Comparator;

/**
 * <p>Created by aakoch on 2017-07-28.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class DistanceFrom7Comparator implements Comparator<Card> {
    @Override
    public int compare(Card card1, Card card2) {
        int numberOfCardsAfter1 = Math.abs(7 - card1.getRank());
        int numberOfCardsAfter2 = Math.abs(7 - card2.getRank());
        return numberOfCardsAfter2 - numberOfCardsAfter1;
    }

    @Override
    public boolean equals(Object obj) {
        return this.equals(obj);
    }
}
