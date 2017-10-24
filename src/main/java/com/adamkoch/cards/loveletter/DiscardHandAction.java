package com.adamkoch.cards.loveletter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>Created by aakoch on 2017-10-08.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class DiscardHandAction implements Action {
    private static final Logger LOGGER = LogManager.getLogger(DiscardHandAction.class);
    @Override
    public Outcome resolve(Player player, Player opponent, Game game) {
        final Card drawnCard = game.drawCard();
//        LOGGER.info(opponent + " must discard their " + opponent.getHand() + " and draws a " + drawnCard);

        opponent.setHand(drawnCard);
        Outcome outcome = new Outcome();
        outcome.setAction(this);
        outcome.setDescription(
                opponent.getName() + " must discard their " + opponent.getHand() + " and draws a " + drawnCard);
        return outcome;
    }
}
