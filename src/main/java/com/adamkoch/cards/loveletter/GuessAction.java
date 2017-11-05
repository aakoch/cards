package com.adamkoch.cards.loveletter;

/**
 * <p>Created by aakoch on 2017-10-07.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class GuessAction implements Action {

    @Override
    public Outcome resolve(Player player, Player opponent, Game game) {
        Outcome outcome = new Outcome();
        final Card cardToGuess = player.determineCardToGuess();

        String description = player.getName() + " guessed " + opponent.getName() + " has a " + cardToGuess;
        if (opponent.getHand() == cardToGuess) {
            description += " and was right";
            game.removePlayer(opponent);
        }
        else {
            description += " and was wrong";
        }

        outcome.setDescription(description);
        outcome.setAction(this);
        return outcome;
    }
}