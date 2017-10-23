package com.adamkoch.cards.loveletter;

/**
 * <p>Created by aakoch on 2017-10-08.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class CompareHandsAction implements Action {
    @Override
    public Outcome resolve(Player player, Player opponent, Game game) {
        final Outcome outcome;
        if (player.getHand().ordinal() < opponent.getHand().ordinal()) {
            game.removePlayer(player);
            outcome = new Outcome();
            outcome.setDescription(player + " with a " + player.getHand() + " is defeated by " + opponent);
            outcome.setAction(this);
        }
        else if (player.getHand().ordinal() > opponent.getHand().ordinal()) {
            game.removePlayer(opponent);
            outcome = new Outcome();
            outcome.setDescription(opponent + " with a " + player.getHand() + " is defeated by " + player);
            outcome.setAction(this);
        }
        else {
            outcome = Outcome.NO_EFFECT;
        }
        return outcome;
    }
}
