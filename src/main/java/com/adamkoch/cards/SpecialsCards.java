package com.adamkoch.cards;

/**
 *
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public enum SpecialsCards {
    //public static final Card JOKER = new Card();
    JOKER(Rank.JOKER, Suit.NONE);

    private final Card card;

    SpecialsCards(Rank rank, Suit suit) {
        this.card = new Card(suit, rank.value());
    }

    public Card getCard() {
        return card;
    }
}
