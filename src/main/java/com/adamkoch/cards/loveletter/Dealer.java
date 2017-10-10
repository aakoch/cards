package com.adamkoch.cards.loveletter;

import java.util.Collections;
import java.util.List;

/**
 * <p>Created by aakoch on 2017-10-09.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Dealer {
    private List<Card> deck;

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }
}
