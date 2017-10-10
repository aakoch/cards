package com.adamkoch.cards.loveletter;

import java.util.Collections;
import java.util.List;

/**
 * <p>Created by aakoch on 2017-10-09.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Dealer {
    private final Player player;
    private List<Card> deck;

    public Dealer(Player player) {
        this.player = player;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public void deal(List<Player> players) {
        players.forEach(player -> player.setHand(nextCard()));
    }

    private Card nextCard() {
        return deck.remove(0);
    }

    public Player asPlayer() {
        return player;
    }
}
