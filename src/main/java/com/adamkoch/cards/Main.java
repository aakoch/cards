package com.adamkoch.cards;

import com.adamkoch.cards.utils.CharacterConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        final int numberOfPlayers = 4;
        final int startKnockLimit = 25;
        final int totalNumberOfGames = 1;
        LOGGER.info(
                "Starting " + totalNumberOfGames + " games with " + numberOfPlayers + " players and starting knock limit of " +
                        startKnockLimit);

        final List<Player> players = PlayerFactory.initializePlayers(numberOfPlayers, startKnockLimit);

        List<GameResult> gameResults = new ArrayList<>();
        for (int gameCounter = 0; gameCounter < totalNumberOfGames; gameCounter++) {
            ThirtyOneGame game = new ThirtyOneGame(new ArrayList<>(players));

            final GameResult result = game.play();
            gameResults.add(result);

            final Player winner = result.getWinner();

            LOGGER.info(winner.getName() + " won");

            resetPlayers(players);
        }

        LOGGER.info("gameResults = " + gameResults);
        SortedMap<String, Map<String, AtomicInteger>> roundsWinMap = getStringMapSortedMap(gameResults, players);

        players.sort(Comparator.comparing(Player::getName));

//        LOGGER.info(gamesWinMap);
        LOGGER.info(roundsWinMap);
        LOGGER.info(players);

        String s = new GameResultsParser(gameResults, players, roundsWinMap).getCsv();
        LOGGER.info(s);
    }

    public static SortedMap<String, Map<String, AtomicInteger>> getStringMapSortedMap(List<GameResult> gameResults, List<Player> players) {
        SortedMap<String, AtomicInteger> gamesWinMap = new TreeMap<>();
        SortedMap<String, Map<String, AtomicInteger>> roundsWinMap = new TreeMap<>();
        for (GameResult gameResult : gameResults) {

            gamesWinMap.computeIfAbsent(gameResult.getWinner().getName(), player4 -> new AtomicInteger())
                       .incrementAndGet();

            for (Player player : players) {
                Map<String, AtomicInteger> map = new HashMap<>();
                map.put("Total", new AtomicInteger());
                for (RoundEndMethod roundEndMethod : RoundEndMethod.values()) {
                    map.put(roundEndMethod.toString(), new AtomicInteger());
                }
                roundsWinMap.put(player.getName(), map);
            }

            for (Result roundResult : gameResult.getRoundResults()) {
                try {
                    List<Player> winners = roundResult.getWinners();
                    for (Player winner : winners) {
                        roundsWinMap.get(winner.getName()).get("Total").incrementAndGet();

                        roundsWinMap.get(winner.getName())
                                    .get(roundResult.getRoundEndMethod().toString())
                                    .incrementAndGet();
                    }
                }
                catch (RuntimeException e) {
                    LOGGER.error("Error looping over win map. gameResult=" + gameResult, e);
                }
            }

        }
        return roundsWinMap;
    }

    private static void resetPlayers(List<Player> players) {
        players.stream().forEach(player -> player.resetHandAndCoins());
    }

    private static void listStandardDeck() {
        new StandardDeck().cards().stream().forEach(card -> {LOGGER.debug(CharacterConverter.getUnicode(card));});
    }

}
