package com.adamkoch.cards;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * In some games, there is one player that has to play, which is the loser. Some games just have a winner. Some have
 * ties.
 *
 * <p>Created by aakoch on 2017-07-30.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Result {
    private List<Player> winners;
    private List<Player> losers;
    private RoundEndMethod roundEndMethod;
    private Player knockerLost;

    public Result() {
        winners = Collections.emptyList();
    }

    public List<Player> getLosers() {
        return losers;
    }

    public void setLosers(List<Player> losers) {
        this.losers = losers;
    }

    public List<Player> getWinners() {
        return winners;
    }

    public void setWinners(List<Player> winners) {
        this.winners = winners;
    }

    public void setRoundEndMethod(RoundEndMethod roundEndMethod) {
        this.roundEndMethod = roundEndMethod;
    }

    public RoundEndMethod getRoundEndMethod() {
        return roundEndMethod;
    }

    @Override
    public String toString() {
        return "Result{" +
                "winners=" + winners +
                ", losers=" + losers +
                ", roundEndMethod=" + roundEndMethod +
                (knockerLost == null ? "" : ", knockerLost=" + knockerLost) +
                '}';
    }

    public void setKnockerLost(Player knockerLost) {
        this.knockerLost = knockerLost;
    }

    public Player getKnockerLost() {
        return knockerLost;
    }
}
