package com.adamkoch.cards;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Created by aakoch on 2017-08-04.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class GameResult {
    private Player winner;
    private List<Result> roundResults;

    public GameResult() {
        this.roundResults = new ArrayList<>();
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void addRound(Result result) {
        roundResults.add(result);
    }

    public List<Result> getRoundResults() {
        return roundResults;
    }

    @Override
    public String toString() {


        final String winnerName;
        if (winner == null) {
            winnerName = "null";
        }
        else
        winnerName = winner.getName();
        return "GameResult{" +
                "winner=" + winnerName +
//                ", roundResults=\n" + Joiner.on("\n").join(roundResults) +
                ",\n rounds won by knocking=" +
                roundResults.stream().filter(result -> result.getRoundEndMethod() == RoundEndMethod.KNOCK).count() +
                ",\n rounds won by 31=" +
                roundResults.stream()
                            .filter(result -> result.getRoundEndMethod() == RoundEndMethod.THIRTY_ONE)
                            .count() +
//                ",\n rounds won by unknown=" +
//                roundResults.stream().filter(result -> result.getRoundEndMethod() == RoundEndMethod.UNKNOWN).count() +
                '}';
    }
}
