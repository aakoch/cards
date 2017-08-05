package com.adamkoch.utils;

import com.adamkoch.cards.Card;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Created by aakoch on 2017-07-29.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class ListUtils {
    public static <T> Iterator<T> constructRotator(List<T> players, int startIndex) {
        Collections.rotate(players, startIndex);
        Iterable<T> cycle = Iterables.cycle(players);
        return cycle.iterator();
    }

    /**
     * Return the intersection of the 2 lists.
     */
    public static <T> List<T> intersect(List<? extends T> list1, List<? extends T> list2) {
        return list1.stream().filter(list2::contains).collect(Collectors.toList());
    }

    public static List<Card> concat(List<Card> cards, Card card) {
        List<Card> newList = new ArrayList<>(cards);
        newList.add(card);
        return newList;
    }
}
