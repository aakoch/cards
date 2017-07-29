package com.adamkoch.cards;

/**
 *
 * <p>Created by aakoch on 2017-07-28.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public enum StandardCard {
    ACE_OF_SPADES(Rank.ACE, Suit.SPADES);

    private final Rank rank;
    private final Suit suit;

    StandardCard(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }
}
