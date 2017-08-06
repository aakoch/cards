package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <p>Created by aakoch on 2017-07-26.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class PlayerFactory {
    private static final Logger LOGGER = LogManager.getLogger(PlayerFactory.class);

    private static List<String> names = Arrays.asList("Watermelon",
            "Cool wHip", "Ace", "C$", "Taz", "Pumpkin", "Shorty", "Bob",  "Mumbles", "Steve");
    private static Queue<String> namesQueue = new ConcurrentLinkedQueue<>(names);

    public static List<Player> initializePlayers(int numberOfInstances, int startKnockLimit) {
        List<Player> players = new ArrayList<>(numberOfInstances);

        for (int i = 0; i < numberOfInstances; i++) {
            players.add(new StalemateBreakingPlayer(randomName(), startKnockLimit + i));
        }
        return players;
    }

//    private static Player makeLowest() {
//        Player player = new ComparatorPlayer("Lowest", new LowestRankComparator());
//        return player;
//    }
//
//    private static Player makeHighest() {
//        Player player = new ComparatorPlayer("Highest", new HighestRankComparator());
//        return player;
//    }
//
//    private static Player makeDistance() {
//        Player player = new ComparatorPlayer("Farthest from 7", new DistanceFrom7Comparator());
//        return player;
//    }

//    private static Player makeReverse() {
//        Player player = new Player("Reverse") {
//            private Iterator<Card> handIterator;
//            private List<Card> discardPile;
//
//            @Override
//            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
//                Collections.reverse(cardsThatCanPlay);
//                return (List<Card>) cardsThatCanPlay;
//            }
//
//            @Override
//            public Card chooseWhichCardToDiscard(DrawPile drawPile, DiscardPile discardPile) {
//                return null;
//            }
//        };
//        return player;
//    }

//    private static Player makeFirst() {
//        Player player = new Player("First") {
//            private Iterator<Card> handIterator;
//            private List<Card> discardPile;
//
//            @Override
//            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
//                // first card
//                return (List<Card>) cardsThatCanPlay;
//            }
//
//            @Override
//            public Card chooseWhichCardToDiscard(DrawPile drawPile, DiscardPile discardPile) {
//                return null;
//            }
//        };
//        return player;
//    }

//    private static Player makeRandom() {
//        Player player = new Player(randomName()) {
//            private Iterator<Card> handIterator;
//            private List<Card> discardPile;
//
//            @Override
//            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
//                Collections.shuffle(cardsThatCanPlay);
//                return (List<Card>) cardsThatCanPlay;
//            }
//
//            @Override
//            public Card chooseWhichCardToDiscard(DrawPile drawPile, DiscardPile discardPile) {
//
//                if (super.discardCard != null) {
//                    Card temp =  discardCard;
//                    discardCard = null;
//                    return temp;
//                }
//
//
//                return RandomUtils.removeRandom(hand);
//            }
//        };
//        return player;
//    }

    private static Player makeEasy() {
        Player player = new EasyPlayer(randomName(), 20);
        return player;
    }

    private static String randomName() {
        return namesQueue.remove();
    }

//    private static Player makeCount() {
//        Player player = new Player("Count") {
//            private Iterator<Card> handIterator;
//            private List<Card> discardPile;
//
//            @Override
//            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
//                Map<Card, Integer> map = cardsThatCanPlay.parallelStream().collect(
//                        Collectors.toMap(card -> card, card -> findNumberOfCardsBehind(card, hand)));
//
//                Collections.sort(cardsThatCanPlay, new Comparator<Card>() {
//                    @Override
//                    public int compare(Card o1, Card o2) {
//                        int card1Count = map.get(o1);
//                        int card2Count = map.get(o2);
//                        return card2Count - card1Count;
//                    }
//                });
//
//                return (List<Card>) cardsThatCanPlay;
//            }
//
//            @Override
//            public Card chooseWhichCardToDiscard(DrawPile drawPile, DiscardPile discardPile) {
//                return null;
//            }
//        };
//        return player;
//    }


    private static int findNumberOfCardsBehind(Card card, List<Card> cards) {
        return (int) cards.stream().filter(_card -> after(card, _card)).count();
    }

//    private static Player makeCountAndDistance() {
//        Player player = new Player("Count and Distance") {
//            private Iterator<Card> handIterator;
//            private List<Card> discardPile;
//
//            @Override
//            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
//                Map<Card, Integer> map = cardsThatCanPlay.parallelStream().collect(
//                        Collectors.toMap(card -> card, card -> findNumberOfCardsBehind(card, hand)));
//
//                Collections.sort(cardsThatCanPlay, new Comparator<Card>() {
//                    @Override
//                    public int compare(Card card1, Card card2) {
//                        int card1Count = map.get(card1);
//                        int card2Count = map.get(card2);
//                        final int count = card2Count - card1Count;
//                        if (count == 0) {
//                            int numberOfCardsAfter1 = Math.abs(7 - card1.getRank().getNumericRank(false));
//                            int numberOfCardsAfter2 = Math.abs(7 - card2.getRank().getNumericRank(false));
//                            return numberOfCardsAfter2 - numberOfCardsAfter1;
//                        }
//                        return count;
//                    }
//                });
//
//                return (List<Card>) cardsThatCanPlay;
//            }
//
//            @Override
//            public Card chooseWhichCardToDiscard(DrawPile drawPile, DiscardPile discardPile) {
//                return null;
//            }
//        };
//        return player;
//    }


//    private static Player makeDistanceAndCount() {
//        Player player = new Player("Distance and Count") {
//            private Iterator<Card> handIterator;
//            private List<Card> discardPile;
//
//            @Override
//            protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
//                Map<Card, Integer> map = cardsThatCanPlay.parallelStream().collect(
//                        Collectors.toMap(card -> card, card -> findNumberOfCardsBehind(card, hand)));
//
//                Collections.sort(cardsThatCanPlay, new Comparator<Card>() {
//                    @Override
//                    public int compare(Card card1, Card card2) {
//                        int numberOfCardsAfter1 = Math.abs(7 - card1.getRank().getNumericRank(false));
//                        int numberOfCardsAfter2 = Math.abs(7 - card2.getRank().getNumericRank(false));
//                        final int numberOfCards = numberOfCardsAfter2 - numberOfCardsAfter1;
//                        if (numberOfCards == 0) {
//
//                            int card1Count = map.get(card1);
//                            int card2Count = map.get(card2);
//                            return card2Count - card1Count;
//                        }
//                        return numberOfCards;
//                    }
//                });
//
//                return (List<Card>) cardsThatCanPlay;
//            }
//
//            @Override
//            public Card chooseWhichCardToDiscard(DrawPile drawPile, DiscardPile discardPile) {
//                return null;
//            }
//        };
//        return player;
//    }

    public static boolean after(Card card, Card cardFromHand) {
        boolean after = false;
        if (card.getSuit() == cardFromHand.getSuit()) {
            final int cardRank = card.getRank().getNumericRank(false);
            if (cardRank == 7) {
                after = true;
            } else {
                final int cardFromHandRank = cardFromHand.getRank().getNumericRank(false);
                if (cardRank > 7 && cardFromHandRank - cardRank > 0) {
                    after = true;
                } else if (cardRank < 7 && cardRank - cardFromHandRank > 0) {
                    after = true;
                }
            }
        }
        return after;
    }

}
