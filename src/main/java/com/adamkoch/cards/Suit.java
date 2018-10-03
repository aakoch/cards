package com.adamkoch.cards;

/**
 * I am not sure if this is the direction I want to go since there are games where the suits are ranked, such as 500.
 * Having them as an enum would restrict me because I couldn't subclass it.
 *
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author Adam A. Koch (aakoch)
 * @since 1.0.0
 */
public enum Suit implements ISuit {
    SPADES("\u2660"), HEARTS("\u2665"), DIAMONDS("\u2666"), CLUBS("\u2663"), NONE("");

    private static final Suit[] STANDARD_SUITS = {SPADES, HEARTS, DIAMONDS, CLUBS};

    private final CharSequence character;

    Suit(CharSequence character) {
        this.character = character;
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
                suit = NONE;
        }
        return suit;
    }

    @Override
    public String toString() {
        return character.toString();
    }
}
