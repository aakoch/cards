package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * <p>Created by aakoch on 2017-08-08.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class StalemateBreakingKnockStrategy implements KnockStrategy {

    private static final Logger LOGGER = LogManager.getLogger(StalemateBreakingKnockStrategy.class);

    private KnockStrategy firstStrategy;

    public StalemateBreakingKnockStrategy(KnockStrategy firstStrategy) {
        this.firstStrategy = firstStrategy;
    }

    public boolean shouldKnock(Player player, GameContext gameContext) {

        List<Card> hand = player.getHand();
        boolean decidesToKnock = firstStrategy.shouldKnock(player, gameContext);

        if (!decidesToKnock) {
            final int total = Calculator.totalCards(hand);
            final int rounds = Math.round(gameContext.getNumberOfPlays() / gameContext.getNumberOfPlayersStillInGame());
            final int currentKnockLimit = player.getKnockLimit() + rounds;
            if (total == 30) {
                if (rounds > 100) {
                    LOGGER.info("Stalemate. after " + rounds + " rounds, " + player.getName() + " has " + total +
                            " points, which is not greater than their limit of " + currentKnockLimit + ", but decides to " +
                            "knock anyway. numberOfTimesDiscardPileShuffled=" + gameContext
                            .getNumberOfTimesDiscardPileShuffled());
                    decidesToKnock = true;
                }
                else {
                    LOGGER.info("after " + rounds + " rounds, " + player.getName() + " has " + total +
                            " points, but decides not to knock. starting knock limit=" + player.getKnockLimit() + ", " +
                            "current=" +
                            currentKnockLimit);
                }
            }
        }

        return decidesToKnock;
    }
}
