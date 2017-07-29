package com.adamkoch.cards;

/**
 *
 * <p>Created by aakoch on 2017-07-27.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Stats {
    private int numberOfPlayersLeft;
    private Player overallWinner;

    public int getNumberOfPlayersLeft() {
        return numberOfPlayersLeft;
    }

    public void setNumberOfPlayersLeft(int numberOfPlayersLeft) {
        this.numberOfPlayersLeft = numberOfPlayersLeft;
    }

    public void setOverallWinner(Player overallWinner) {
        this.overallWinner = overallWinner;
    }

    public Player getOverallWinner() {
        return overallWinner;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "overallWinner=" + overallWinner +
                '}';
    }
}
