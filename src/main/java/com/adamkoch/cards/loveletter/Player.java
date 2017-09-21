package com.adamkoch.cards.loveletter;

import com.adamkoch.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;

import static com.adamkoch.cards.loveletter.Card.PRINCESS;

/**
 * <p>Created by aakoch on 2017-09-19.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Player {
    private final String name;

    private Card hand;
    private Card lastPlayed;

    public Player(String name) {
        this.name = name;
    }

    public static Card determineCardToPlay(Card card1, Card card2) {
        Card card = null;

        if (card1 == PRINCESS || card2 == PRINCESS) {
            card = nonPrincess(card1, card2);
        }
        else if (card1 == Card.COUNTESS && (card2 == Card.KING || card2 == Card.PRINCE)) {
            card = card1;
        }
        else if (card2 == Card.COUNTESS && (card1 == Card.KING || card1 == Card.PRINCE)) {
            card = card2;
        }
        else if (card2.ordinal() < card1.ordinal()) {
            card = card2;
        }
        else if (card1.ordinal() < card2.ordinal()) {
            card = card1;
        }
        else {
            card = card2;
        }

        return card;
    }

    private static Card nonPrincess(Card card1, Card card2) {
        return card1 == PRINCESS ? card2 : card1;
    }

    public void clearHand() {
        hand = null;
    }

    public Card getHand() {
        return hand;
    }

    public void setHand(Card card) {
        hand = card;
    }

    public Card determineCardToPlay(Card card2) {
        final Card card1 = hand;

        Card card = determineCardToPlay(card1, card2);


        lastPlayed = card;

        if (card == card1) {
            hand = card2;
        }
        else {
            hand = card1;
        }

        return card;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", hand=" + hand +
                '}';
    }

    public String getName() {
        return name;
    }

    public Player chooseOpponent(List<Player> players) {
        List<Player> remainingPlayers = new ArrayList<>(players);
        remainingPlayers.remove(this);
        remainingPlayers.removeIf(player -> player.lastPlayed() == Card.HANDMAID);

        if (remainingPlayers.isEmpty()) {
            throw new NoOpponentException();
        }
        return RandomUtils.getRandom(remainingPlayers);
    }

    private Card lastPlayed() {
        return lastPlayed;
    }

    public Player choosePlayerToDiscardTheirCard(List<Player> players) {
        List<Player> remainingPlayers = new ArrayList<>(players);
        remainingPlayers.removeIf(player -> player.lastPlayed() == Card.HANDMAID);

        if (remainingPlayers.isEmpty()) {
            throw new NoOpponentException();
        }
        return RandomUtils.getRandom(remainingPlayers);
    }

    public Player chooseOpponentToGuess(List<Player> players) {
        return chooseOpponent(players);
    }

    public Player chooseOpponentToReveal(List<Player> players) {

        return chooseOpponent(players);
    }

    public Player chooseOpponentToCompare(List<Player> players) {

        return chooseOpponent(players);
    }

    public Player chooseOpponentToTrade(List<Player> players) {

        return chooseOpponent(players);
    }
}
