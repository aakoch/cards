package com.adamkoch.cards;

/**""
 *
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public enum Suit {
    SPADES("\u2660"), HEARTS("\u2665"), DIAMONDS("\u2666"), CLUBS("\u2663"), NONE("");

    private static final Suit[] STANDARD_SUITS = {SPADES, HEARTS, DIAMONDS, CLUBS};

    private final String character;

    Suit(String character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return character;
    }

    public static Suit[] standardSuits() {
        return STANDARD_SUITS;
    }

    public static Suit valueOf(char c) {
        Suit suit;
        switch (c) {
            case '♣':
                suit = CLUBS;
                break;
            case '♠':
                suit = SPADES;
                break;
            case '♥':
                suit = HEARTS;
                break;
            case '♦':
                suit = DIAMONDS;
                break;
            default:
                suit = null;
        }
        return suit;
    }
}
