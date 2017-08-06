package com.adamkoch.cards;


import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * <p>Created by aakoch on 2017-07-28.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class LowestRankComparatorTest {

    @Test
    public void test() {
        LowestRankComparator lowestRankComparator = new LowestRankComparator();
        final Card hearts8 = new Card(Rank.EIGHT, Suit.HEARTS);
        final Card hearts6 = new Card(Rank.SIX, Suit.HEARTS);
        final Card hearts9 = new Card(Rank.NINE, Suit.HEARTS);
        List<Card> list = Arrays.asList(hearts8, hearts6, hearts9);
        Collections.sort(list, lowestRankComparator);
//        System.out.println("list = " + list);
        assertEquals(hearts6, list.get(0));
        assertEquals(hearts8, list.get(1));
        assertEquals(hearts9, list.get(2));
    }

}