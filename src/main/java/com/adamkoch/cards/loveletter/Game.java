package com.adamkoch.cards.loveletter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by aakoch on 2017-10-07.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Game {
    private static final Logger LOGGER = LogManager.getLogger(Game.class);
    
    private final List<Card> deck;
    private List<Player> players;
    private List<Play> history;
    private Player winner;
    private List<Player> playersStillInGame;
    private Dealer dealer;
    private PlayerIterator playerIterator;
    private boolean cardsDealt = false;

    public Game() {
        history = new ArrayList<>();
        deck = Card.deck();
    }

    public void setPlayers(List<Player> players) {
        if (players.size() > deck.size()) {
            throw new RuntimeException("Can't have more players than cards in the deck");
        }
        this.players = players;
        players.forEach(player -> player.setGame(this));
        this.playersStillInGame = new ArrayList<>(players);
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public void addToHistory(Player player, Card playedCard) {
        history.add(new Play(player, playedCard));
    }

    public List<Player> getPlayersStillInGame() {
        return playersStillInGame;
    }

    public List<Player> getPlayersThatCanBeAttacked(Player playerGuessing) {
        final List<Player> players = getPlayersStillInGame();
        players.removeIf(player -> player.equals(playerGuessing));
        players.removeIf(player -> player.isSafe());
        return players;
    }

    public void removePlayer(int index) {
        playersStillInGame.remove(getPlayer(index));
    }

    public boolean shouldContinue() {
        return playersStillInGame.size() > 1 && !deck.isEmpty();
    }

    public Player getWinner() {
        return playersStillInGame.get(0);
    }

    public void removePlayer(Player player) {
        playersStillInGame.remove(player);
    }

    public List<Card> getDeck() {
        return deck;
    }

    public Player nextPlayer() {
        if (playerIterator == null) {
            final Player lastWinner = winner != null ? winner : dealer.asPlayer();

            playerIterator = new PlayerIterator(players, players.indexOf(lastWinner));
        }
        return playerIterator.next();
    }

    public static <T> T firstNonNull(T... objects) {
        for (T o : objects) {
            if (o != null) {
                return o;
            }
        }

        return null;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Card drawCard() {
        assertCardsWereDealt();

        LOGGER.debug(deck);
        return deck.remove(0);
    }

    private void assertCardsWereDealt() {
        if (!cardsDealt) {
            throw new RuntimeException("Cannot start game until the cards are dealt");
        }
    }

    public void dealCards() {
        dealer.setDeck(deck);
        dealer.shuffle();
        dealer.deal(players);
        cardsDealt = true;
    }

    private class Play {
        public Play(Player player, Card playedCard) {
        }
    }
}
