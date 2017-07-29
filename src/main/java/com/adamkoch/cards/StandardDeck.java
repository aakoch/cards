package com.adamkoch.cards;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class StandardDeck extends Deck {

    public StandardDeck() {
        super(createCards());
    }

    private static List<Card> createCards() {
        List<Card> cards = new ArrayList<>();
        for (Suit suit : Suit.standardSuits()) {
            for (int rank = 1; rank < 14; rank++) {
                Card card = new Card(suit, rank);
                cards.add(card);
            }
        }

        return cards;
    }

}
