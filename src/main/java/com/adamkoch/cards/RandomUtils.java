package com.adamkoch.cards;

import java.util.List;
import java.util.Random;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-31.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class RandomUtils {
    public static <T> T pickRandom(List<T> list) {
        Random random = new Random();
        return list.remove(random.nextInt(list.size()));
    }
}
