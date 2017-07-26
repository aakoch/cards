package com.adamkoch.cards;

import java.util.ArrayList;
import java.util.List;

import static com.adamkoch.cards.Outcome.TIE;

/**
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class GameOfWar implements Game {

    List<Player> players;

    @Override
    public void play(Deck deck) {
        try {
            players = initializePlayers(2);

            deck.shuffle();

            Dealer dealer = new Dealer(deck.cards());
            dealer.dealTo(players);

            for (Player player : players) {
                System.out.println("start: player " + player.getName() + " hand size = " + player.getHandSize());
            }

            while (allPlayersStillHaveCards(players)) {
                play2(players, new ArrayList<Card>());

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
                final int player1HandSize = players.get(0).getHandSize();
                final int player2HandSize = players.get(1).getHandSize();
                System.out.println("end: player 1 hand size = " + player1HandSize);
                System.out.println("end: player 2 hand size = " + player2HandSize);
                final int player1DiscardPileSize = players.get(0).getDiscardPile().size();
                System.out.println("end: player 1 discard pile size = " + player1DiscardPileSize);
                final int player2DiscardPileSize = players.get(1).getDiscardPile().size();
                System.out.println("end: player 2 discard pile size = " + player2DiscardPileSize);
                System.out.println(
                        "total = " + (player1HandSize + player2HandSize + player1DiscardPileSize + player2DiscardPileSize));
            }
        } catch (RuntimeException e) {
            final int player1HandSize = players.get(0).getHandSize();
            final int player2HandSize = players.get(1).getHandSize();
            System.out.println("end: player 1 hand size = " + player1HandSize);
            System.out.println("end: player 2 hand size = " + player2HandSize);
            final int player1DiscardPileSize = players.get(0).getDiscardPile().size();
            System.out.println("end: player 1 discard pile size = " + player1DiscardPileSize);
            final int player2DiscardPileSize = players.get(1).getDiscardPile().size();
            System.out.println("end: player 2 discard pile size = " + player2DiscardPileSize);
            System.out.println(
                    "total = " + (player1HandSize + player2HandSize + player1DiscardPileSize + player2DiscardPileSize));
            throw e;
        }
    }

    private void play2(List<Player> players, ArrayList<Card> extraCards) {
        play(players.get(0), players.get(1), extraCards);
    }

    private boolean allPlayersStillHaveCards(List<Player> players) {
        return players.stream().allMatch(player -> player.hasAnotherCard());
    }

    private List<Player> initializePlayers(int numberOfInstances) {
        List<Player> players = new ArrayList<>(numberOfInstances);
        for (int i = 0; i < numberOfInstances; i++) {
            players.add(new Player(String.valueOf(i + 1)));
        }
        return players;
    }

    private void play(Player player1, Player player2, List<Card> extraCards) {
        Card player1Card = player1.getNextCard();
        Card player2Card = player2.getNextCard();

        Outcome outcome = determineWinner(player1Card, player2Card);

        if (outcome == TIE) {
            resolveTie(player1Card, player2Card, player1, player2, extraCards);
        } else if (outcome.getCard().equals(player1Card)) {
            System.out.println(player1Card + " <- " + player2Card);
            player1.getDiscardPile().add(player1Card);
            player1.getDiscardPile().add(player2Card);
            player1.getDiscardPile().addAll(extraCards);
        } else {
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
        } else if (card1.getRank() > card2.getRank()) {
            return new Outcome(card1);
        } else {
            return new Outcome(card2);
        }
    }
}
