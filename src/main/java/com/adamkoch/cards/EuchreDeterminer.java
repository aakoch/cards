package com.adamkoch.cards;

import com.adamkoch.cards.utils.CardUtil;
import com.adamkoch.utils.SuitUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>Created by aakoch on 2017-09-04.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class EuchreDeterminer {
    public Bet shouldOrderUpTrump(List<Card> cards, Card topCard, boolean wouldGetTopCard) {
        int score = score(cards, topCard, wouldGetTopCard);
        Bet betOutcome;
        if (score >= 100) {
            betOutcome = Bet.PICK_UP_ALONE;
        }
        else if (score == 0) {
            betOutcome = Bet.PASS;
        }
        else {
            betOutcome = null;
        }
        return betOutcome;
    }

    public int score(List<Card> cards, Card topCard, boolean wouldGetTopCard) {
        int score = 0;
        final Map<Suit, Integer> numberOfCardsPerSuit = CardUtil.getNumberOfCardsPerSuit(cards);

        if (hasRightBower(topCard.getSuit(), cards) || (topCard.getRank() == Rank.JACK && wouldGetTopCard))
            score += 20;

        if (hasLeftBower(topCard.getSuit(), cards) || (topCard.getRank() == Rank.JACK && wouldGetTopCard))
            score += 20;

        if (numberOfCardsPerSuit.containsKey(topCard.getSuit())) {
            Integer numberOfSameSuit = numberOfCardsPerSuit.get(topCard.getSuit());

            score += numberOfSameSuit * 20;
        }
        else {
            score += 0;
        }
        return Math.min(score, 100);
    }

    public static boolean hasLeftBower(Suit suit, List<Card> cards) {
        Suit complementSuit = SuitUtils.getComplement(suit);
        return hasRightBower(complementSuit, cards);
    }

    public static boolean hasRightBower(Suit suit, List<Card> cards) {
        return cards.stream().anyMatch(card -> card.getSuit() == suit && card.getRank() == Rank.JACK);
    }

    public int score(List<Card> cards, Suit trump) {
        return cards.stream().mapToInt(card -> {
            return scoreSingleCard(card, trump);
        }).sum();
    }

    private int scoreSingleCard(Card card, Suit trump) {
        int score = 0;
        if (isRightBower(card, trump)) {
            score = 20;
        }
        else if (isLeftBower(card, trump)) {
            score = 15;
        }
        else if (card.getSuit() == trump) {
            score = 10;
        }

        score += card.getRank().getNumericRank(true);

        return score;
    }

    private boolean isLeftBower(Card card, Suit trump) {
        return card.getSuit() == SuitUtils.getComplement(trump) && card.getRank() == Rank.JACK;
    }

    private boolean isRightBower(Card card, Suit trump) {
        return card.getSuit() == trump && card.getRank() == Rank.JACK;
    }
}
