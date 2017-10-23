package com.adamkoch.cards.loveletter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Created by aakoch on 2017-10-06.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class SingleCardHandPlayer implements Player {

    private final int playerCount;
    private Card hand;
    private Card drawnCard;
    private Card playedCard;
    private Optional<Player> chosenOpponent;
    private Game game;
    private boolean lastCardPlayedHandmaid;

    private static AtomicInteger count = new AtomicInteger(1);

    public SingleCardHandPlayer() {
        this.playerCount = count.getAndIncrement();
    }

    @Override
    public void setHand(Card... cards) {
        if (cards.length != 1) {
            throw new IllegalArgumentException("Can only accept one card, not " + cards.length);
        }
        hand = cards[0];
    }

    @Override
    public void startTurn() {
        drawnCard = null;
        playedCard = null;
        chosenOpponent = null;
        lastCardPlayedHandmaid = false;
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

        lastCardPlayedHandmaid = playedCard == Card.HANDMAID;

        return playedCard;
    }

    @Override
    public Optional<Player> chooseOpponent() {
        assertCardWasDetermined();

        List<Player> players = game.getPlayersThatCanBeAttacked(this);

        if (players.size() == 0) {
            return chosenOpponent = Optional.<Player>empty();
        }
        chosenOpponent = Optional.of(players.get(0));
        return chosenOpponent;
    }

    @Override
    public Outcome performsAction(Game game) {
        assertOpponentWasChosen();
        final Action action;
        if (!chosenOpponent.isPresent()) {
            action = new EmptyAction();
        }
        else {
            action = playedCard.getAction();
        }
        return action.resolve(this, chosenOpponent.orElse(null), game);
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

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public boolean isSafe() {
        return lastCardPlayedHandmaid;
    }

    @Override
    public void plays(Card card) {
        playedCard = card;

        lastCardPlayedHandmaid = playedCard == Card.HANDMAID;
    }

    @Override
    public void setSafe() {
        lastCardPlayedHandmaid = true;
    }

    @Override
    public Card getHand() {
        return hand;
    }

    @Override
    public Card determineCardToGuess() {
        return Card.GUARD;
    }

    @Override
    public void isShownHand(Player opponent) {
        learn(opponent).has(opponent.getHand());
    }

    private Study learn(Player opponent) {
        return new Study(opponent);
    }

    @Override
    public String toString() {
        return "SingleCardHandPlayer{" +
                "index=" + playerCount +
                ", hand=" + hand +
                ", drawnCard=" + drawnCard +
                ", playedCard=" + playedCard +
                ", chosenOpponent=" + chosenOpponent +
                ", lastCardPlayedHandmaid=" + lastCardPlayedHandmaid +
                '}';
    }

    private class Study {
        private final Player opponent;

        public Study(Player opponent) {
            this.opponent = opponent;
        }

        public void has(Card hand) {

        }
    }
}
