package com.adamkoch.utils;

import com.adamkoch.cards.Card;
import com.adamkoch.cards.Player;
import com.adamkoch.cards.loveletter.KnownHand;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

import java.util.*;
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
        return Iterators.cycle(players);
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

    /**
     * Return the next item in the list. If at the end of the list, return the first.
     */
    public static <T> T getNext(List<T> list, T item) {
        int index = list.indexOf(item);
        if (index == -1) {
            throw new NoSuchElementException(item + " is not in list " + list);
        }
        if (index >= list.size() - 1) {
            index = -1;
        }
        return list.get(index + 1);
    }

    public static <T> T last(List<T> list) {
        if (list == null || list.isEmpty())
        return null;
        return list.get(list.size() - 1);
    }
}
