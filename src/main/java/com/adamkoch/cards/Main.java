package com.adamkoch.cards;

import com.adamkoch.cards.utils.CharacterConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {
private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println(LOGGER.isDebugEnabled());
//        if (LOGGER.isDebugEnabled()) {
//            listStandardDeck();
//        }

//        GamesWonCounter gamesWonCounter = new GamesWonCounter();
        Players players = new Players(PlayerFactory.initializePlayers(4));

        List<GameResult> gameResults = new ArrayList<>();
        final int totalNumberOfGames = 1;
        for (int i = 0; i < totalNumberOfGames; i++) {
            ThirtyOneGame game = new ThirtyOneGame(players);

            final GameResult result = game.play();
            gameResults.add(result);

            final Player winner = result.getWinner();

            LOGGER.info(winner.getName() + " won");

//            gamesWonCounter.increment(winner);

            resetPlayers(players);
        }

//        LOGGER.info("\n" + gamesWonCounter);
        LOGGER.info("gameResults = " + gameResults);
    }

    private static void resetPlayers(Players<Player> players) {
        players.list().stream().forEach(player -> player.resetHandAndCoins());
    }

    private static void listStandardDeck() {
        new StandardDeck().cards().stream().forEach(card -> {LOGGER.debug(CharacterConverter.getUnicode(card));});
    }

}
