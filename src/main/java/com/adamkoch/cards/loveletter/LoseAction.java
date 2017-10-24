package com.adamkoch.cards.loveletter;

/**
 * <p>Created by aakoch on 2017-10-08.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class LoseAction implements Action {

    @Override
    public Outcome resolve(Player player, Player opponent, Game game) {
        Outcome outcome = new Outcome();
        game.removePlayer(player);
        outcome.setAction(this);
        outcome.setDescription(player.getName() + " is out of the game");
        return outcome;
    }
}
