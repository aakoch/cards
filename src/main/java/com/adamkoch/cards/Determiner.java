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
    private final Player player;

    public Determiner(Player player) {
        this.player = player;
    }

    public Card chooseCardToDiscard(final List<Card> cards, GameContext gameContext) {

        assert (cards.size() == 4);

        List<Card> returnedCards = new ArrayList<>(cards);

        Chain chain = player.getChain(gameContext);
        while (chain.hasNext() && returnedCards.size() != 1) {
            returnedCards = chain.nextRule(returnedCards);
        }

        return returnedCards.get(0);
    }


    public static List<Card> findCardsWithSuit(List<Card> cards, Suit suit) {
        List<Card> cardsWithSuit = new ArrayList<>();
        for (Card card : cards) {
            if (card.getSuit() == suit) {
                cardsWithSuit.add(card);
            }
        }

        return cardsWithSuit;
    }

    public static Suit findSuitWith2Cards(Map<Suit, Integer> numberOfCardsPerSuit) {
        for (Map.Entry<Suit, Integer> entry : numberOfCardsPerSuit.entrySet()) {
            if (entry.getValue().intValue() == 2) {
                return entry.getKey();
            }
        }

        return null;
    }


    public static boolean discardingCardWillResultInAnotherPlayerGetting31(Card card) {
        return false;
    }


    public static Card oddSuit(List<Card> cards, Map<Suit, Integer> numberOfCardsPerSuit) {
        Suit oddSuit = null;
        try {
            oddSuit = getOddSuit(numberOfCardsPerSuit);
        }
        catch (RuntimeException e) {
            LOGGER.error("threeOfOneSuit(numberOfCardsPerSuit) returned true for " + numberOfCardsPerSuit +
                    "\ncards="+ cards,e);

        }
        Suit finalOddSuit = oddSuit;
        return cards.stream()
                    .filter(card -> card.getSuit() == finalOddSuit)
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
                                   .orElseThrow(() -> new RuntimeException("Could not find odd suit from " + numberOfCardsPerSuit))
                                   .getKey();
    }

    public static boolean threeOfOneSuit(Map<Suit, Integer> numberOfCardsPerSuit) {
        return numberOfCardsPerSuit.values().contains(3);
    }

    public static boolean twoOfOneSuit(Map<Suit, Integer> numberOfCardsPerSuit) {
        return numberOfCardsPerSuit.values().contains(2) && numberOfCardsPerSuit.size() != 2;
    }

    public static Map<Suit, Integer> getNumberOfCardsPerSuit(List<Card> cards) {
        return cards.stream().collect(Collectors.toMap(Card::getSuit, card -> 1, Integer::sum));
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

        final boolean[] isLowestCard = {false};
        final Map<Suit, Integer> numberOfCardsPerSuit = Determiner.getNumberOfCardsPerSuit(cards);
        if (numberOfCardsPerSuit.size() == 1) {
            final Optional<Card> min = cards.stream().min(Comparator.comparing(Card::getRank));
            min.ifPresent(card1 -> isLowestCard[0] = true);
        }

        if (isLowestCard[0]) {
            return false;
        }
        final int currentCardsTotal = Calculator.totalCards(cards);
        final int candidateCardsTotal = Calculator.totalCards(ListUtils.concat(cards, card));
        return currentCardsTotal < candidateCardsTotal;

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
