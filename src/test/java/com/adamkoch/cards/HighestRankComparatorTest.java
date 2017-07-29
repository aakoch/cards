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
public class HighestRankComparatorTest {

    @Test
    public void test() {
        HighestRankComparator highestRankComparator = new HighestRankComparator();
        final Card hearts8 = new Card(Suit.HEARTS, Rank.EIGHT);
        final Card hearts6 = new Card(Suit.HEARTS, Rank.SIX);
        final Card hearts9 = new Card(Suit.HEARTS, Rank.NINE);
        List<Card> list = Arrays.asList(hearts8, hearts6, hearts9);
        Collections.sort(list, highestRankComparator);
//        System.out.println("list = " + list);
        assertEquals(hearts9, list.get(0));
        assertEquals(hearts8, list.get(1));
        assertEquals(hearts6, list.get(2));
    }

}