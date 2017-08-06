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
    JOKER(Suit.NONE, Rank.JOKER);

    private final Card card;

    SpecialsCards(Suit suit, Rank rank) {
        this.card = new Card(rank, suit);
    }

    public Card getCard() {
        return card;
    }
}
