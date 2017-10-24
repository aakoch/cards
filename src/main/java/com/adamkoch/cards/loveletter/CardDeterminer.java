package com.adamkoch.cards.loveletter;

/**
 * <p>Created by aakoch on 2017-10-22.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class CardDeterminer {
    private boolean shownHandPresent;

    public boolean shouldPlayCardOne(Card hand, Card drawnCard) {
        final boolean b;
        if (shownHandPresent && isEither(hand, drawnCard, Card.GUARD)) {
            b = hand == Card.GUARD;
        }
        else if (isEither(hand, drawnCard, Card.COUNTESS)) {
            b = drawnCard == Card.COUNTESS;
        }
        else {
            b = drawnCard.ordinal() < hand.ordinal();
        }
        shownHandPresent = false;
        return b;
    }

    private boolean isEither(Card hand, Card drawnCard, Card card) {
        return hand == card || drawnCard == card;
    }

    public CardDeterminer with(boolean shownHandPresent) {
        this.shownHandPresent = shownHandPresent;
        return this;
    }
}
