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
//    private static List<String> names = Arrays.asList("Steve", "Shorty", "Bob", "Watermelon", "Taz", "Ace", "Mumbles",
//            "Cool wHip");
//    private static List<String> names = Arrays.asList("Dad", "Mom", "Alyssa", "Joel");
//    public static List<Player> initializePlayers(int numberOfInstances) {
//        List<String> names2 = new ArrayList<>(names);
//        Collections.shuffle(names2);
//        List<Player> players = new ArrayList<>(numberOfInstances);
//        for (int i = 0; i < numberOfInstances; i++) {
//            final int index = (new Random()).nextInt(names2.size());
//            final String name = names2.remove(index);
//            players.add(new Player(name));
//        }
//        return players;
//    }

    public static List<Player> initializePlayers(int numberOfInstances) {
        List<Player> players = new ArrayList<>(numberOfInstances);
        players.add(makeHighest());

        // First: 17.26%, Random: 16.75%, Random: 16.67%, Random: 16.61%, Random: 16.36%, Random: 16.35%
        // Random: 17.57%, Reverse: 16.75%, Random: 16.59%, Random: 16.55%, Random: 16.34%, Random: 16.20%
        // Farthest from 7: 18.37%, Random: 17.20%, Random: 16.56%, Random: 16.07%, Random: 16.03%, Random: 15.77%
        // Random: 16.81%, Random: 16.80%, Lowest: 16.73%, Random: 16.68%, Random: 16.67%, Random: 16.31%
        // Random: 17.28%, Random: 16.86%, Random: 16.73%, Random: 16.46%, Highest: 16.35%, Random: 16.32%


        for (int i = 0; i < 5; i++) {
            players.add(makeRandom());
        }
//        players.add(makeReverse());
//        players.add(makeDistance());
//        players.add(makeLowest());
//        players.add(makeHighest());
        return players;
    }

    private static Player makeLowest() {
        Player dad = new ComparatorPlayer("Lowest", new LowestRankComparator());
        return dad;
    }

    private static Player makeHighest() {
        Player dad = new ComparatorPlayer("Highest", new HighestRankComparator());
        return dad;
    }

    private static Player makeDistance() {
        Player mom = new ComparatorPlayer("Farthest from 7", new DistanceFrom7Comparator());
        return mom;
    }

    private static Player makeReverse() {
        Player alyssa = new Player("Reverse") {
            @Override
            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
                // random
                //Collections.shuffle(cardsThatCanPlay);
                Collections.reverse(cardsThatCanPlay);
                return (List<Card>) cardsThatCanPlay;
            }
        };
        return alyssa;
    }

    private static Player makeFirst() {
        Player joel = new Player("First") {
            @Override
            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
                // first card
                return (List<Card>) cardsThatCanPlay;
            }
        };
        return joel;
    }

    private static Player makeRandom() {
        Player joel = new Player("Random") {
            @Override
            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
                Collections.shuffle(cardsThatCanPlay);
                return (List<Card>) cardsThatCanPlay;
            }
        };
        return joel;
    }

}
