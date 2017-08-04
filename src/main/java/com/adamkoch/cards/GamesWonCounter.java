package com.adamkoch.cards;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>Created by aakoch on 2017-07-29.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class GamesWonCounter {

    private Map<Player, Integer> map = new ConcurrentHashMap<>();
    private int totalNumberOfGames;

    public void increment(Player winner) {
        totalNumberOfGames++;
        map.putIfAbsent(winner, Integer.valueOf(0));
        map.computeIfPresent(winner, (player, timesWon) -> Integer.valueOf(timesWon + 1));
    }

    @Override
    public String toString() {
        String stats = map.entrySet().stream()
                          .sorted(Comparator.comparing((Map.Entry<Player, Integer> entry) -> entry.getValue()).reversed())
                          .map(entry -> {
                              final double v = 100.0 * (double) entry.getValue() / (double) totalNumberOfGames;
                              return entry.getKey().getName() + ": " + String.format("%.2f%%", v);
                          })
                          .collect(Collectors.joining(", "));
        return stats + " -- total number of games=" + totalNumberOfGames;
    }
}
