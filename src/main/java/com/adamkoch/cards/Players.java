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
public class Players {
    private final List<Player> players;

    public Players(List<Player> players) {
        this.players = players;
    }

    public Player pickRandomPlayer() {
        return players.get((new Random()).nextInt(players.size()));
    }

    public List<Player> list() {
        return players;
    }

    public int size() {
        return players.size();
    }
}
