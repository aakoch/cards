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
        List<Card> list = Arrays.asList(new Card(Suit.HEARTS, 8), new Card(Suit.HEARTS, 6), new Card(Suit.HEARTS,
                9) );
        Collections.sort(list, lowestRankComparator);
        System.out.println("list = " + list);
    }

}