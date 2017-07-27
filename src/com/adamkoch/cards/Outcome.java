package com.adamkoch.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Outcome {
    public static final Outcome TIE = new Outcome((Card) null);
    private final Card card;
    private Player winner;
    private List<Player> losers;
    private Player loser;

    public Outcome(Card card) {
        this.card = card;
    }

    public Outcome(Player highestPlayer) {
        this.card = null;
        this.winner = highestPlayer;
    }

    public Card getCard() {
        return card;
    }

    @Override
    public String toString() {
        return "winner=" + card;
    }

    public Player getWinner() {
        return winner;
    }

    public String getLosers() {

        return losers.stream()
                .map(player -> "Player " + player.getName() + " with " + player.getCard())
                .collect(Collectors.joining(" and "));

    }

    public Player getLoser() {
        return winner;
    }

    public void setLoser(Player loser) {
        this.winner = loser;
    }

    public void setLosers(Set<Player> lowestPlayers) {
        this.losers = new ArrayList<>(lowestPlayers);
    }
}
