package com.adamkoch.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * <p>Created by aakoch on 2017-07-31.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class RandomUtils {
    private static Random RANDOM = new Random();

    public static <T> T removeRandom(List<T> list) {
        return list.remove(RANDOM.nextInt(list.size()));
    }

    public static <T> T getRandom(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }

    public static int nextInt(int inclusive, int exclusive) {
        return RANDOM.nextInt(exclusive - inclusive) + inclusive;
    }

    public static <T> T getRandom(Set<T> set) {
        return getRandom(new ArrayList<T>(set));
    }
}
