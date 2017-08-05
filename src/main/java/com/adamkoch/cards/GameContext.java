package com.adamkoch.cards;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Created by aakoch on 2017-08-03.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class GameContext {
    private AtomicInteger numberOfPlays;
    private boolean someoneElseHasKnocked;
    private final Map<String, Set<Card>> hands;
    private final Map<String, Set<Card>> discards;
    private List<Card> cardsLeft;
    private List<Outcome> outcomes;
    private List<Player> players;

    public GameContext() {
        someoneElseHasKnocked = false;
        numberOfPlays = new AtomicInteger(0);
        hands = new HashMap<>();
        cardsLeft = new ArrayList<>();
        outcomes = new ArrayList<>();
        discards = new HashMap<>();
        players = Collections.emptyList();
    }

    public void incrementNumberOfPlays() {
        numberOfPlays.incrementAndGet();
    }

    public int getNumberOfPlays() {
        return numberOfPlays.get();
    }

    public void setPlayers(List<Player> players) {
        this.players = new ArrayList<>(players);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public boolean someoneElseHasKnocked() {
        return someoneElseHasKnocked;
    }

    public void setSomeoneElseKnocked() {
        someoneElseHasKnocked = true;
    }

    public void addCardToHandForPlayer(Player player, Card card) {
        hands.computeIfAbsent(player.getName(), name -> new HashSet<>()).add(card);
    }

    public void removeCardFromHandForPlayer(Player player, Card card) {
        if (hands.containsKey(player.getName())) {
            hands.get(player.getName()).remove(card);
        }
    }

    @Deprecated
    public void cardsStillOutThere(List<Card> cardsLeft) {
        this.cardsLeft = new ArrayList<>(cardsLeft);
    }

    public double chanceOfGettingBetterCardWithSameSuit(Card card) {
        double numberOfCardsWithSameSuitAndHigher = cardsLeft.stream().filter(cardLeft -> cardLeft.getSuit() == card.getSuit()
            && cardLeft.getRank().greaterThan(card.getRank())).count();
        double numberOfCardsLeft = cardsLeft.size();
        return numberOfCardsWithSameSuitAndHigher / numberOfCardsLeft;
    }

    public void addOutcome(Outcome outcome) {
        outcomes.add(outcome);
    }

    public void addCardToThoseDiscardedByPlayer(Player player, Card discardedCard) {
        discards.computeIfAbsent(player.getName(), name -> new HashSet<>()).add(discardedCard);
    }

    @Override
    public String toString() {
        return "GameContext{" +
                "numberOfPlays=" + numberOfPlays +
                ", numberOfPlayers=" + players.size() +
                ", someoneElseHasKnocked=" + someoneElseHasKnocked +
                ", hands=" + hands +
                ", discards=" + discards +
                '}';
    }

    public Player getNextPlayer(Player player) {
        int indexOfCurrentPlayer = players.indexOf(player);
        if (indexOfCurrentPlayer >= players.size() - 1) {
            indexOfCurrentPlayer = 0;
        }
        return players.get(indexOfCurrentPlayer + 1);
    }
}
