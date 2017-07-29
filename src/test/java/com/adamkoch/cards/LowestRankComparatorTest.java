package com.adamkoch.cards;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Created by aakoch on 2017-07-28.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class LowestRankComparatorTest {

    public static void main(String[] args) {
        LowestRankComparator lowestRankComparator = new LowestRankComparator();
        final Card hearts8 = new Card(Suit.HEARTS, 8);
        final Card hearts6 = new Card(Suit.HEARTS, 6);
        final Card hearts9 = new Card(Suit.HEARTS, 9);
        List<Card> list = Arrays.asList(hearts8, hearts6, hearts9);
        Collections.sort(list, lowestRankComparator);
        System.out.println("list = " + list);
//        assertEquals(hearts6, list.get(0));
    }

}