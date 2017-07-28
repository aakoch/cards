package com.adamkoch.cards;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-27.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Stats {
    private int numberOfPlayersLeft;
    private ShoveItPlayer overallWinner;

    public int getNumberOfPlayersLeft() {
        return numberOfPlayersLeft;
    }

    public void setNumberOfPlayersLeft(int numberOfPlayersLeft) {
        this.numberOfPlayersLeft = numberOfPlayersLeft;
    }

    public void setOverallWinner(ShoveItPlayer overallWinner) {
        this.overallWinner = overallWinner;
    }

    public ShoveItPlayer getOverallWinner() {
        return overallWinner;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "overallWinner=" + overallWinner +
                '}';
    }
}