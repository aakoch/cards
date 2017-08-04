package com.adamkoch.cards;

import java.util.List;

/**
 * <p>Created by aakoch on 2017-08-01.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public interface Rule {

    /**
     * Return a list of discard candidates
     */
    List<Card> apply(List<Card> cards);
}
