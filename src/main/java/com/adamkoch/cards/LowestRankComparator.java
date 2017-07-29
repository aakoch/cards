package com.adamkoch.cards;

import java.util.Comparator;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-28.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class LowestRankComparator implements Comparator<Card> {
    @Override
    public int compare(Card card1, Card card2) {
        // by rank? (lowest? highest?)
        return card1.getRank() - card2.getRank();
    }

    @Override
    public boolean equals(Object obj) {
        return this.equals(obj);
    }
}
