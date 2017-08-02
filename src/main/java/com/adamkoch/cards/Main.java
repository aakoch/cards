package com.adamkoch.cards;

import com.adamkoch.cards.seven.SevenGame;
import com.adamkoch.cards.utils.CharacterConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println(LOGGER.isDebugEnabled());
//        if (LOGGER.isDebugEnabled()) {
//            listStandardDeck();
//        }

        GamesWonCounter gamesWonCounter = new GamesWonCounter();
        Players players = new Players(PlayerFactory.initializePlayers(4));

        final int totalNumberOfGames = 1;
        for (int i = 0; i < totalNumberOfGames; i++) {
            ThirtyOneGame game = new ThirtyOneGame(players);

            final Result result = game.play();

            final Player winner = result.getWinner();

            LOGGER.info(winner.getName() + " won");

            gamesWonCounter.increment(winner);

            resetPlayers(players);
        }

        LOGGER.info(gamesWonCounter);
    }

    private static void resetPlayers(Players<Player> players) {
        players.list().parallelStream().forEach(player -> player.clearHand());
    }

    private static void listStandardDeck() {
        new StandardDeck().cards().stream().forEach(card -> {LOGGER.debug(CharacterConverter.getUnicode(card));});
    }

}
