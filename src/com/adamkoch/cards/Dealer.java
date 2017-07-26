package com.adamkoch.cards;

import java.util.List;

/**
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

    public void dealTo(List<Player> players) {
        Rotation<Player> r = new Rotation<>(players.toArray(new Player[0]));
        for (Card card : cards) {
            Player player = r.next();
            player.addCardToHand(card);
        }
    }

    private class Rotation<T> {
        private final T[] objects;
        private int counter;

        Rotation(T[] objects) {
            this.objects = objects;
            counter = 0;
        }

        T next() {
            if (counter == objects.length) {
                counter = 0;
            }
            return objects[counter++];
        }
    }
}
