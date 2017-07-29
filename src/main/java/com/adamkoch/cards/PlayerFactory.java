package com.adamkoch.cards;

import java.util.*;
import java.util.stream.Collectors;

/**
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
        players.add(makeDistanceAndCount());

        // First: 17.26%, Random: 16.75%, Random: 16.67%, Random: 16.61%, Random: 16.36%, Random: 16.35%
        // Random: 17.57%, Reverse: 16.75%, Random: 16.59%, Random: 16.55%, Random: 16.34%, Random: 16.20%
        // Farthest from 7: 18.37%, Random: 17.20%, Random: 16.56%, Random: 16.07%, Random: 16.03%, Random: 15.77%
        // Random: 16.81%, Random: 16.80%, Lowest: 16.73%, Random: 16.68%, Random: 16.67%, Random: 16.31%
        // Random: 17.28%, Random: 16.86%, Random: 16.73%, Random: 16.46%, Highest: 16.35%, Random: 16.32%
        // Count: 21.23%, Random: 16.61%, Random: 16.00%, Random: 15.49%, Random: 15.37%, Random: 15.30%
        // Count and Distance: 25.33%, Random: 15.26%, Random: 15.02%, Random: 14.94%, Random: 14.76%, Random: 14.69%
        // Distance and Count: 21.15%, Random: 15.98%, Random: 15.86%, Random: 15.77%, Random: 15.69%, Random: 15.55%

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
        Player player = new ComparatorPlayer("Lowest", new LowestRankComparator());
        return player;
    }

    private static Player makeHighest() {
        Player player = new ComparatorPlayer("Highest", new HighestRankComparator());
        return player;
    }

    private static Player makeDistance() {
        Player player = new ComparatorPlayer("Farthest from 7", new DistanceFrom7Comparator());
        return player;
    }

    private static Player makeReverse() {
        Player player = new Player("Reverse") {
            @Override
            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
                Collections.reverse(cardsThatCanPlay);
                return (List<Card>) cardsThatCanPlay;
            }
        };
        return player;
    }

    private static Player makeFirst() {
        Player player = new Player("First") {
            @Override
            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
                // first card
                return (List<Card>) cardsThatCanPlay;
            }
        };
        return player;
    }

    private static Player makeRandom() {
        Player player = new Player("Random") {
            @Override
            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
                Collections.shuffle(cardsThatCanPlay);
                return (List<Card>) cardsThatCanPlay;
            }
        };
        return player;
    }

    private static Player makeCount() {
        Player player = new Player("Count") {
            @Override
            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
                Map<Card, Integer> map = cardsThatCanPlay.parallelStream().collect(
                        Collectors.toMap(card -> card, card -> findNumberOfCardsBehind(card, hand)));

                Collections.sort(cardsThatCanPlay, new Comparator<Card>() {
                    @Override
                    public int compare(Card o1, Card o2) {
                        int card1Count = map.get(o1);
                        int card2Count = map.get(o2);
                        return card2Count - card1Count;
                    }
                });

                return (List<Card>) cardsThatCanPlay;
            }
        };
        return player;
    }


    private static int findNumberOfCardsBehind(Card card, List<Card> cards) {
        return (int) cards.parallelStream().filter(_card -> after(card, _card)).count();
    }

    private static Player makeCountAndDistance() {
        Player player = new Player("Count and Distance") {
            @Override
            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
                Map<Card, Integer> map = cardsThatCanPlay.parallelStream().collect(
                        Collectors.toMap(card -> card, card -> findNumberOfCardsBehind(card, hand)));

                Collections.sort(cardsThatCanPlay, new Comparator<Card>() {
                    @Override
                    public int compare(Card card1, Card card2) {
                        int card1Count = map.get(card1);
                        int card2Count = map.get(card2);
                        final int count = card2Count - card1Count;
                        if (count == 0) {
                            int numberOfCardsAfter1 = Math.abs(7 - card1.getRank().innerRank);
                            int numberOfCardsAfter2 = Math.abs(7 - card2.getRank().innerRank);
                            return numberOfCardsAfter2 - numberOfCardsAfter1;
                        }
                        return count;
                    }
                });

                return (List<Card>) cardsThatCanPlay;
            }
        };
        return player;
    }


    private static Player makeDistanceAndCount() {
        Player player = new Player("Distance and Count") {
            @Override
            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
                Map<Card, Integer> map = cardsThatCanPlay.parallelStream().collect(
                        Collectors.toMap(card -> card, card -> findNumberOfCardsBehind(card, hand)));

                Collections.sort(cardsThatCanPlay, new Comparator<Card>() {
                    @Override
                    public int compare(Card card1, Card card2) {
                        int numberOfCardsAfter1 = Math.abs(7 - card1.getRank().innerRank);
                        int numberOfCardsAfter2 = Math.abs(7 - card2.getRank().innerRank);
                        final int numberOfCards = numberOfCardsAfter2 - numberOfCardsAfter1;
                        if (numberOfCards == 0) {

                            int card1Count = map.get(card1);
                            int card2Count = map.get(card2);
                            return card2Count - card1Count;
                        }
                        return numberOfCards;
                    }
                });

                return (List<Card>) cardsThatCanPlay;
            }
        };
        return player;
    }

    public static boolean after(Card card, Card cardFromHand) {
        boolean after = false;
        if (card.getSuit() == cardFromHand.getSuit()) {
            final int cardRank = card.getRank().innerRank;
            if (cardRank == 7) {
                after = true;
            }
            else {
                final int cardFromHandRank = cardFromHand.getRank().innerRank;
                if (cardRank > 7 && cardFromHandRank - cardRank > 0) {
                    after = true;
                }
                else if (cardRank < 7 && cardRank - cardFromHandRank > 0) {
                    after = true;
                }
            }
        }
        return after;
    }

}
