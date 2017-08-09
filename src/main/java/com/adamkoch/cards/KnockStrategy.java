package com.adamkoch.cards;

import java.util.List;

/**
 * <p>Created by aakoch on 2017-08-08.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public interface KnockStrategy {
    boolean shouldKnock(Player player, GameContext gameContext);
}
