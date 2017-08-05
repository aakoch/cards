package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

/**
 * Game where every player starts out with 3 tokens and play until they are out of tokens. The player left is the
 * winner. Many hands will be played. After each hand, the cards are collected and shuffled.
 *
 * <p>Created by aakoch on 2017-07-30.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class ThirtyOneGame implements Game {
    private static final Logger LOGGER = LogManager.getLogger(ThirtyOneGame.class);
    private final Players<Player> players;

    public ThirtyOneGame(Players<Player> players) {

        this.players = players;
    }

    @Override
    public GameResult play() {
        GameResult gameResult = new GameResult();
        Player randomPlayer = players.pickRandomPlayer();
        Deck deck = getStartDeck();
        deck.shuffle();

        randomPlayer.setAsDealer(deck);

        Dealer dealer = new Dealer(randomPlayer, deck);
//        List<Card> remainingCards = dealer.dealTo(players.list(), 3);
//        Pile drawPile = new DrawPile(remainingCards);
//        Pile discardPile = new DiscardPile(drawPile.remove());

        final Queue<Player> playerQueue = determineOrderOfPlayers(this.players, randomPlayer);

//        for (Player player : playerQueue) {
//            LOGGER.debug("player = " + player);
//        }
        while(atLeast2PlayersAreStillIn(playerQueue)) {

            ThirtyOneRound round = new ThirtyOneRound(dealer, playerQueue);
            Result result = round.play();
            gameResult.addRound(result);
            List<Player> payers = result.getLosers();
            payers.forEach(Player::pay);
            LOGGER.debug("payers = " + payers + "\n\n");

            deck = getStartDeck();
            deck.shuffle();
            dealer = determineDealer(deck);

            if (playerQueue.removeIf(player -> !player.stillInGame())) {
                LOGGER.info("players left in game are " + playerQueue.stream().map(Player::getName).collect
                        (Collectors.joining(", ")));
            }

        }

        Player winner = figureOutWinner(players);
        LOGGER.info("**************** winner = " + winner + " ************************");

        players.list().stream().forEach(player -> LOGGER.debug("player = " + player));
        gameResult.setWinner(winner);

        return gameResult;
    }

    private Player figureOutWinner(Players<Player> players) {
        return players.list().stream().filter(player -> player.stillInGame()).findFirst().orElseThrow(() -> new
                RuntimeException("Could not find winner"));
    }

    private boolean atLeast2PlayersAreStillIn(final Queue<Player> playerQueue) {

        return playerQueue.stream().filter(player -> player.stillInGame()).count() > 1;

    }

    private Dealer determineDealer(Deck deck) {
        final Player randomPlayer = players.pickRandomPlayer();
        Dealer dealer = new Dealer(randomPlayer, deck);
        return dealer;
    }

    private void listHands() {
        for (Player player : players.list()) {
            LOGGER.debug(player);
        }
    }

    public Deck getStartDeck() {
        return new StandardDeck();
    }


    private Queue<Player> determineOrderOfPlayers(Players<Player> players, Player dealer) {
        int index = players.list().indexOf(dealer);

        final ArrayBlockingQueue<Player> ordered = new ArrayBlockingQueue<>(players.size());
        for (int i = index; i < players.size(); i++) {
            final Player player = players.list().get(i);
            ordered.add(player);
        }
        for (int i = 0; i < index; i++) {
            final Player player = players.list().get(i);
            ordered.add(player);
        }

        return ordered;
    }
}
