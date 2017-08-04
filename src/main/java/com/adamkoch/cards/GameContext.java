package com.adamkoch.cards;

/**
 * <p>Created by aakoch on 2017-08-03.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class GameContext {
    private int numberOfPlays = 0;
    private int numberOfPlayers;

    public void incrementNumberOfPlays() {
        numberOfPlays++;
    }

    public int getNumberOfPlays() {
        return numberOfPlays;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
