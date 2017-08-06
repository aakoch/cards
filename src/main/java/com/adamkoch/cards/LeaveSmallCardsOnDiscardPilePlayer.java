package com.adamkoch.cards;

/**
 * <p>Created by aakoch on 2017-08-05.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class LeaveSmallCardsOnDiscardPilePlayer extends EasyPlayer {

    private Rank rankToLeaveOnDiscardPile = Rank.FOUR;

    public LeaveSmallCardsOnDiscardPilePlayer(String name, int knockLimit) {
        super(name, knockLimit);
    }

    @Override
    public boolean shouldTakeCardFromDiscardPile(Card card, GameContext gameContext) {
        if (card.getRank().lessThan(rankToLeaveOnDiscardPile) || card.getRank() == rankToLeaveOnDiscardPile) {
            return false;
        }
        return super.shouldTakeCardFromDiscardPile(card, gameContext);
    }
}
