package com.adamkoch.cards;

import com.adamkoch.cards.utils.CardUtil;
import com.adamkoch.utils.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * <p>Created by aakoch on 2017-08-01.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Determiner {

    private static final Logger LOGGER = LogManager.getLogger(Determiner.class);

    public static boolean discardingCardWillResultInAnotherPlayerGetting31(Card card) {
        return false;
    }

    public boolean cardWouldImproveHand(Card card, List<Card> cards, GameContext gameContext) {
        final boolean[] isLowestCard = {false};
        final Map<Suit, Integer> numberOfCardsPerSuit = CardUtil.getNumberOfCardsPerSuit(cards);
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
        Map<Suit, Integer> map = CardUtil.getNumberOfCardsPerSuit(newHand);
        if (map.size() == 2 && map.containsValue(Integer.valueOf(2))) {
            boolean newPairBetter;

            newHand.sort((card1, card2) -> card2.getSuit().ordinal() - card1.getSuit().ordinal());

            List<Card> pair1 = newHand.subList(0, 2);
            List<Card> pair2 = newHand.subList(2, 4);
            int total1 = Calculator.totalCards(pair1);
            int total2 = Calculator.totalCards(pair2);

            if (pair1.contains(card)) {
                newPairBetter = total1 > total2;
            }
            else {
                newPairBetter = total2 > total1;
            }

            return newPairBetter;
        }
        else {
            return false;
        }
    }

    public Card chooseCardToDiscard(Player player, GameContext gameContext) {
        final List<Card> cards = player.getHand();

        assert (cards.size() == 4);

        List<Card> returnedCards = new ArrayList<>(cards);

        Chain chain = player.getChain(gameContext);
        while (chain.hasNext() && returnedCards.size() != 1) {
            returnedCards = chain.nextRule(returnedCards);
        }

        return returnedCards.get(0);
    }

}
