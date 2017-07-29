package com.adamkoch.cards;

import java.util.Comparator;

/**
 *
 * <p>Created by aakoch on 2017-07-28.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class HighestRankComparator implements Comparator<Card> {
    @Override
    public int compare(Card card1, Card card2) {
        return card2.getRank().compareTo(card1.getRank());
    }

    @Override
    public boolean equals(Object obj) {
        return this.equals(obj);
    }
}
