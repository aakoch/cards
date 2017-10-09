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
    private Player winner;
    private List<Player> playersStillInGame;

    public Game() {
        history = new ArrayList<>();
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
        players.forEach(player -> player.setGame(this));
        this.playersStillInGame = new ArrayList<>(players);
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public void addToHistory(Player player, Card playedCard) {
        history.add(new Play(player, playedCard));
    }

    public List<Player> getPlayersStillInGame() {
        return playersStillInGame;
    }

    public List<Player> getPlayersThatCanBeAttacked(Player playerGuessing) {
        final List<Player> players = getPlayersStillInGame();
        players.removeIf(player -> player.equals(playerGuessing));
        players.removeIf(player -> player.isSafe());
        return players;
    }

    public void removePlayer(int index) {
        playersStillInGame.remove(getPlayer(index));
    }

    public boolean shouldContinue() {
        return playersStillInGame.size() > 1;
    }

    public Player getWinner() {
        return playersStillInGame.get(0);
    }

    public void removePlayer(Player player) {
        playersStillInGame.remove(player);
    }

    private class Play {
        public Play(Player player, Card playedCard) {
        }
    }
}
