package com.adamkoch.cards;

import com.adamkoch.utils.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Created by aakoch on 2017-08-01.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Determiner {

    private static final Logger LOGGER = LogManager.getLogger(Determiner.class);

    public static Card chooseCardToDiscard(final List<Card> cards, GameContext gameContext) {

        assert (cards.size() == 4);

        Chain chain = new Chain();
        chain.addRule(createGameContextRule(gameContext));
        chain.addRule(createOddSuitRule());
        chain.addRule(createComparePairsRule());
        chain.addRule(createNumericRankRule());
        chain.addRule(createKeepTensRule());
        chain.addRule(createRandomRule(cards));

        List<Card> returnedCards = new ArrayList<>(cards);

        while (returnedCards.size() != 1) {
            returnedCards = chain.nextRule(returnedCards);
        }


        return returnedCards.get(0);
    }

    private static Rule createComparePairsRule() {
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

    private static Rule createGameContextRule(GameContext gameContext) {
        return new Rule() {
            @Override
            public List<Card> apply(List<Card> cards) {
                LOGGER.debug("gameContext = " + gameContext);
                return cards;
            }
        };
    }

    private static Rule createKeepTensRule() {
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

    private static Rule createRandomRule(List<Card> cards) {
        return new Rule() {
            @Override
            public List<Card> apply(List<Card> cards) {
                return Arrays.asList(RandomUtils.pickRandom(cards));
            }
        };
    }

    private static Rule createOddSuitRule() {
        return new Rule() {
            @Override
            public List<Card> apply(List<Card> cards) {
                List<Card> returnCandidates = new ArrayList<>(cards);
                Map<Suit, Integer> numberOfCardsPerSuit = getNumberOfCardsPerSuit(cards);
                if (threeOfOneSuit(numberOfCardsPerSuit)) {
                    final Card oddSuit = oddSuit(cards, numberOfCardsPerSuit);
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
        };
    }

    private static List<Card> findCardsWithSuit(List<Card> cards, Suit suit) {
        List<Card> cardsWithSuit = new ArrayList<>();
        for (Card card : cards) {
            if (card.getSuit() == suit) {
                cardsWithSuit.add(card);
            }
        }

        return cardsWithSuit;
    }

    private static Suit findSuitWith2Cards(Map<Suit, Integer> numberOfCardsPerSuit) {
        for (Map.Entry<Suit, Integer> entry : numberOfCardsPerSuit.entrySet()) {
            if (entry.getValue().intValue() == 2) {
                return entry.getKey();
            }
        }

        return null;
    }


    private static boolean discardingCardWillResultInAnotherPlayerGetting31(Card card) {
        return false;
    }


    private static Card oddSuit(List<Card> cards, Map<Suit, Integer> numberOfCardsPerSuit) {
        Suit oddSuit = getOddSuit(numberOfCardsPerSuit);
        return cards.stream()
                    .filter(card -> card.getSuit() == oddSuit)
                    .findFirst()
                    .orElseThrow(() -> {
                        IllegalStateException exception = new IllegalStateException();
                        return exception;
                    });
    }

    private static Suit getOddSuit(Map<Suit, Integer> numberOfCardsPerSuit) {
        return numberOfCardsPerSuit.entrySet()
                                   .stream()
                                   .filter(suitIntegerEntry -> suitIntegerEntry.getValue() == 1)
                                   .findFirst()
                                   .orElseThrow(RuntimeException::new)
                                   .getKey();
    }

    private static boolean threeOfOneSuit(Map<Suit, Integer> numberOfCardsPerSuit) {
        return numberOfCardsPerSuit.values().contains(3);
    }

    private static boolean twoOfOneSuit(Map<Suit, Integer> numberOfCardsPerSuit) {
        return numberOfCardsPerSuit.values().contains(2) && numberOfCardsPerSuit.size() != 2;
    }

    public static Map<Suit, Integer> getNumberOfCardsPerSuit(List<Card> cards) {
        return cards.stream().collect(Collectors.toMap(Card::getSuit, card -> 1, Integer::sum));
    }

    private static Rule createNumericRankRule() {
        return new Rule() {

            @Override
            public List<Card> apply(List<Card> cards) {
                cards.sort(Comparator.comparingInt(o -> o.getRank().getNumericRank(true)));
                return cards.subList(0, 1);
            }
        };
    }

    public static boolean cardWouldImproveHand(Card card, List<Card> cards, GameContext gameContext) {

//        final boolean chanceOfGettingBetterCardWithSameSuit;
//        if (gameContext == null) {
//            chanceOfGettingBetterCardWithSameSuit = false;
//        }
//        else {
//            final double percentOfGettingBetterCardWithSameSuit = gameContext.chanceOfGettingBetterCardWithSameSuit(card);
//            LOGGER.debug(String.format("chanceOfGettingBetterCardWithSameSuit=%.2f%%",
//                    percentOfGettingBetterCardWithSameSuit * 100));
//            chanceOfGettingBetterCardWithSameSuit = percentOfGettingBetterCardWithSameSuit > .18;
//        }

//        if (addingCardWouldMake3OfTheSameSuit(card, cards)) {
//            return true;
//        }
//        else if (addingCardWouldMake2OfTheSameSuit(card, cards)) {
//            // rank and choose
//            List<Card> newCardsList = new ArrayList<>(cards);
//            newCardsList.add(card);
//            Map<Suit, List<Card>> map = createSuitListMap(newCardsList);
//            List<Card> list = rankAndPickCardsToDiscard(map);
//
//            // if the discarded cards includes the card passed in, then return false
//            return list.contains(card);
//        }
//        else if (newPairBetterThanCurrentPair(cards, card)) {
//            return true;
//        }
//        else {
//            return false;
//        }
        return Calculator.totalCards(cards) < Calculator.totalCards(ListUtils.concat(cards, card));

    }

    public static boolean newPairBetterThanCurrentPair(List<Card> cards, Card card) {
        List<Card> newHand = new ArrayList<>(cards);
        newHand.add(card);
        Map<Suit, Integer> map = getNumberOfCardsPerSuit(newHand);
        if (map.size() == 2 && map.containsValue(Integer.valueOf(2))) {
            boolean b;

            newHand.sort((card1, card2) -> card2.getSuit().ordinal() - card1.getSuit().ordinal());

            List<Card> pair1 = newHand.subList(0, 2);
            List<Card> pair2 = newHand.subList(2, 4);
            int total1 = Calculator.totalCards(pair1);
            int total2 = Calculator.totalCards(pair2);

            if (pair1.contains(card)) {
                b = total1 > total2;
            }
            else {
                b = total2 > total1;
            }


            return b;
        }
        else {
            return false;
        }
    }

    private static boolean addingCardWouldMake2OfTheSameSuit(Card card, List<Card> cards) {
        if (alreadyHave3OfTheSameSuit(cards)) {
            return false;
        }
        List<Card> newHand = new ArrayList<>(cards);
        final boolean alreadyHave2CardsWithSameSuit = suitWithNumberOfCards(newHand, 2);

        if (alreadyHave2CardsWithSameSuit) {
            return false;
        }

        newHand.add(card);

        final boolean foundSuitWith2Cards = suitWithNumberOfCards(newHand, 2);
        return foundSuitWith2Cards;
    }

    private static boolean addingCardWouldMake3OfTheSameSuit(Card card, List<Card> cards) {
        if (alreadyHave3OfTheSameSuit(cards)) {
            return false;
        }
        List<Card> newHand = new ArrayList<>(cards);
        newHand.add(card);
        return areThreeCardsWithSameSuit(newHand);
    }

    private static boolean alreadyHave3OfTheSameSuit(List<Card> cards) {
        return areThreeCardsWithSameSuit(cards);
    }

    public static boolean areThreeCardsWithSameSuit(List<Card> cards) {
        return suitWithNumberOfCards(cards, 3);
    }

    private static boolean suitWithNumberOfCards(List<Card> cards, int numberOfCards) {
        final Map<Suit, Integer> numberOfCardsPerSuit = getNumberOfCardsPerSuit(cards);
        return numberOfCardsPerSuit.values().contains(numberOfCards);
    }
}
