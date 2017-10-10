package com.adamkoch.cards.loveletter;

import java.util.ArrayList;
import java.util.Collections;
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
            final Player player = createPlayer();
            player.setHand(card);
            list.add(player);
        }
        return list;
    }

    public static Player createPlayer() {
        return new SingleCardHandPlayer();
    }

    public static Game withNPlayers(int numberOfPlayers) {
        final Game game = new Game();
        game.setPlayers(createPlayers(numberOfPlayers));
        return game;
    }

    private static List<Player> createPlayers(int numberOfPlayers) {
        List<Player> list = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            final Player player = createPlayer();
            list.add(player);
        }
        return list;
    }
}
