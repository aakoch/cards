package com.adamkoch.cards;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-27.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Rotation<T> {
    private final T[] objects;
    private int counter;

    Rotation(T[] objects, int startIndex) {
        this.objects = objects;
        counter = startIndex;
    }

    T next() {
        if (counter == objects.length) {
            counter = 0;
        }
        return objects[counter++];
    }
}
