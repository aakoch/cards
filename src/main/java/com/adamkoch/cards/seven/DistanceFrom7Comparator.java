package com.adamkoch.cards.seven;

import com.adamkoch.cards.Card;

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
        int numberOfRanksAfter1 = Math.abs(7 - card1.getRank().getNumericRank(false));
        int numberOfRanksAfter2 = Math.abs(7 - card2.getRank().getNumericRank(false));
        return numberOfRanksAfter2 - numberOfRanksAfter1;
    }

    @Override
    public boolean equals(Object obj) {
        return this.equals(obj);
    }
}
