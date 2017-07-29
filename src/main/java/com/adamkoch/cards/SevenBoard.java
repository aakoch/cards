package com.adamkoch.cards;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Created by aakoch on 2017-07-27.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class SevenBoard {
    private List<SevenStack> stacks = Arrays.stream(Suit.values()).map(SevenStack::new).collect(Collectors.toList());

    public void play(Player player) {
        if (stacks.isEmpty()) {
            Card card = player.play7OfHearts();
            System.out.println(player.getName() + " playing " + card);
            SevenStack stack = new SevenStack(card.getSuit());
            stack.addCard(card);
            stacks.add(stack);
        }
        else {
            List<Card> possiblePlays = getPossiblePlays();
            Card cardToPlay = player.determineCardToPlay(possiblePlays);
            if (cardToPlay == null) {
                System.out.println(player.getName() + " can't play");
            }
            else {
                System.out.println(player.getName() + " playing " + cardToPlay);
                player.removeFromHand(cardToPlay);
                playCard(cardToPlay);
            }
        }
    }


    private void playCard(Card card) {
        SevenStack stack = stacks.parallelStream().filter(_stack -> card.getSuit() == _stack.getSuit
                ()).findFirst().orElse(new SevenStack(card.getSuit()));
        stack.addCard(card);
    }


    private List<Card> getPossiblePlays() {
        List<Card> cards = new ArrayList<>();
        for (SevenStack stack : stacks) {
            if (stack.isStarted()) {
                Card topCard = stack.getTopCard();
                Card bottomCard = stack.getBottomCard();
                if (topCard.getRank() < 13) {
                    cards.add(new Card(stack.getSuit(), topCard.getRank() + 1));
                }
                if (bottomCard.getRank() > 1) {
                    cards.add(new Card(stack.getSuit(), bottomCard.getRank() - 1));
                }
            }
            else {
                cards.add(new Card(stack.getSuit(), 7));
            }
        }

        return cards;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (SevenStack stack : stacks) {
            sb.append(stack.getSuit());
            sb.append(":");
            if (stack.isStarted()) {
                sb.append(stack.getBottomCard().formatRank());
                sb.append("--");
                sb.append(stack.getTopCard().formatRank());
            }
            else {
                sb.append("not started");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
