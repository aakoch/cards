package com.adamkoch.cards.loveletter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * <p>Created by aakoch on 2017-09-20.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class PlayerIterator implements Iterator<Player> {
    private final List<Player> players;
    private int index;

    public PlayerIterator(List<Player> players, int startIndex) {
        this.players = players;
        this.index = startIndex;
    }

    public int size() {
        return players.size();
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    public Player next() {
        return players.get(nextIndex());
    }

    private int nextIndex() {
        index++;
        if (index >= players.size()) {
            index = 0;
        }
        return index;
    }

    public void remove(Player player) {
        players.remove(player);
    }

    public List<Player> getPlayerList() {
        return players;
    }
}
