package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.adamkoch.cards.Determiner.*;

/**
 * <p>Created by aakoch on 2017-08-05.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class RuleFactory {

    private static final Logger LOGGER = LogManager.getLogger(RuleFactory.class);

    public static Rule createComparePairsRule() {
        return new Rule() {
            @Override
            public List<Card> apply(List<Card> cards) {

                if (cards.size() == 4) {
                    Map<Suit, Integer> map = getNumberOfCardsPerSuit(cards);
                    if (map.size() == 2 && map.containsValue(Integer.valueOf(2))) {

                        cards.sort((card1, card2) -> card2.getSuit().ordinal() - card1.getSuit().ordinal());

                        List<Card> pair1 = cards.subList(0, 2);
                        List<Card> pair2 = cards.subList(2, 4);
                        int total1 = Calculator.totalCards(pair1);
                        int total2 = Calculator.totalCards(pair2);

                        if (total1 > total2) {
                            pair2.sort(Comparator.comparingInt(card -> card.getRank().getValue(true)));
                            return Arrays.asList(pair2.get(0));
                        }
                        else if (total2 > total1) {
                            pair1.sort(Comparator.comparingInt(card -> card.getRank().getValue(true)));
                            return Arrays.asList(pair1.get(0));
                        }
                    }
                }

                return cards;
            }
        };
    }

    public static Rule createGameContextRule(GameContext gameContext) {
        return new Rule() {
            @Override
            public List<Card> apply(List<Card> cards) {
                LOGGER.debug("gameContext = " + gameContext);
                return cards;
            }
        };
    }

    public static Rule createKeepTensRule() {
        return new Rule() {
            @Override
            public List<Card> apply(List<Card> cards) {
                Map<Suit, List<Card>> map = createSuitListMap(cards);
                List<Card> list = new ArrayList<>();
                if (map.size() == 2) {
                    list = rankAndPickCardsToDiscard(map);
                }

                return list;
            }
        };
    }

    public static Map<Suit, List<Card>> createSuitListMap(List<Card> cards) {
        Map<Suit, List<Card>> map = new HashMap<>();
        for (Suit suit : Suit.standardSuits()) {
            map.put(suit, new ArrayList<>());
        }
        for (Card card : cards) {
            map.get(card.getSuit()).add(card);
        }
        for (Iterator<Map.Entry<Suit, List<Card>>> iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<Suit, List<Card>> entry = iterator.next();
            if (entry.getValue().isEmpty()) {
                iterator.remove();
            }
        }
        return map;
    }

    public static List<Card> rankAndPickCardsToDiscard(Map<Suit, List<Card>> map) {
        List<Card> list = new ArrayList<>();

        Map<Integer, List<Card>> map2 = new HashMap<>();

        for (Map.Entry<Suit, List<Card>> entry : map.entrySet()) {
            final List<Card> cards = entry.getValue();
            //int rank = calculateRankOfCardsWithSameSuitToBeComparedToOtherLists(cards);

            int rank = Calculator.totalCards(cards);
            map2.put(rank, cards);
        }

        final Map.Entry<Integer, List<Card>> integerListEntry = map2.entrySet()
                                                                    .stream()
                                                                    .sorted(Comparator.comparing((Map.Entry<Integer,
                                                                            List<Card>> entry) -> entry.getKey())
                                                                    )
                                                                    .findFirst()
                                                                    .orElseThrow(RuntimeException::new);

        return integerListEntry.getValue();
    }

    private static int calculateRankOfCardsWithSameSuitToBeComparedToOtherLists(List<Card> cards) {

        int rank = 0;

        for (Card card : cards) {
            final Rank cardRank = card.getRank();
            if (Arrays.asList(Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING).contains(cardRank)) {
                rank += 10;
            }
            rank += cardRank.value();
        }

        LOGGER.debug("rank = " + rank);

        return rank;
    }

    public static Rule createRandomRule() {
        return new Rule() {
            @Override
            public List<Card> apply(List<Card> cards) {
                return Arrays.asList(RandomUtils.removeRandom(cards));
            }
        };
    }

    public static Rule createOddSuitRule() {
        return new Rule() {
            @Override
            public List<Card> apply(List<Card> cards) {
                if (cards.size() == 4) {
                    List<Card> returnCandidates = new ArrayList<>(cards);
                    Map<Suit, Integer> numberOfCardsPerSuit = getNumberOfCardsPerSuit(cards);
                    if (threeOfOneSuit(numberOfCardsPerSuit)) {
                        final Card oddSuit = Determiner.oddSuit(cards, numberOfCardsPerSuit);
                        if (!discardingCardWillResultInAnotherPlayerGetting31(oddSuit)) {
                            returnCandidates.clear();
                            returnCandidates.add(oddSuit);
                        }
                    }
                    else if (twoOfOneSuit(numberOfCardsPerSuit)) {
                        Suit suitWith2Cards = findSuitWith2Cards(numberOfCardsPerSuit);
                        final List<Card> cardsOfCertainSuit = findCardsWithSuit(cards, suitWith2Cards);
//                    if (!discardingCardWillResultInAnotherPlayerGetting31(oddSuit)) {
                        returnCandidates.removeAll(cardsOfCertainSuit);
//                    }
                    }
                    return returnCandidates;
                }
                return cards;
            }
        };
    }

    public static Rule createNumericRankRule() {
        return new Rule() {

            @Override
            public List<Card> apply(List<Card> cards) {
                cards.sort(Comparator.comparingInt(o -> o.getRank().getNumericRank(true)));
                return cards.subList(0, 1);
            }
        };
    }

    /**
     * If the cards are all the same rank, choose the last one as that is the one drawn.
     */
    public static Rule createSameRankChooseLast() {
        return new Rule() {

            @Override
            public List<Card> apply(List<Card> cards) {
                Rank rank = cards.get(0).getRank();
                boolean allSame = cards.stream().allMatch(card -> card.getRank() == rank);
                if (allSame) {
                    return Arrays.asList(cards.get(cards.size() - 1));
                }
                return cards;
            }
        };
    }
}
