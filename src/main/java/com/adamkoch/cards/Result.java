package com.adamkoch.cards;

/**
 * In some games, there is one player that has to play, which is the loser. Some games just have a winner. Some have
 * ties.
 *
 * <p>Created by aakoch on 2017-07-30.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Result {
    private Player winner;

    public Result(Player winner) {
        this.winner = winner;
    }

    public Player getWinner() {
        return winner;
    }
}
