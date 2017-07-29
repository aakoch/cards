package com.adamkoch.cards;

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
        if (card.getRank() == 7) {
            topCard = bottomCard = card;
        }
        else if (card.getRank() == topCard.getRank() + 1) {
            topCard = card;
        }
        else if (card.getRank() == bottomCard.getRank() - 1) {
            bottomCard = card;
        }
    }

    public boolean isStarted() {
        return topCard != null;
    }
}
