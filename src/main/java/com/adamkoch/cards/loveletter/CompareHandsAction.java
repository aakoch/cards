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
        final Card playerHand = player.getHand();
        final Card opponentHand = opponent.getHand();
        if (playerHand.ordinal() < opponentHand.ordinal()) {
            game.removePlayer(player);
            outcome = new Outcome();
            outcome.setDescription(player.getName() + " [" + playerHand + "] lost to " + opponent.getName() + " [" +
                    opponentHand + "]");
            outcome.setAction(this);
        }
        else if (playerHand.ordinal() > opponentHand.ordinal()) {
            game.removePlayer(opponent);
            outcome = new Outcome();
            outcome.setDescription(player.getName() + " [" + playerHand + "] beat " + opponent.getName() + " [" +
                    opponentHand + "]");
            outcome.setAction(this);
        }
        else {
            outcome = Outcome.NO_EFFECT;
        }
        return outcome;
    }
}
