package com.adamkoch.cards;

import java.util.List;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-08-01.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public interface Rule {
    Card apply(List<Card> cards);
}
