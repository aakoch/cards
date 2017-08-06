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
    private final GameContext gameContext;
    private final List<Player> players;

    public ThirtyOneGame(List<Player> players) {
        this.players = players;
        gameContext = new GameContext();
        gameContext.setPlayers(players);
    }

    @Override
    public GameResult play() {
        GameResult gameResult = new GameResult();

        Deck deck = getStartDeck();
        deck.shuffle();

        Dealer dealer = determineDealer(deck);


        while(players.size() >= 2) {

            try {
                ThirtyOneRound round = new ThirtyOneRound(dealer, players, gameContext);
                Result result = round.play();

                gameResult.addRound(result);
                List<Player> payers = result.getLosers();
                if (payers == null) {
                    LOGGER.error("payers in null. result=" + result);
                }
                payers.forEach(p -> {
                    if (p != null)
                        p.pay();
                });
                LOGGER.debug("payers = " + payers + "\n\n");
            }
            catch (RuntimeException e) {
                LOGGER.error("Round failed", e);
            }

            //deck = getStartDeck();
            deck.shuffle();
            dealer = passDealerRole(dealer, deck);

            if (players.removeIf(player -> !player.stillInGame())) {
                LOGGER.info("players left in game are " + players.stream().map(Player::getName).collect
                        (Collectors.joining(", ")));
            }

        }

        Player winner = figureOutWinner(players);
        gameResult.setWinner(winner);

        LOGGER.info("**************** winner = " + winner + " ************************");
        players.stream().forEach(player -> LOGGER.debug("player = " + player));

        return gameResult;
    }

    private Dealer passDealerRole(Dealer currentDealer, Deck deck) {
        Dealer dealer = new Dealer(gameContext.getNextPlayer(currentDealer.asPlayer()), deck);
        return dealer;
    }

    private Player figureOutWinner(List<Player> players) {
        return players.stream().filter(Player::stillInGame).findFirst().orElseThrow(() -> new
                RuntimeException("Could not find winner"));
    }

    private boolean atLeast2PlayersAreStillIn(final List<Player> players) {
        return players.stream().filter(Player::stillInGame).count() > 1;
    }

    private Dealer determineDealer(Deck deck) {
        final Player randomPlayer = RandomUtils.getRandom(players);
        Dealer dealer = new Dealer(randomPlayer, deck);
        return dealer;
    }
//
//    private void listHands() {
//        for (Player player : players) {
//            LOGGER.debug(player);
//        }
//    }
//
    public Deck getStartDeck() {
        return new StandardDeck();
    }
}
