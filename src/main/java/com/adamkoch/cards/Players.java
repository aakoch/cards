package com.adamkoch.cards;

import java.util.List;
import java.util.Random;

/**
 *
 * <p>Created by aakoch on 2017-07-27.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Players<T extends Player> {
    private final List<T> players;

    public Players(List<T> players) {
        this.players = players;
    }

    public T pickRandomPlayer() {
        return players.get((new Random()).nextInt(players.size()));
    }

    public List<T> list() {
        return players;
    }

    public int size() {
        return players.size();
    }
}
