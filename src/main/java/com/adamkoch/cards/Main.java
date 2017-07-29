package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Main {
private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        listStandardDeck();
        GamesWonCounter gamesWonCounter = new GamesWonCounter();
        Players players = new Players(PlayerFactory.initializePlayers(4));

        final int totalNumberOfGames = 10000;
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
        for (Card card : new StandardDeck().cards()) {
            LOGGER.debug(CharacterConverter.getUnicode(card));
        }
    }

}
