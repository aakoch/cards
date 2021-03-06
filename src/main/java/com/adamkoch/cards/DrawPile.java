package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-30.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class DrawPile {
    private static final Logger LOGGER = LogManager.getLogger(DrawPile.class);

    private List<Card> cards;
    private final GameContext gameContext;
    private DiscardPile discardPile;

    public DrawPile(List<Card> remainingCards, GameContext gameContext) {
        cards = new ArrayList<>(remainingCards);
        this.gameContext = gameContext;
        LOGGER.debug("remainingCards.size() = " + remainingCards.size());
    }

    public Card draw() {
        if (cards.isEmpty()) {
            List<Card> cards = discardPile.getCards();
            Collections.shuffle(cards);
            LOGGER.debug("Shuffling discard pile back into draw pile: " + cards);
            gameContext.incrementDiscardPileShuffled();
            this.cards = new ArrayList<>(cards);
        }
        return cards.remove(0);
    }

    public void setDiscardPile(DiscardPile discardPile) {
        this.discardPile = discardPile;
    }

    public DiscardPile getDiscardPile() {
        return discardPile;
    }
}
