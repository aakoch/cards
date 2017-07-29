package com.adamkoch.cards;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

//        listStandardDeck();
        Map<Player, Integer> map = new ConcurrentHashMap<>();
        Players players = new Players(PlayerFactory.initializePlayers(4));

        final int totalNumberOfGames = 1;
        for (int i = 0; i < totalNumberOfGames; i++) {

            SevenGame game = new SevenGame(players);
            final Player winner = game.play();
            System.out.println(winner.getName() + " won");

            map.putIfAbsent(winner, Integer.valueOf(0));
            map.computeIfPresent(winner, (player, timesWon) -> Integer.valueOf(timesWon + 1));

            resetPlayers(players);
        }

        String stats = map.entrySet().stream()
              .sorted(Comparator.comparing((Map.Entry<Player, Integer> entry) -> entry.getValue()) .reversed())
              .map(entry -> {
                  final double v = 100.0 * (double) entry.getValue() / (double) totalNumberOfGames;
                  return entry.getKey().getName() + ": " + String.format("%.2f%%", v);
              })
              .collect(Collectors.joining(", "));
        System.out.println(stats);
    }

    private static void resetPlayers(Players players) {
        players.list().parallelStream().forEach(player -> player.clearHand());
    }

    private static void listStandardDeck() {
        for (Card card : new StandardDeck().cards()) {
            System.out.print(CharacterConverter.getUnicode(card));
        }
    }

}
