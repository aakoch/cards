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
        Game game = GameCreater.withNPlayers(2);

        while (game.shouldContinue()) {
            final Player player = game.nextPlayer();
            final Card drawnCard = game.drawCard();
            player.addToHand(drawnCard);
            final Card cardPlayed = player.determineCardToPlay();
            LOGGER.info(player + " draws " + drawnCard + " and plays " + cardPlayed);
            final Optional<Player> opponent = player.chooseOpponent();
            final Outcome outcome = player.performsAction(game);
            LOGGER.info((opponent.isPresent() ? opponent.get() : "No one") + " is attacked and outcome is " + outcome);
        }

        LOGGER.info("Winner is " + game.getWinner());
    }
}
