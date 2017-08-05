package com.adamkoch.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Outcome {
    public static final Outcome TIE = new Outcome((Card) null);

    private Card card;
    private Player winner;
    private List<Player> losers;
    private Player loser;
    private Card discard;
    private boolean has31;
    private boolean playerKnocks;
    private boolean playerDrewFromDiscardPile;
    private Card cardTakenFromDiscardPile;

    public Outcome(Card card) {
        this.card = card;
        playerDrewFromDiscardPile = false;
    }

    public Outcome(Player highestPlayer) {
        this.card = null;
        this.winner = highestPlayer;
        losers = new ArrayList<>();
        losers.add(highestPlayer);
        playerDrewFromDiscardPile = false;
    }

    public Outcome() {

        playerDrewFromDiscardPile = false;
    }

    public Card getCard() {
        return card;
    }

    @Override
    public String toString() {
        return "winner=" + card;
    }

    public Player getWinner() {
        return winner;
    }

    public List<Player> getLosers() {
        return losers;
    }

//    public Player getLoser() {
//        return losers.get(0);
//    }
//
//    public void setLoser(Player loser) {
//        losers.clear();
//        losers.add(loser);
//    }

    public void setLosers(Set<Player> lowestPlayers) {
        this.losers = new ArrayList<>(lowestPlayers);
    }

    public void setDiscard(Card discard) {
        this.discard = discard;
    }

    public void setHas31() {
        has31 = true;
    }

    public boolean getHas31() {
        return has31;
    }

    public void setPlayerKnocks() {
        playerKnocks = true;
    }

    public boolean playerKnocks() {
        return playerKnocks;
    }

    public boolean playerDrewFromDiscardPile() {
        return playerDrewFromDiscardPile;
    }

    public Card getCardTakenFromDiscardPile() {
        return cardTakenFromDiscardPile;
    }

    public void setCardTakenFromDiscardPile(Card cardTakenFromDiscardPile) {
        playerDrewFromDiscardPile = true;
        this.cardTakenFromDiscardPile = cardTakenFromDiscardPile;
    }

    public Card getDiscardCard() {
        return discard;
    }
}
