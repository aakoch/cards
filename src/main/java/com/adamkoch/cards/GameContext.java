package com.adamkoch.cards;

import com.adamkoch.cards.utils.CardUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Created by aakoch on 2017-08-03.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class GameContext {
    private final Map<String, Set<Card>> hands;
    private final Map<String, Set<Card>> discards;
    private AtomicInteger numberOfPlays;
    private boolean someoneElseHasKnocked;
    private List<Card> cardsLeft;
    private List<Outcome> outcomes;
    private List<Player> players;
    private int discardPileShuffled;

    public GameContext() {
        someoneElseHasKnocked = false;
        numberOfPlays = new AtomicInteger(0);
        hands = new HashMap<>();
        cardsLeft = new ArrayList<>();
        outcomes = new ArrayList<>();
        discards = new HashMap<>();
        players = Collections.emptyList();
        discardPileShuffled = 0;
    }

    public void incrementNumberOfPlays() {
        numberOfPlays.incrementAndGet();
    }

    public int getNumberOfPlays() {
        return numberOfPlays.get();
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public int getNumberOfPlayersStillInGame() {
        return (int) getPlayersStillInTheGameStream().count();
    }

    private Stream<Player> getPlayersStillInTheGameStream() {
        return players.stream().filter(Player::stillInGame);
    }

    public List<Player> getPlayersStillInTheGame() {
        return getPlayersStillInTheGameStream().collect(Collectors.toList());
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
        double numberOfCardsWithSameSuitAndHigher = cardsLeft.stream()
                                                             .filter(cardLeft -> cardLeft.getSuit() == card.getSuit()
                                                                     && cardLeft.getRank().greaterThan(card.getRank()))
                                                             .count();
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

//    public Player getNextPlayer(Player player) {
//        final List<Player> players = getPlayersStillInTheGame();
//        int indexOfCurrentPlayer = players.indexOf(player);
//        if (indexOfCurrentPlayer == -1) {
//            // dealer got out -- find out where he was in the original lineup
//            indexOfCurrentPlayer = this.players.indexOf(player);
//        }
//        if (indexOfCurrentPlayer >= players.size() - 1) {
//            indexOfCurrentPlayer = -1;
//        }
//        Player nextPlayer;
////        do {
//            nextPlayer = players.get(indexOfCurrentPlayer + 1);
////            if (!nextPlayer.stillInGame()) {
////                indexOfCurrentPlayer++;
////            }
////        } while (nextPlayer == null);
//
//        return nextPlayer;
//    }

    public Set<Card> getKnownHand(Player player) {
        return hands.getOrDefault(player.getName(), Collections.emptySet());
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = new ArrayList<>(players);
    }

    public void startNewRound() {

        hands.clear();
        numberOfPlays.set(0);
        someoneElseHasKnocked = false;
        discards.clear();
        cardsLeft = new ArrayList<>();
        outcomes = new ArrayList<>();
        discardPileShuffled = 0;

    }

    public void incrementDiscardPileShuffled() {
        discardPileShuffled++;
    }

    public int getNumberOfTimesDiscardPileShuffled() {
        return discardPileShuffled;
    }

    public Suit suitPlayerLikelyHas(Player player) {
        Optional<Suit> knownHandSuitOptional = suitPlayerLikelyHasUsingKnownHand(player);
        if (knownHandSuitOptional.isPresent()) {
            return knownHandSuitOptional.get();
        }

        Optional<Suit> usingDiscardsSuitOptional = suitPlayerLikelyHasUsingDiscards(player);
        return usingDiscardsSuitOptional.orElse(Suit.NONE);
    }

    public Optional<Suit> suitPlayerLikelyHasUsingKnownHand(Player player) {
        Optional<Suit> majoritySuit;
        final Set<Card> cardSet = hands.get(player.getName());
        if (cardSet == null || cardSet.isEmpty()) {
            majoritySuit = Optional.empty();
        }
        else {
            majoritySuit = CardUtil.findMajoritySuit(cardSet);
        }
        return majoritySuit;
    }

    public Optional<Suit> suitPlayerLikelyHasUsingDiscards(Player player) {
        Optional<Suit> minoritySuit;
        final Set<Card> cards = discards.get(player.getName());
        if (cards == null || cards.isEmpty()) {
            minoritySuit = Optional.empty();
        }
        else {
            minoritySuit = CardUtil.findMinoritySuit(cards);
        }
        return minoritySuit;
    }
}
