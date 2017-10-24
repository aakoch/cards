package com.adamkoch.cards.loveletter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by aakoch on 2017-10-07.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class GameCreater {

    public static Game withNPlayers(int numberOfPlayers, Card card) {
        final Game game = new Game();
        game.setPlayers(createPlayers(numberOfPlayers, card));
        return game;
    }

    private static List<Player> createPlayers(int numberOfPlayers, Card card) {
        List<Player> list = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            final Player player = createPlayer(i + 1);
            player.setHand(card);
            list.add(player);
        }
        return list;
    }

    public static Player createPlayer(int count) {
        return new SingleCardHandPlayer("Player " + count);
    }

    public static Game withNPlayers(int numberOfPlayers) {
        final Game game = new Game();
        final List<Player> players = createPlayers(numberOfPlayers);
        game.setPlayers(players);
        Dealer dealer = new Dealer(players.get(0));
        game.setDealer(dealer);
        game.dealCards();
        return game;
    }

    private static List<Player> createPlayers(int numberOfPlayers) {
        List<Player> list = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            final Player player = createPlayer(i + 1);
            list.add(player);
        }
        return list;
    }
}
