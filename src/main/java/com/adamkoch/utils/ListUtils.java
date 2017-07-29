package com.adamkoch.utils;

import com.google.common.collect.Iterables;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
}
