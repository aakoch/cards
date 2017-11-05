package com.adamkoch.cards.loveletter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>Created by aakoch on 2017-10-07.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class ShowAction implements Action {
    private static final Logger LOGGER = LogManager.getLogger(ShowAction.class);

    @Override
    public Outcome resolve(Player player, Player opponent, Game game) {
        player.isShownHand(opponent);

        Outcome outcome = new Outcome();
        outcome.setAction(this);
        outcome.setDescription(
                opponent.getName() + " shows their hand [" + opponent.getHand() + "] to " + player.getName());
        return outcome;
    }
}
