package com.adamkoch.cards.loveletter;

/**
 * <p>Created by aakoch on 2017-10-06.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class SingleCardHandPlayer implements Player {

    private Card hand;
    private Card drawnCard;
    private Card playedCard;
    private Player chosenOpponent;

    @Override
    public void setHand(Card... cards) {
        if (cards.length != 1) {
            throw new IllegalArgumentException("Can only accept one card, not " + cards.length);
        }
        hand = cards[0];
    }

    @Override
    public void addToHand(Card card) {
        assertCardWasNotDrawn();
        drawnCard = card;
    }

    private void assertCardWasNotDrawn() {
        if (drawnCard != null) {
            throw new IllegalStateException("Player has already drawn a card");
        }
    }

    @Override
    public Card determineCardToPlay() {
        assertCardWasDrawn();

        if (drawnCard.ordinal() < hand.ordinal()) {
            playedCard = drawnCard;
        }
        else {
            playedCard = this.hand;
            hand = drawnCard;
        }
        drawnCard = null;

        return playedCard;
    }

    @Override
    public void chooseOpponent() {
        assertCardWasDetermined();
        chosenOpponent = new SingleCardHandPlayer();
    }

    @Override
    public void performsAction() {
        assertOpponentWasChosen();
    }

    private void assertOpponentWasChosen() {
        if (chosenOpponent == null) {
            throw new IllegalStateException("Player has not chosen an opponent yet");
        }
    }

    private void assertCardWasDetermined() {
        if (playedCard == null) {
            throw new IllegalStateException("Player has not played a card yet");
        }
    }

    private void assertCardWasDrawn() {
        if (drawnCard == null) {
            throw new IllegalStateException("Player has not drawn a card yet");
        }
    }
}
