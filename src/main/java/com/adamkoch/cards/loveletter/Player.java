package com.adamkoch.cards.loveletter;

import java.util.Optional;

/**
 * <p>Created by aakoch on 2017-10-06.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public interface Player {
    void setHand(Card... cards);

    void startTurn();

    void addToHand(Card card);

    Card determineCardToPlay();

    Optional<Object> chooseOpponent();

    Action performsAction();

    void setGame(Game game);

    boolean isSafe();

    void plays(Card card);

    void setSafe();
}
