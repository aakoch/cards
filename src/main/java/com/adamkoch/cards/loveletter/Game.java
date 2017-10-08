package com.adamkoch.cards.loveletter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by aakoch on 2017-10-07.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Game {
    private List<Player> players;
    private List<Play> history;

    public Game() {
        history = new ArrayList<>();
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
        players.forEach(player -> player.setGame(this));
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public void addToHistory(Player player, Card playedCard) {
        history.add(new Play(player, playedCard));
    }

    public List<Player> getPlayersStillInGame() {
        return new ArrayList<>(players);
    }

    public List<Player> getPlayersThatCanBeAttacked(Player playerGuessing) {
        final List<Player> players = getPlayersStillInGame();
        players.removeIf(player -> player.equals(playerGuessing));
        players.removeIf(player -> player.isSafe());
        return players;
    }

    private class Play {
        public Play(Player player, Card playedCard) {
        }
    }
}
