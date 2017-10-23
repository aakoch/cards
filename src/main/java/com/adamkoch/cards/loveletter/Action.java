package com.adamkoch.cards.loveletter;

/**
 * <p>Created by aakoch on 2017-10-07.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public interface Action {
    Outcome resolve(Player player, Player opponent, Game game);
}
