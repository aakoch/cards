package com.adamkoch.cards;

/**
 *
 * <p>Created by aakoch on 2017-07-30.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public interface Pile {

    void add(Card card);

    Card remove();
}
