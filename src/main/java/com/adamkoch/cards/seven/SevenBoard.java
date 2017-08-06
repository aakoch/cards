package com.adamkoch.cards.seven;

import com.adamkoch.cards.*;
import com.adamkoch.cards.utils.CharacterConverter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Created by aakoch on 2017-07-27.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class SevenBoard {
    private List<com.adamkoch.cards.seven.SevenStack> stacks = Arrays.stream(Suit.standardSuits()).map(
            com.adamkoch.cards.seven.SevenStack::new).collect(Collectors.toList());


    public void playCard(Card card) {
        com.adamkoch.cards.seven.SevenStack stack = stacks.stream().filter(_stack -> card.getSuit() == _stack.getSuit
                ()).findFirst().orElse(new com.adamkoch.cards.seven.SevenStack(card.getSuit()));
        stack.addCard(card);
    }

    public List<Card> getPossiblePlays() {
        List<Card> cards = new ArrayList<>();
        for (com.adamkoch.cards.seven.SevenStack stack : stacks) {
            if (stack.isStarted()) {
                Card topCard = stack.getTopCard();
                Card bottomCard = stack.getBottomCard();
                if (topCard.getRank().lessThan(Rank.KING)) {
                    cards.add(new Card(topCard.getRank().offsetRank(1), stack.getSuit()));
                }
                if (bottomCard.getRank().greaterThan(Rank.ACE)) {
                    cards.add(new Card(bottomCard.getRank().offsetRank(-1), stack.getSuit()));
                }
            }
            else {
                cards.add(new Card(Rank.SEVEN, stack.getSuit()));
            }
        }

        return cards;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (com.adamkoch.cards.seven.SevenStack stack : stacks) {
//            sb.append(stack.getSuit());
//            sb.append(":");
            if (stack.isStarted()) {
                sb.append(CharacterConverter.getUnicode(stack.getBottomCard()));
                sb.append("-");
                sb.append(CharacterConverter.getUnicode(stack.getTopCard()));
            }
            else {
                sb.append("not started");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public boolean isEmpty() {
        return stacks.isEmpty();
    }

    public void startStack(Suit suit) {
        com.adamkoch.cards.seven.SevenStack stack = new com.adamkoch.cards.seven.SevenStack(suit);
        Card card = new Card(Rank.SEVEN, suit);
        stack.addCard(card);
        stacks.add(stack);
    }
}
