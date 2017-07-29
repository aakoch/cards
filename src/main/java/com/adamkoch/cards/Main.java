package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        if (LOGGER.isDebugEnabled()) {
            listStandardDeck();
        }

        GamesWonCounter gamesWonCounter = new GamesWonCounter();
        Players players = new Players(PlayerFactory.initializePlayers(4));

        final int totalNumberOfGames = 1000;
        for (int i = 0; i < totalNumberOfGames; i++) {
            SevenGame game = new SevenGame(players);
            final Player winner = game.play();
            LOGGER.info(winner.getName() + " won");

            gamesWonCounter.increment(winner);

            resetPlayers(players);
        }


        LOGGER.info(gamesWonCounter);
    }

    private static void resetPlayers(Players players) {
        players.list().parallelStream().forEach(player -> player.clearHand());
    }

    private static void listStandardDeck() {
        new StandardDeck().cards().stream().forEach(card -> {LOGGER.debug(CharacterConverter.getUnicode(card));});
    }

}
