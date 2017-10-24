package com.adamkoch.cards.loveletter;

import java.util.Optional;

/**
 * <p>Created by aakoch on 2017-10-06.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public interface Player {

    String getName();
    void setHand(Card... cards);

    void startTurn();

    void addToHand(Card card);

    Card determineCardToPlay();

    Optional<Player> chooseOpponent();

    Outcome performsAction(Game game);

    void setGame(Game game);

    boolean isSafe();

    void plays(Card card);

    void setSafe();

    Card getHand();

    Card determineCardToGuess();

    void isShownHand(Player opponent);
}
