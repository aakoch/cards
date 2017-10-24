package com.adamkoch.cards.loveletter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    private Card burntCard;

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
        final List<Player> players = new ArrayList<>(getPlayersStillInGame());
        players.removeIf(player -> player.equals(playerGuessing));
        players.removeIf(player -> player.isSafe());
        return players;
    }

    public void removePlayer(int index) {
        playersStillInGame.remove(getPlayer(index));
    }

    public boolean shouldContinue() {
        if (deck.isEmpty()) {
            LOGGER.info("Deck is empty");
        }
        return playersStillInGame.size() > 1 && !deck.isEmpty();
    }

    public Player getWinner() {
        if (playersStillInGame.size() > 1) {
            LOGGER.info("More than one player still in the game...");
            LOGGER.info(playersStillInGame.stream().map(player -> player.getName() + " [" + player.getHand() + "]")
                                          .collect(Collectors.joining(", ")));
            playersStillInGame.sort(Comparator.comparingInt(p -> -p.getHand().ordinal()));
        }
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

        LOGGER.trace(deck);
        if (deck.isEmpty()) {
            return burntCard;
        }
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
        burntCard = deck.remove(0);
        dealer.deal(players);
        cardsDealt = true;
    }

    public List<Card> getCardsUnknownToPlayer(SingleCardHandPlayer player) {
        List<Card> list = new ArrayList<>();
        list.addAll(deck);
        if (burntCard != null) {
            list.add(burntCard);
        }
        for (Player playerLeft : playersStillInGame) {
            if (!player.equals(playerLeft)) {
                if (player.getHand() != null) {
                    list.add(player.getHand());
                }
            }
        }
        return list;
    }

    private List<Card> join(List<Card> deck, Card burntCard) {
        List<Card> list = new ArrayList<>(deck);
        list.add(burntCard);
        return list;
    }

    private class Play {
        public Play(Player player, Card playedCard) {
        }
    }
}
