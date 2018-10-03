package com.adamkoch.cards;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * I've been going back and forth whether I needed Hand but the latest use case for it would be to cache calculations
 * such as the total points in a hand of 31.
 *
 * <p>Created by aakoch on 2017-07-27.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Hand<T extends ICard> {
    private final List<T> cards;

    Hand() {
        this(Collections.emptyList());
    }

    public Hand(List<T> cards) {
        this.cards = cards;
    }

    public List<T> cards() {
        return cards;
    }

    @Override
    public boolean equals(Object o) {
        return Objects.equals(o, this);
    }

    @Override
    public int hashCode() {
        return this.cards == null ? 0 : this.cards.hashCode();
    }

    protected void addCard(T card) {
        cards.add(card);
    }
}
