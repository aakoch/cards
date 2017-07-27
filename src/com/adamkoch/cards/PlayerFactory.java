package com.adamkoch.cards;

import java.util.*;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-26.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class PlayerFactory {
    private static List<String> names = Arrays.asList("Steve", "Shorty", "Taz", "Ace", "Mumbles", "Cool wHip");
    public static List<Player> initializePlayers(int numberOfInstances) {
        List<String> names2 = new ArrayList<>(names);
        Collections.shuffle(names2);
        List<Player> players = new ArrayList<>(numberOfInstances);
        for (int i = 0; i < numberOfInstances; i++) {
            final int index = (new Random()).nextInt(names2.size());
            final String name = names2.remove(index);
            players.add(new Player(name));
        }
        return players;
    }
}
