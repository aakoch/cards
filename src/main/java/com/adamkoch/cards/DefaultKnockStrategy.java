package com.adamkoch.cards;

import com.adamkoch.cards.utils.CardUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-08-08.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class DefaultKnockStrategy implements KnockStrategy {
    private static final Logger LOGGER = LogManager.getLogger(DefaultKnockStrategy.class);

    @Override
    public boolean shouldKnock(Player player, GameContext gameContext) {
        final List<Card> cards = player.getHand();
        final int total = Calculator.totalCards(cards);
        final int rounds = Math.round(gameContext.getNumberOfPlays() / gameContext.getNumberOfPlayersStillInGame());
        final int currentKnockLimit = player.getKnockLimit() + rounds;
        final boolean totalGreater = total > currentKnockLimit;
        boolean decidesToKnock = CardUtil.areThreeCardsWithSameSuit(cards) && totalGreater;
        if (decidesToKnock) {
            LOGGER.debug("after " + rounds + " rounds, " + player.getName() + " has " + total +
                    " points, which is greater than their limit of " + currentKnockLimit + ", and decides to knock");
        }
        return decidesToKnock;
    }
}
