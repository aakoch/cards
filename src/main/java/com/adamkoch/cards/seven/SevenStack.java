package com.adamkoch.cards.seven;

import com.adamkoch.cards.Card;
import com.adamkoch.cards.Suit;

/**
 * <p>Created by aakoch on 2017-07-27.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class SevenStack {
    private final Suit suit;
    private Card topCard;
    private Card bottomCard;
    private boolean aceIsHigh = false;

    public SevenStack(Suit suit) {
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }

    public Card getBottomCard() {
        return bottomCard;
    }

    public Card getTopCard() {
        return topCard;
    }

    public void addCard(Card card) {
        if (card.getRank().getNumericRank(aceIsHigh) == 7) {
            topCard = bottomCard = card;
        }
        else if (card.getRank().getNumericRank(aceIsHigh) == topCard.getRank().getNumericRank(aceIsHigh) + 1) {
            topCard = card;
        }
        else if (card.getRank().getNumericRank(aceIsHigh) == bottomCard.getRank().getNumericRank(aceIsHigh) - 1) {
            bottomCard = card;
        }
    }

    public boolean isStarted() {
        return topCard != null;
    }
}
