package com.adamkoch.cards;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-26.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class PlayerFactory {
    public static List<Player> initializePlayers(int numberOfInstances) {
        List<Player> players = new ArrayList<>(numberOfInstances);
        for (int i = 0; i < numberOfInstances; i++) {
            players.add(new Player(String.valueOf(i + 1)));
        }
        return players;
    }
}
