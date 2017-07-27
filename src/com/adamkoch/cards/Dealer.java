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
    private Player player;

    public Dealer(List<Card> cards) {
        this.cards = cards;
    }

    public Dealer(Deck deck) {
        this(deck.cards());
    }

    public void dealTo(List<Player> players, int numberOfCards) {
        if (players.size() * numberOfCards > cards.size()) {
            throw new IllegalArgumentException("Not enough cards to deal " + numberOfCards + " cards to each of the "
                    + players.size() + " players");
        }
        int startIndex = getIndexOfPlayerToLeftOfDealer(players);
        Rotation<Player> r = new Rotation<>(players.toArray(new Player[0]), startIndex);
        for (int i = 0; i < numberOfCards * players.size(); i++) {
            Player player = r.next();
            player.addCardToHand(cards.remove(0));
        }
    }

    public int getIndexOfPlayerToLeftOfDealer(List<Player> players) {
        int startIndex = players.indexOf(player) + 1;
        if (startIndex == players.size()) {
            startIndex = 0;
        }
        return startIndex;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player playerToLeft(List<Player> players) {
        return players.get(getIndexOfPlayerToLeftOfDealer(players));
    }

    private class Rotation<T> {
        private final T[] objects;
        private int counter;

        Rotation(T[] objects, int startIndex) {
            this.objects = objects;
            counter = startIndex;
        }

        T next() {
            if (counter == objects.length) {
                counter = 0;
            }
            return objects[counter++];
        }
    }
}
