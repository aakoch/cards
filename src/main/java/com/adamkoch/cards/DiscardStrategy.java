package com.adamkoch.cards;

/**
 * <p>Created by aakoch on 2017-08-08.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public interface DiscardStrategy {

    Card chooseWhichCardToDiscard(Player player, DrawPile drawPile, DiscardPile discardPile, GameContext gameContext);
}
