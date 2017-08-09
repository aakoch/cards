package com.adamkoch.cards;

/**
 * <p>Created by aakoch on 2017-08-08.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class DefaultPickUpStrategy implements PickUpStrategy {

    private Determiner determiner;

    public DefaultPickUpStrategy(Determiner determiner) {
        this.determiner = determiner;
    }

    @Override
    public boolean shouldTakeCardFromDiscardPile(Player player, Card card, GameContext gameContext) {
        return determiner.cardWouldImproveHand(card, player.getHand(), gameContext);
    }
}
