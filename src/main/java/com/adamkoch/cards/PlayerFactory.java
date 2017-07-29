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
        players.add(makeJoel());
        players.add(makeAlyssa());
        players.add(makeMom());
        players.add(makeDad());
        return players;
    }

    private static Player makeDad() {
        Player dad = new Player("Dad") {
            @Override
            protected List<Card> rank(List<? extends Card> cards, List<Card> hand) {

                if (cards.size() > 1) {
                    cards.sort(new LowestRankComparator());
                }
                return (List<Card>) cards;
            }
        };
        return dad;
    }

    private static Player makeMom() {
        Player mom = new Player("Mom") {
            @Override
            protected List<Card> rank(List<? extends Card> cards, List<Card> hand) {

                if (cards.size() > 1) {
                    cards.sort(new Comparator<Card>() {
                        @Override
                        public int compare(Card card1, Card card2) {
                            int numberOfCardsAfter1 = Math.abs(7 - card1.getRank());
                            int numberOfCardsAfter2 = Math.abs(7 - card2.getRank());
                            return numberOfCardsAfter2 - numberOfCardsAfter1;
                        }

                        @Override
                        public boolean equals(Object obj) {
                            return this.equals(obj);
                        }
                    });
                }
                return (List<Card>) cards;
            }

        };
        return mom;
    }

    private static Player makeAlyssa() {
        Player alyssa = new Player("Alyssa") {
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

    private static Player makeJoel() {
        Player joel = new Player("Joel") {
            @Override
            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
                // first card
                return (List<Card>) cardsThatCanPlay;
            }
        };
        return joel;
    }

}
