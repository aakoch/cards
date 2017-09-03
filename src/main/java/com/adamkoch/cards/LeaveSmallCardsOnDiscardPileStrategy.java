package com.adamkoch.cards;

/**
 * <p>Created by aakoch on 2017-08-05.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class LeaveSmallCardsOnDiscardPileStrategy implements PickUpStrategy {

    private Rank rankToLeaveOnDiscardPile;

    private PickUpStrategy defaultPickupStrategy;

    public LeaveSmallCardsOnDiscardPileStrategy(PickUpStrategy defaultPickupStrategy) {
        this(defaultPickupStrategy, Rank.FOUR);
    }

    public LeaveSmallCardsOnDiscardPileStrategy(PickUpStrategy defaultPickupStrategy, Rank rankToLeaveOnDiscardPile) {
        this.defaultPickupStrategy = defaultPickupStrategy;
        this.rankToLeaveOnDiscardPile = rankToLeaveOnDiscardPile;
    }

    @Override
    public boolean shouldTakeCardFromDiscardPile(Player player, Card card, GameContext gameContext) {
        if (card.getRank().lessThan(rankToLeaveOnDiscardPile) || card.getRank() == rankToLeaveOnDiscardPile) {
            return false;
        }
        return defaultPickupStrategy.shouldTakeCardFromDiscardPile(player, card, gameContext);
    }
}
