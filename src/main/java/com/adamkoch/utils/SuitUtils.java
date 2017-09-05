package com.adamkoch.utils;

import com.adamkoch.cards.Suit; /**
 * <p>Created by aakoch on 2017-09-04.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class SuitUtils {
    public static Suit getComplement(Suit suit) {
        switch (suit) {
            case CLUBS:
                return Suit.SPADES;
            case SPADES:
                return Suit.CLUBS;
            case HEARTS:
                return Suit.DIAMONDS;
            case DIAMONDS:
                return Suit.HEARTS;
        }
        return null;
    }
}
