package com.adamkoch.cards;

import com.adamkoch.cards.utils.CardUtil;
import com.adamkoch.utils.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

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
        return cards -> {
            if (cards.size() == 4) {
                Map<Suit, Integer> map = CardUtil.getNumberOfCardsPerSuit(cards);
                if (map.size() == 2 && map.containsValue(Integer.valueOf(2))) {

                    cards.sort((card1, card2) -> card2.getSuit().ordinal() - card1.getSuit().ordinal());

                    List<Card> pair1 = cards.subList(0, 2);
                    List<Card> pair2 = cards.subList(2, 4);
                    int total1 = Calculator.totalCards(pair1);
                    int total2 = Calculator.totalCards(pair2);

                    if (total1 > total2) {
                        pair2.sort(Comparator.comparingInt(card -> card.getRank().getValue(true)));
                        return Collections.singletonList(pair2.get(0));
                    }
                    else if (total2 > total1) {
                        pair1.sort(Comparator.comparingInt(card -> card.getRank().getValue(true)));
                        return Collections.singletonList(pair1.get(0));
                    }
                }
            }

            return cards;
        };
    }

    public static Rule createGameContextRule(GameContext gameContext) {
        return cards -> {
            LOGGER.debug("gameContext = " + gameContext);
            return cards;
        };
    }

    public static Rule createKeepTensRule() {
        return cards -> {
            Map<Suit, List<Card>> map = CardUtil.createSuitListMap(cards);
            List<Card> list = new ArrayList<>();
            if (map.size() == 2) {
                list = rankAndPickCardsToDiscard(map);
            }

            return list;
        };
    }

    public static List<Card> rankAndPickCardsToDiscard(Map<Suit, List<Card>> map) {

        // TODO: this needs work
        return map.entrySet()
                  .stream()
                  .collect(Collectors.toMap(entry -> Calculator.totalCards(entry.getValue()), entry ->
                          entry.getValue()))
                  .entrySet()
                  .stream()
                  .sorted(Comparator.comparing((Map.Entry<Integer, List<Card>> entry) -> entry.getKey()))
                  .findFirst()
                  .orElseThrow(RuntimeException::new)
                  .getValue();

    }

    public static Rule createRandomRule() {
        return cards -> {
            final List<Card> randomCards = Collections.singletonList(RandomUtils.removeRandom(cards));
            LOGGER.debug("randomCards = " + randomCards);
            return randomCards;
        };
    }

    public static Rule createOddSuitRule() {
        return cards -> {
            if (cards.size() == 4) {
                List<Card> returnCandidates = new ArrayList<>(cards);
                Map<Suit, Integer> numberOfCardsPerSuit = CardUtil.getNumberOfCardsPerSuit(cards);
                if (CardUtil.threeOfOneSuit(numberOfCardsPerSuit)) {
                    final Card oddSuit = CardUtil.oddSuit(cards, numberOfCardsPerSuit);
                    if (!discardingCardWillResultInAnotherPlayerGetting31(oddSuit)) {
                        returnCandidates.clear();
                        returnCandidates.add(oddSuit);
                    }
                }
                else if (CardUtil.twoOfOneSuit(numberOfCardsPerSuit)) {
                    Suit suitWith2Cards = CardUtil.findSuitWith2Cards(numberOfCardsPerSuit);
                    final List<Card> cardsOfCertainSuit = CardUtil.findCardsWithSuit(cards, suitWith2Cards);
//                    if (!discardingCardWillResultInAnotherPlayerGetting31(oddSuit)) {
                    returnCandidates.removeAll(cardsOfCertainSuit);
//                    }
                }
                return returnCandidates;
            }
            return cards;
        };
    }

    /**
     * The card with the lowest rank is returned.
     */
    public static Rule createNumericRankRule() {
        return cards -> {
            cards.sort(Comparator.comparingInt(o -> o.getRank().getNumericRank(true)));
            return cards.subList(0, 1);
        };
    }

    /**
     * If the cards are all the same rank, choose the last one as that is the one drawn.
     */
    public static Rule createSameRankChooseLastRule() {
        return cards -> {
            Rank rank = cards.get(0).getRank();
            boolean allSame = cards.stream().allMatch(card -> card.getRank() == rank);
            if (allSame) {
                return Collections.singletonList(cards.get(cards.size() - 1));
            }
            return cards;
        };
    }
}
