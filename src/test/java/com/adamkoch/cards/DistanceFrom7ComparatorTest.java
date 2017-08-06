package com.adamkoch.cards;

import com.adamkoch.cards.seven.DistanceFrom7Comparator;
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
        final Card hearts8 = new Card(Rank.EIGHT, Suit.HEARTS);
        final Card hearts12 = new Card(Rank.QUEEN, Suit.HEARTS);
        final Card hearts5 = new Card(Rank.FIVE, Suit.HEARTS);
        final Card hearts11 = new Card(Rank.JACK, Suit.HEARTS);
        List<Card> list = Arrays.asList(hearts8, hearts12, hearts5, hearts11);
        Collections.sort(list, distanceFrom7Comparator);
        assertEquals(hearts12, list.get(0));
        assertEquals(hearts11, list.get(1));
        assertEquals(hearts5, list.get(2));
        assertEquals(hearts8, list.get(3));
    }
}