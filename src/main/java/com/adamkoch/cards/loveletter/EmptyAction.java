package com.adamkoch.cards.loveletter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>Created by aakoch on 2017-10-07.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class EmptyAction implements Action {
    private static final Logger LOGGER = LogManager.getLogger(EmptyAction.class);

    @Override
    public Outcome resolve(Player player, Player opponent, Game game) {
        LOGGER.debug("Empty action does nothing");
        return Outcome.NO_EFFECT;
    }
}
