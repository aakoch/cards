package com.adamkoch.cards.loveletter;

import java.util.stream.Stream;

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
        if (shownHandPresent && Stream.of(hand, drawnCard).anyMatch(card -> card == Card.GUARD)) {
            b = hand == Card.GUARD;
        }
        else {
            b = drawnCard.ordinal() < hand.ordinal();
        }
        shownHandPresent = false;
        return b;
    }

    public CardDeterminer with(boolean shownHandPresent) {
        this.shownHandPresent = shownHandPresent;
        return this;
    }
}
