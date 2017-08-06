package com.adamkoch.cards;

import java.util.ArrayList;
import java.util.List;

/**
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
            for (Rank rank : Rank.standardRanks()) {
                Card card = new Card(rank, suit);
                cards.add(card);
            }
        }

        return cards;
    }

}
