package com.adamkoch.cards;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-28.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class CharacterConverter {
    public static String getUnicode(Card card) {
        Suit suit = card.getSuit();
        int rankValue = card.getRank();
        int suitOffset = suit.ordinal();
        if (rankValue >= 12) {
            rankValue++;
        }
        if (rankValue == 15) {
            rankValue = 1;
        }

        return String.valueOf(Character.toChars("\uD83C\uDCA1".codePointAt(0) + (rankValue - 1) + (16 * suitOffset)));
    }
}
