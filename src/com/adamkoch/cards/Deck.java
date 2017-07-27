package com.adamkoch.cards;

import java.util.Collections;
import java.util.List;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Deck {
    private List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public void remove(Card card) {
        cards.remove(card);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public List<Card> cards() {
        return cards;
    }

    public Card next() {
        return cards.remove(0);
    }
}
