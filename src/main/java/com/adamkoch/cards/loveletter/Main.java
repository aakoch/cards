package com.adamkoch.cards.loveletter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * <p>Created by aakoch on 2017-10-09.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        Game game = GameCreater.withNPlayers(1);

        while (game.shouldContinue()) {
            Player player = game.nextPlayer();
            player.addToHand(game.drawCard());
            final Card cardPlayed = player.determineCardToPlay();
            final Optional<Player> opponent = player.chooseOpponent();
            player.performsAction();
        }

        LOGGER.info("Winner is " + game.getWinner());
    }
}
