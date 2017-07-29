package com.adamkoch.cards;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-28.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class DistanceFrom7ComparatorTest {

    @Test
    public void test() {
        DistanceFrom7Comparator distanceFrom7Comparator = new DistanceFrom7Comparator();
        final Card hearts8 = new Card(Suit.HEARTS, 8);
        final Card hearts12 = new Card(Suit.HEARTS, 12);
        final Card hearts5 = new Card(Suit.HEARTS, 5);
        final Card hearts11 = new Card(Suit.HEARTS, 11);
        List<Card> list = Arrays.asList(hearts8, hearts12, hearts5, hearts11);
        Collections.sort(list, distanceFrom7Comparator);
        assertEquals(hearts12, list.get(0));
        assertEquals(hearts11, list.get(1));
        assertEquals(hearts5, list.get(2));
        assertEquals(hearts8, list.get(3));
    }
}