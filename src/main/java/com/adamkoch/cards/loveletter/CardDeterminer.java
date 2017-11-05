package com.adamkoch.cards.loveletter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>Created by aakoch on 2017-10-22.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class CardDeterminer {

    List<Voter> voters;

    public CardDeterminer() {
        voters = new ArrayList<>(3);
        voters.add((card1, card2) -> {
            if (isEither(card1, card2, Card.PRIEST)) {
                return Optional.of(Card.PRIEST);
            }
            return Optional.empty();
        });
        voters.add((card1, card2) -> {
            if (isEither(card1, card2, Card.COUNTESS)) {
                return Optional.of(Card.COUNTESS);
            }
            return Optional.empty();
        });
        voters.add((card1, card2) -> {
            Card play;
            if (card1.ordinal() > card2.ordinal()) {
                play = card2;
            }
            else {
                play = card1;
            }
            return Optional.of(play);
        });
    }

    public Card determineCard(Card card1, Card card2, boolean shownHandPresent) {
        Card play;
        if (shownHandPresent && isEither(card1, card2, Card.GUARD)) {
            play = Card.GUARD;
        }
        else {
            play = voters.stream()
                         .map(voter -> voter.vote(card1, card2))
                         .filter(Optional::isPresent)
                         .findFirst()
                         .map(Optional::get)
                         .orElse(card2);
        }
        return play;
    }

    private boolean isEither(Card hand, Card drawnCard, Card card) {
        return hand == card || drawnCard == card;
    }

    @FunctionalInterface
    private interface Voter {
        Optional<Card> vote(Card card1, Card card2);
    }
}
