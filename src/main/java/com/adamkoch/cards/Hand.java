package com.adamkoch.cards;

import java.util.List;

/**
 *
 * <p>Created by aakoch on 2017-07-27.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Hand {
    private final List<Card> cards;

    public Hand(List<Card> cards) {

        this.cards = cards;
    }

    public List<Card> cards() {
        return cards;
    }
}
