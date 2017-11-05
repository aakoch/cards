package com.adamkoch.cards.loveletter;

/**
 * <p>Created by aakoch on 2017-10-08.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class SwitchHandsAction implements Action {
    @Override
    public Outcome resolve(Player player, Player opponent, Game game) {
        final Card playerHand = player.getHand();
        final Card opponentHand = opponent.getHand();
        player.setHand(opponentHand);
        opponent.setHand(playerHand);
        Outcome outcome = new Outcome();
        outcome.setAction(this);
        outcome.setDescription(opponent.getName() + " had to give up his " + opponentHand + " for " + player.getName
                () + "'s " + playerHand);
        player.isShownHand(opponent);
        opponent.isShownHand(player);
        return outcome;
    }
}
