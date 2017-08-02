package com.adamkoch.cards;

import java.util.ArrayList;
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
    private FIFOList<Card> cards;
    private final DrawPile drawPile;

    public DiscardPile(DrawPile drawPile) {
        this.drawPile = drawPile;
        drawPile.setDiscardPile(this);

        cards = new FIFOList<>();
        cards.add(drawPile.draw());
    }

    public Card topCard() {
        return cards.pop();
    }

    public List<Card> getCards() {
        return cards.allButLast();
    }

    public void setCards(List<Card> cards) {
        this.cards = new FIFOList<>(cards);
    }

    public void add(Card card) {
        cards.add(card);
    }
}
