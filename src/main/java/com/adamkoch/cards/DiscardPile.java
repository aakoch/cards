package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-30.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class DiscardPile {
    private static final Logger LOGGER = LogManager.getLogger(DiscardPile.class);

    private FIFOList<Card> discardedCards;
    private final DrawPile drawPile;

    public DiscardPile(DrawPile drawPile) {
        this.drawPile = drawPile;
        drawPile.setDiscardPile(this);

        final Card card = drawPile.draw();
        LOGGER.info("Dealer flips over " + card);

        discardedCards = new FIFOList<>();
        discardedCards.add(card);
    }

    public Card peekAtTopCard() {
        return discardedCards.peek();
    }

    public Card removeTopCard() {
        return discardedCards.pop();
    }

    public List<Card> getCards() {
        return discardedCards.allButLast();
    }

    public void setCards(List<Card> cards) {
        this.discardedCards = new FIFOList<>(cards);
    }

    public void add(Card card) {
        discardedCards.add(card);
    }
}
