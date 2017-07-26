package com.adamkoch.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Dealer {
    private final List<Card> cards;

    public Dealer(List<Card> cards) {

        this.cards = cards;
    }

    public void dealTo(Player player1, Player player2) {

        for (Iterator<Card> iterator = cards.iterator(); iterator.hasNext(); ) {
            Card card = iterator.next();
            player1.addCardToHand(card);
            if (iterator.hasNext()) {
                Card card2 = iterator.next();
                player2.addCardToHand(card2);
            }
        }

    }
}
