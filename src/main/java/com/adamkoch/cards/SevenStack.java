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
        if (card.getRank().innerRank == 7) {
            topCard = bottomCard = card;
        }
        else if (card.getRank().innerRank == topCard.getRank().innerRank + 1) {
            topCard = card;
        }
        else if (card.getRank().innerRank == bottomCard.getRank().innerRank - 1) {
            bottomCard = card;
        }
    }

    public boolean isStarted() {
        return topCard != null;
    }
}
