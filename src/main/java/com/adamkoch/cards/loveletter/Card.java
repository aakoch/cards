package com.adamkoch.cards.loveletter;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Created by aakoch on 2017-09-20.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public enum Card {
    GUARD,
    PRIEST,
    BARON,
    HANDMAID,
    PRINCE,
    KING,
    COUNTESS,
    PRINCESS;

    public static Set<Card> GUESSABLE = Sets.immutableEnumSet(
                                                                PRIEST,
                                                                BARON,
                                                                HANDMAID,
                                                                PRINCE,
                                                                KING,
                                                                COUNTESS,
                                                                PRINCESS);
}
