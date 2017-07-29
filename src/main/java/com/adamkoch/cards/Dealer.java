package com.adamkoch.cards;

import java.util.Iterator;
import java.util.List;

import com.adamkoch.utils.ListUtils;

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

    public Dealer(Player player, Deck deck) {
        this(deck);
        this.player = player;
        player.setAsDealer(deck);
    }

    public void dealTo(List<? extends Player> players, int numberOfCards) {
        if (players.size() * numberOfCards > cards.size()) {
            throw new IllegalArgumentException("Not enough cards to deal " + numberOfCards + " cards to each of the "
                    + players.size() + " players");
        }
        int startIndex = getIndexOfPlayerToLeftOfDealer(players);

        Iterator<? extends Player> r = ListUtils.constructRotator(players, startIndex);

        for (int i = 0; i < numberOfCards * players.size() && r.hasNext(); i++) {
            Player player = r.next();
            player.addCardToHand(cards.remove(0));
        }
    }

    public int getIndexOfPlayerToLeftOfDealer(List<? extends Player> players) {
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

    public void dealUntilGoneTo(List<Player> players) {
        int startIndex = getIndexOfPlayerToLeftOfDealer(players);
        Iterator<Player> r = ListUtils.constructRotator(players, startIndex);

        Iterator<Card> cardIterator = cards.iterator();
        while (cardIterator.hasNext()) {
            Card card = cardIterator.next();
            cardIterator.remove();
            Player player = r.next();
            player.addCardToHand(card);
        }
    }

    @Override
    public String toString() {
        return player.toString();
    }
}
