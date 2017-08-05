package com.adamkoch.cards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Created by aakoch on 2017-08-03.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class GameContext {
    private AtomicInteger numberOfPlays;
    private int numberOfPlayers;
    private boolean someoneElseHasKnocked;
    private final Map<Player, List<Card>> hands;
    private List<Card> cardsLeft;

    public GameContext() {
        someoneElseHasKnocked = false;
        numberOfPlays = new AtomicInteger(0);
        hands = new HashMap<>();
        cardsLeft = new ArrayList<>();
    }

    public void incrementNumberOfPlays() {
        numberOfPlays.incrementAndGet();
    }

    public int getNumberOfPlays() {
        return numberOfPlays.get();
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public boolean someoneElseHasKnocked() {
        return someoneElseHasKnocked;
    }

    public void setSomeoneElseKnocked() {
        someoneElseHasKnocked = true;
    }

    public void addCardToHandForPlayer(Player player, Card card) {
        hands.computeIfAbsent(player, player1 -> new ArrayList<>()).add(card);
    }

    public void removeCardFromHandForPlayer(Player player, Card card) {
        //hands.getOrDefault(player, new ArrayList<>()).remove(card);
    }

    public void cardsStillOutThere(List<Card> cardsLeft) {
        this.cardsLeft = new ArrayList<>(cardsLeft);
    }

    public double chanceOfGettingBetterCardWithSameSuit(Card card) {
        double numberOfCardsWithSameSuitAndHigher = cardsLeft.stream().filter(cardLeft -> cardLeft.getSuit() == card.getSuit()
            && cardLeft.getRank().greaterThan(card.getRank())).count();
        double numberOfCardsLeft = cardsLeft.size();
        return numberOfCardsWithSameSuitAndHigher / numberOfCardsLeft;
    }

    @Override
    public String toString() {
        return "GameContext{" +
                "numberOfPlays=" + numberOfPlays +
                ", numberOfPlayers=" + numberOfPlayers +
                ", someoneElseHasKnocked=" + someoneElseHasKnocked +
                ", hands=" + hands +
                ", cardsLeft=" + cardsLeft +
                '}';
    }
}
