package com.adamkoch.cards;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Created by aakoch on 2017-08-03.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class GameContext {
    private AtomicInteger numberOfPlays;
    private int numberOfPlayers;
    private boolean someoneElseHasKnocked;

    public GameContext() {
        someoneElseHasKnocked = false;
        numberOfPlays = new AtomicInteger(0);
    }

    public void incrementNumberOfPlays() {
        numberOfPlays.incrementAndGet();
    }

    public int getNumberOfPlays() {
        return numberOfPlays.get();
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public boolean someoneElseHasKnocked() {
        return someoneElseHasKnocked;
    }

    public void setSomeoneElseKnocked() {
        someoneElseHasKnocked = true;
    }
}
