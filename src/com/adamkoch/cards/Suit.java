package com.adamkoch.cards;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public enum Suit {
    HEARTS("\u2665"), DIAMONDS("\u2666"), SPADES("\u2660"), CLUBS("\u2663");

    private final String character;

    Suit(String character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return character;
    }
}
