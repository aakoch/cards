package com.adamkoch.cards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-08-05.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class GameResultsParser {
    private final List<GameResult> gameResults;
    private final List<Player> players;
    private final SortedMap<String, Map<String, AtomicInteger>> roundsWinMap;

    public GameResultsParser(List<GameResult> gameResults, List<Player> players, SortedMap<String, Map<String, AtomicInteger>> roundsWinMap) {
        this.gameResults = gameResults;
        this.players = players;
        this.roundsWinMap = roundsWinMap;
    }

    public String getCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append("name,knock-limit,knock,backfired,31,unknown,wins\n");
        players.stream().forEach(player -> {
            final String name = player.getName();
            sb.append(name);
            sb.append(",");
            sb.append(player.getKnockLimit());
            sb.append(",");
            sb.append(roundsWinMap.getOrDefault(name, new HashMap<>()).getOrDefault(RoundEndMethod.KNOCK.toString(), new
                    AtomicInteger(0)));
            sb.append(",");
            sb.append(roundsWinMap.getOrDefault(name, new HashMap<>()).getOrDefault(RoundEndMethod.KNOCK_BACKFIRED.toString(), new
                    AtomicInteger(0)));
            sb.append(",");
            sb.append(roundsWinMap.getOrDefault(name, new HashMap<>()).getOrDefault(RoundEndMethod.THIRTY_ONE.toString(), new
                    AtomicInteger(0)));
            sb.append(",");
            sb.append(roundsWinMap.getOrDefault(name, new HashMap<>()).getOrDefault(RoundEndMethod.UNKNOWN.toString(), new
                    AtomicInteger(0)));
            sb.append(",");
            sb.append(roundsWinMap.getOrDefault(name, new HashMap<>()).getOrDefault("Total", new
                    AtomicInteger(0)));
            sb.append("\n");
        });
        return sb.toString();
    }
}
