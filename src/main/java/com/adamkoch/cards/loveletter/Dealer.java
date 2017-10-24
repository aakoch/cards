package com.adamkoch.cards.loveletter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * <p>Created by aakoch on 2017-10-09.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Dealer {
    private static final Logger LOGGER = LogManager.getLogger(Dealer.class);

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
        int random = new Random().nextInt(100);
        for (int i = 0; i < random; i++) {
            Collections.shuffle(deck);
        }
        LOGGER.trace(() -> deck.toString());
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
