package com.adamkoch.cards.loveletter;

import com.adamkoch.utils.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>Created by aakoch on 2017-10-06.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class SingleCardHandPlayer implements Player {

    private static final Logger LOGGER = LogManager.getLogger(SingleCardHandPlayer.class);
    protected Card playedCard;
    protected Optional<Player> chosenOpponent;
    protected Game game;
    private Card hand;
    protected Card drawnCard;
    private boolean lastCardPlayedHandmaid;

    private String name;
    private Optional<ShownHand> shownHand = Optional.empty();
    private CardDeterminer cardDeterminer;

    public SingleCardHandPlayer(String name) {
        this.name = name;
        chosenOpponent = Optional.empty();
        cardDeterminer = new CardDeterminer();
    }

    @Override
    public String getName() {
        return name;
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

        playedCard = cardDeterminer.determineCard(hand, drawnCard, shownHand.isPresent());

        if (playedCard == hand) {
            hand = drawnCard;
        }

        lastCardPlayedHandmaid = playedCard == Card.HANDMAID;

        return playedCard;
    }

    @Override
    public Optional<Player> chooseOpponent() {
        assertCardWasDetermined();

        if (playedCard == Card.GUARD && shownHand.isPresent() && !shownHand.get().player.isSafe()) {
            chosenOpponent = Optional.of(shownHand.get().player);
        }
        else {
            List<Player> players = game.getPlayersThatCanBeAttacked(this);

            if (players.size() == 0) {
                LOGGER.info("It doesn't seem like there is anyone " + getName() + " can attack");
                return chosenOpponent = Optional.<Player>empty();
            }
            chosenOpponent = Optional.of(RandomUtils.getRandom(players));
        }

        shownHand = Optional.empty();

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
        final Outcome outcome = action.resolve(this, chosenOpponent.orElse(null), game);
        drawnCard = null;
        return outcome;
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
    public void setHand(Card... cards) {
        if (cards.length != 1) {
            throw new IllegalArgumentException("Can only accept one card, not " + cards.length);
        }
        hand = cards[0];
    }

    @Override
    public Card determineCardToGuess() {
        final List<Card> deck = game.getCardsUnknownToPlayer(this);
        try {
            LOGGER.debug("Cards to choose from: " + deck.stream()
                                                        .collect(Collectors.groupingBy(Function.identity(),
                                                                Collectors.counting())));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        deck.removeIf(card -> card == Card.GUARD);
        final int index = new Random().nextInt(deck.size());
        final Card card = deck.get(index);
        if (card == Card.GUARD) {
            throw new RuntimeException("Player is trying to guess a Guard");
        }
        return card;
    }

    @Override
    public void isShownHand(Player opponent) {
        shownHand = Optional.of(new ShownHand(opponent, opponent.getHand()));
    }

    @Override
    public String toString() {
        return "SingleCardHandPlayer{" +
                "name=" + name +
                ", hand=" + hand +
                ", drawnCard=" + drawnCard +
                ", playedCard=" + playedCard +
                ", chosenOpponent=" + chosenOpponent.map(Player::getName).orElse("<none>") +
                ", lastCardPlayedHandmaid=" + lastCardPlayedHandmaid +
                '}';
    }

    private class ShownHand {
        private final Player player;
        private final Card hand;

        public ShownHand(Player player, Card hand) {
            this.player = player;
            this.hand = hand;
        }
    }
}
