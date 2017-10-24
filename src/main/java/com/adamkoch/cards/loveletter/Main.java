package com.adamkoch.cards.loveletter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
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
        for (int i = 0; i < 100; i++) {
            playGame();
            LOGGER.info("");
        }
    }

    private static void playGame() {
        Game game = GameCreater.withNPlayers(2);
        List<Player> playerList = game.getPlayersStillInGame();
        int playerCounter = 1;
        StringBuilder sb = new StringBuilder();
        for (Player player : playerList) {
            sb.append("P");
            sb.append(playerCounter++);
            sb.append(":");
            sb.append(player.getHand());
            sb.append(", ");
        }
        LOGGER.info(sb.toString());


        while (game.shouldContinue()) {
            final Player player = game.nextPlayer();
            final Card hand = player.getHand();
            final Card drawnCard = game.drawCard();
            player.addToHand(drawnCard);
            final Card cardPlayed = player.determineCardToPlay();
            LOGGER.info(player.getName() + " [" + hand + "], draws " + drawnCard + " and plays " + cardPlayed);
            final Optional<Player> opponent = player.chooseOpponent();
            final Outcome outcome = player.performsAction(game);
//            LOGGER.info((opponent.isPresent() ? opponent.get().getName() : "No one") + " is attacked and outcome is " +
//                    outcome);
            LOGGER.info(outcome.getDescription());
        }

        LOGGER.info("Winner is " + game.getWinner().getName());
    }
}
