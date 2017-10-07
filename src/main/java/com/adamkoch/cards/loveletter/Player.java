package com.adamkoch.cards.loveletter;

/**
 * <p>Created by aakoch on 2017-10-06.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public interface Player {
    void setHand(Card... cards);

    void addToHand(Card card);

    Card determineCardToPlay();

    void chooseOpponent();

    void performsAction();
}
