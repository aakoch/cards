package com.adamkoch.cards;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.adamkoch.cards.Outcome.TIE;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class GameOfWar implements Game {

    Player player1;
    Player player2;

    @Override
    public void play(Deck deck) {
        try {
        player1 = new Player("1");
        player2 = new Player("2");

        deck.shuffle();

        Dealer dealer = new Dealer(deck.cards());
        dealer.dealTo(player1, player2);

            System.out.println("start: player 1 hand size = " + player1.getHandSize());
            System.out.println("start: player 2 hand size = " + player2.getHandSize());

        while (player1.hasAnotherCard() && player2.hasAnotherCard()) {
            play(player1, player2, new ArrayList<>());

//            if (!player1.hasAnotherCard()) {
//                player1.shuffleDiscardPileIntoHand();
//                player1Hand = player1.getHand();
//            }
//
//            if (!player2.hasAnotherCard()) {
//                System.out.println("player 2 ran out but has " + player2.getDiscardPile().size() + " cards in his " +
//                        "discard pile");
//                player2.shuffleDiscardPileIntoHand();
//                player2Hand = player2.getHand();
//            }
        }
        } catch (RuntimeException e) {
            final int player1HandSize = player1.getHandSize();
            final int player2HandSize = player2.getHandSize();
            System.out.println("end: player 1 hand size = " + player1HandSize);
            System.out.println("end: player 2 hand size = " + player2HandSize);
            final int player1DiscardPileSize = player1.getDiscardPile()
                                                      .size();
            System.out.println("end: player 1 discard pile size = " + player1DiscardPileSize);
            final int player2DiscardPileSize = player2.getDiscardPile()
                                                      .size();
            System.out.println("end: player 2 discard pile size = " + player2DiscardPileSize);
            System.out.println("total = " + (player1HandSize + player2HandSize + player1DiscardPileSize + player2DiscardPileSize));
            throw e;
        }
    }

    private void play(Player player1, Player player2, List<Card> extraCards) {
        Card player1Card = player1.getNextCard();
        Card player2Card = player2.getNextCard();

        Outcome outcome = determineWinner(player1Card, player2Card);

        if (outcome == TIE) {
            resolveTie(player1Card, player2Card, player1, player2, extraCards);
        }
        else if (outcome.getCard().equals(player1Card)) {
            System.out.println(player1Card + " <- " + player2Card);
            player1.getDiscardPile().add(player1Card);
            player1.getDiscardPile().add(player2Card);
            player1.getDiscardPile().addAll(extraCards);
        }
        else {
            System.out.println(player1Card + " -> " + player2Card);
            player2.getDiscardPile().add(player1Card);
            player2.getDiscardPile().add(player2Card);
            player2.getDiscardPile().addAll(extraCards);
        }
        if (!extraCards.isEmpty()) {
            System.out.println(extraCards);
            extraCards.clear();
        }
    }

    private void resolveTie(Card player1Card, Card player2Card, Player player1, Player player2, List<Card> hiddenCards) {
        System.out.println(player1Card + " == " + player2Card);

        hiddenCards.add(player1Card);
        hiddenCards.add(player2Card);
        hiddenCards.add(player1.getNextCard());
        hiddenCards.add(player1.getNextCard());
        hiddenCards.add(player2.getNextCard());
        hiddenCards.add(player2.getNextCard());

        play(player1, player2, hiddenCards);
    }

    private Outcome determineWinner(Card card1, Card card2) {
        if (card1.getRank() == card2.getRank()) {
            return TIE;
        }
        else if (card1.getRank() > card2.getRank()) {
            return new Outcome(card1);
        }
        else {
            return new Outcome(card2);
        }
    }
}
