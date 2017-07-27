package com.adamkoch.cards;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-26.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class ShoveIt implements Game {

    private static final boolean TRACE = false;
    private List<Player> players;

    public ShoveIt(int numberOfPlayers) {
        this.players = PlayerFactory.initializePlayers(numberOfPlayers);

    }

    @Override
    public void play(Deck deck) {
        Player currentDealer = randomPlayer();

        final Deck standardDeck = DeckFactory.standard();
        standardDeck.shuffle();

        while (standardDeck.cards().size() > players.size() + 1) {
            playRound(deck, currentDealer, standardDeck);
            trace("deck size = " + standardDeck.cards().size());
            System.out.println();
            int index = players.indexOf(currentDealer) + 1;
            if (index == players.size())
                index = 0;
            currentDealer.notDealer();
            currentDealer = players.get(index);
        }
    }

    private void trace(String msg) {
        if (TRACE)
        System.out.println(msg);
    }

    private void playRound(Deck deck, Player randomPlayer, Deck standardDeck) {
        Dealer dealer = randomPlayer.setAsDealer(standardDeck);
        dealer.dealTo(players, 1);

        for (Player player : players) {
            trace("start: player = " + player);
        }

        Queue<Player> playersInTurn = determineOrderOfPlayers(players, dealer);
        Player player;
        Decision previousDecision = null;
        while ((player = playersInTurn.poll()) != null) {
            final Decision decision = stayOrSwitch(player, previousDecision);
            previousDecision = decision;
            if (decision == Decision.SWITCH) {
                if (player.isDealer()) {
                    final Card card = deck.next();
                    System.out.println(player.getName()
                            + ", the dealer, discards " + player.getCard() + " and draws a " + card + " from the deck");
                    player.setCard(card);
                }
                else {
                    Card player1Card = player.getCard();
                    final Player player2 = playersInTurn.peek();
                    Card player2Card = player2.getCard();
                    if (player2Card.getRank() == 13) {
                        System.out.println(player.getName() + " wants to switch their " + player1Card + " " +
                                "with " +
                                player2.getName() + " but they have a king!");
                    }
                    else {
                        System.out.println(player.getName() + " decides to switch " + decision
                                .getReasonOrDefault(player1Card.toString()) + " and gets "+ player2Card + " from " +
                                player2.getName());
                        player.setCard(player2Card);
                        player2.setCard(player1Card);
                    }
                }
            }
            else {

                System.out.println(player.getName() + " decides to keep their " + player.getCard() +
                        decision.getReasonOrDefault(""));
            }
        }

//        Outcome outcome = determineWinner(players);
//        if (outcome == Outcome.TIE) {
//            System.out.println("TIE");
//        }
//        else
//            System.out.println("Winner is " + outcome.getWinner().getName());

        Outcome losingOutcome = determineLoser(players);
        if (losingOutcome == Outcome.TIE) {

            System.out.println("Tie between " + losingOutcome.getLosers() );
        }
        else
            System.out.println("Loser is " + losingOutcome.getLoser().getName() + " with " + losingOutcome.getLoser()
                    .getCard());

        for (Player player2 : players) {
            trace("end: player = " + player2);
            player2.clearHand();
        }
    }

    private Outcome determineLoser(List<Player> players) {
        Player lowestPlayer = null;
        Set<Player> lowestPlayers = new HashSet<>();

        for (Player player : players) {
            if (lowestPlayer == null) {
                lowestPlayer = player;
            }
            else if (lowestPlayer.getCard().getRank() == player.getCard().getRank()) {
                lowestPlayers.add(player);
            }
            else if (lowestPlayer.getCard().getRank() > player.getCard().getRank()) {
                lowestPlayer = player;
                lowestPlayers.clear();
            }
        }

        if (lowestPlayers.size() > 0) {
            final Outcome tie = Outcome.TIE;
            lowestPlayers.add(lowestPlayer);
            tie.setLosers(lowestPlayers);
            return tie;
        }
        else
            return new Outcome(lowestPlayer);
    }

    private Outcome determineWinner(List<Player> players) {
        Player highestPlayer = null;

        for (Player player : players) {
            if (highestPlayer == null) {
                highestPlayer = player;
            }
            else if (highestPlayer.getCard().getRank() == player.getCard().getRank()) {
                highestPlayer = null;
            }
            else if (highestPlayer.getCard().getRank() < player.getCard().getRank()) {
                highestPlayer = player;
            }
        }

        if (highestPlayer == null)
            return Outcome.TIE;
        else
            return new Outcome(highestPlayer);
    }

    private Queue<Player> determineOrderOfPlayers(List<Player> players, Dealer dealer) {
        int index = dealer.getIndexOfPlayerToLeftOfDealer(players);

        final ArrayBlockingQueue<Player> ordered = new ArrayBlockingQueue<>(players.size());
        for (int i = index; i < players.size(); i++) {
            ordered.add(players.get(i));
        }
        for (int i = 0; i < index; i++) {
            ordered.add(players.get(i));
        }

        return ordered;
    }


    private Decision stayOrSwitch(Player player, Decision previousDecision) {
        final Card card = player.getCard();
        if (player.wasSwapped() && player.previousCard().getRank() < card.getRank()) {
            return Decision.STAY.withReason(" because swapped card was lower");
        }
        else if ((previousDecision == null && card.getRank() > 7) || card
                .getRank() > 9)
            return Decision.STAY.withReason(null);
        else
            return Decision.SWITCH;
    }

    private Player randomPlayer() {
        return players.get((new Random()).nextInt(players.size()));
    }

    private enum Decision {
        STAY, SWITCH;

        private String reason;

        public Decision withReason(String reason) {
            this.reason = reason;
            return this;
        }


        public String getReasonOrDefault(String defaultReason) {
            return reason == null ? defaultReason : reason;
        }
    }
}
