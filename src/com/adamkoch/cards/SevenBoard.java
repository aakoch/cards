package com.adamkoch.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <a href=""></a>
 *
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
            List<Card> intersection = intersect(possiblePlays, player.getHand().cards());
            Card cardToPlay = determineCardToPlay(intersection, player.getHand().cards());
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

    private Card determineCardToPlay(List<Card> cardsThatCanPlay, List<Card> hand) {
        return cardsThatCanPlay.isEmpty() ? null : rank(cardsThatCanPlay, hand).get(0);
    }

    private List<Card> rank(List<Card> cards, List<Card> hand) {
        if (cards.size() > 1) {
            System.out.println("before: cards = " + cards);
            cards.sort(new Comparator<Card>() {
                @Override
                public int compare(Card card1, Card card2) {
                    int numberOfCardsAfter1 = Math.abs(7 - card1.getRank());
                    int numberOfCardsAfter2 = Math.abs(7 - card2.getRank());
                    return numberOfCardsAfter2 - numberOfCardsAfter1;
                }

                @Override
                public boolean equals(Object obj) {
                    return this.equals(obj);
                }
            });
            System.out.println("after: cards = " + cards);
        }
        return cards;
    }

    private List<Card> intersect(List<Card> playableCards, List<Card> playerHand) {
        List<Card> cards = new ArrayList<>();

        for (Card card : playableCards) {
            if (playerHand.contains(card)) {
                cards.add(card);
            }
        }

        return cards;
    }

    private List<Card> getPossiblePlays() {
        List<Card> cards = new ArrayList<>();
        for (SevenStack stack : stacks) {
            if (stack.isStarted()) {
                Card topCard = stack.getTopCard();
                Card bottomCard = stack.getBottomCard();
                cards.add(new Card(stack.getSuit(), topCard.getRank() + 1));
                cards.add(new Card(stack.getSuit(), bottomCard.getRank() - 1));
            }
            else {
                cards.add(new Card(stack.getSuit(), 7));
            }
        }

        return cards;
    }

}
