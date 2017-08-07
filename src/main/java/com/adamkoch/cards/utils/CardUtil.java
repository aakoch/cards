package com.adamkoch.cards.utils;

import com.adamkoch.cards.Card;
import com.adamkoch.cards.Suit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Created by aakoch on 2017-08-05.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class CardUtil {
    private static final Logger LOGGER = LogManager.getLogger(CardUtil.class);

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

    public static Card oddSuit(List<Card> cards, Map<Suit, Integer> numberOfCardsPerSuit) {
        Suit oddSuit = null;
        try {
            oddSuit = getOddSuit(numberOfCardsPerSuit);
        }
        catch (RuntimeException e) {
            LOGGER.error("threeOfOneSuit(numberOfCardsPerSuit) returned true for " + numberOfCardsPerSuit +
                    "\ncards=" + cards, e);

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
                                   .orElseThrow(() -> new RuntimeException(
                                           "Could not find odd suit from " + numberOfCardsPerSuit))
                                   .getKey();
    }

    public static boolean threeOfOneSuit(Map<Suit, Integer> numberOfCardsPerSuit) {
        return numberOfCardsPerSuit.values().contains(3);
    }

    public static boolean twoOfOneSuit(Map<Suit, Integer> numberOfCardsPerSuit) {
        return numberOfCardsPerSuit.values().contains(2) && numberOfCardsPerSuit.size() != 2;
    }

    public static Map<Suit, Integer> getNumberOfCardsPerSuit(Collection<Card> cards) {
        return cards.stream().collect(Collectors.toMap(Card::getSuit, card -> 1, Integer::sum));
    }

    public static boolean addingCardWouldMake2OfTheSameSuit(Card card, List<Card> cards) {
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

    public static boolean addingCardWouldMake3OfTheSameSuit(Card card, List<Card> cards) {
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

    public static boolean handContainSuit(List<Card> cards, Suit suit) {
        return cards.stream().anyMatch(card -> card.getSuit() == suit);
    }

    public static Optional<Suit> findMajoritySuit(Collection<Card> cards) {
        return findSuit(cards, (entry1, entry2) -> entry2.getValue().intValue() - entry1.getValue().intValue());
    }

    public static Optional<Suit> findMinoritySuit(Collection<Card> cards) {
        return findSuit(cards, Comparator.comparingInt(Map.Entry::getValue));
    }

    protected static Optional<Suit> findSuit(Collection<Card> cards, Comparator<Map.Entry<Suit, Integer>> entryComparator) {
        try {
            final Map<Suit, Integer> numberOfCardsPerSuit = getNumberOfCardsPerSuit(cards);
            final Optional<Map.Entry<Suit, Integer>> first =
                    numberOfCardsPerSuit.entrySet()
                                        .stream()
                                        .sorted(entryComparator)
                                        .findFirst();


            return first.map(Map.Entry::getKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
