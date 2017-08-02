package com.adamkoch.cards;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-30.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class DefaultPile implements Pile {
    private List<Card> cards;

    public DefaultPile() {
        cards = new ArrayList<>();
    }

    public DefaultPile(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public void add(Card card) {
        cards.add(card);
    }

    @Override
    public Card remove() {
        return cards.remove(0);
    }
}
