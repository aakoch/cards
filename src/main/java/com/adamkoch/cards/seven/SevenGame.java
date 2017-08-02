package com.adamkoch.cards.seven;

import com.adamkoch.cards.*;
import com.adamkoch.utils.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

/**
 * <p>Created by aakoch on 2017-07-27.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class SevenGame implements Game {

    private static final Logger LOGGER = LogManager.getLogger(SevenBoard.class);

    private Players<Player> players;

    public SevenGame(Players players) {

        this.players = players;
    }

    @Override
    public Result play() {

        Player randomPlayer = players.pickRandomPlayer();
        final Deck deck = getStartDeck();
        deck.shuffle();

        randomPlayer.setAsDealer(deck);

        Dealer dealer = new Dealer(randomPlayer, deck);
        dealer.dealAllTo(players.list());

        final Queue<Player> playerQueue = determineOrderOfPlayers(this.players);

        for (Player player : playerQueue) {
            LOGGER.debug("player = " + player);
        }

        SevenBoard sevenBoard = new SevenBoard();
        Iterator<Player> r = ListUtils.constructRotator(playerQueue.stream().collect(Collectors.toList()), 0);
        while (everyoneHasCards(playerQueue)) {
            Player player = r.next();
            playTurn(player, sevenBoard);
            //System.out.println("player = " + player);

        }

        listHands();
        LOGGER.info(sevenBoard);

        Player winner = null;
        for (Player player : players.list()) {
            if (player.getHandSize() == 0) {
                winner = player;
                break;
            }

        }

        return new Result(winner);

    }

    public void playTurn(Player player, SevenBoard board) {

        if (board.isEmpty()) {
            Card card = player.play7OfHearts();
            LOGGER.info(player.getName() + " playing " + card);
            board.startStack(card.getSuit());
        }
        else {
            List<Card> possiblePlays = board.getPossiblePlays();
            Card cardToPlay = player.determineCardToPlay(possiblePlays);
            if (cardToPlay == null) {
                LOGGER.warn(player.getName() + " can't play");
            }
            else {
                LOGGER.info(player.getName() + " playing " + cardToPlay);
                player.removeFromHand(cardToPlay);
                board.playCard(cardToPlay);
            }
        }
    }

    private boolean everyoneHasCards(Queue<Player> playerQueue) {
        return playerQueue.stream().allMatch(player -> player.getHandSize() > 0);
    }


    private Queue<Player> determineOrderOfPlayers(Players<Player> players) {
        Player playerWith7OfHearts = players.list().parallelStream()
                                            .filter(player -> player.getHand()
                                                                    .cards()
                                                                    .contains(new Card(Suit.HEARTS, Rank.SEVEN)))
                                            .findFirst()
                                            .orElseThrow(
                                                    () -> new RuntimeException(
                                                            "No player with the 7 of Hearts"));


        int index = players.list().indexOf(playerWith7OfHearts);

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

    private void listHands() {
        for (Player player : players.list()) {
            LOGGER.debug(player);
        }
    }

    public Deck getStartDeck() {
        return new StandardDeck();
    }
}