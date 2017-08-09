package com.adamkoch.cards;

/**
 * <p>Created by aakoch on 2017-08-08.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public interface PickUpStrategy {

    /**
     * Should the user pick the card up from the discard pile?
     */
    boolean shouldTakeCardFromDiscardPile(Player player, Card card, GameContext gameContext);
}
