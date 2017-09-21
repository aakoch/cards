package com.adamkoch.cards.loveletter;

import com.adamkoch.utils.ListUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * <p>Created by aakoch on 2017-09-19.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Dealer {
    private final Player player;
    private final List<? extends Card> cards;

    public Dealer(Player player, List<? extends Card> cards) {
        this.player = player;
        this.cards = cards;
    }

    /**
     * Deal each player a number of cards
     * @param players The players  to deal to
     * @param numberOfCardsToEachPlayer The number of cards to deal to each player
     * @return The remaining cards
     */
    public List<Card> dealTo(List<Player> players, int numberOfCardsToEachPlayer) {
        Objects.requireNonNull(players, "List of players cannot be null");

        List<Card> pile = new ArrayList<>(cards);
        if (players.size() * numberOfCardsToEachPlayer > cards.size()) {
            throw new IllegalArgumentException("Not enough cards to deal " + numberOfCardsToEachPlayer + " cards to each of the "
                    + players.size() + " players");
        }
        int startIndex = getIndexOfPlayerToLeftOfDealer(players);

        players.forEach(Player::clearHand);
        Iterator<? extends Player> r = ListUtils.constructRotator(players, startIndex);

        for (int i = 0; i < numberOfCardsToEachPlayer * players.size() && r.hasNext(); i++) {
            Player player = r.next();
            player.setHand(pile.remove(0));
        }
        return pile;
    }

    public int getIndexOfPlayerToLeftOfDealer(List<? extends Player> players) {
        int startIndex = players.indexOf(player) + 1;
        if (startIndex == players.size()) {
            startIndex = 0;
        }
        return startIndex;
    }

    public Player asPlayer() {
        return player;
    }
}
