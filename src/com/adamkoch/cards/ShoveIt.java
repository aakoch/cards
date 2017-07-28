package com.adamkoch.cards;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

/**
 * Shove It game. A game is once through a deck of cards or until there is a winner.
 *
 * <p>Created by aakoch on 2017-07-26.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class ShoveIt implements Game {

    private static final boolean TRACE = false;
    private List<ShoveItPlayer> players;

    public ShoveIt(int numberOfPlayers) {
        this.players = PlayerFactory.initializeShoveItPlayers(numberOfPlayers);
    }

    @Override
    public Stats play(Deck deck) {
        Stats stats = new Stats();
        Player currentDealer = randomPlayer();

        deck.shuffle();

        int rounds = 0;
        while (players.size() > 1 && deck.cards().size() > players.size() + 1) {
            rounds++;
            Outcome outcome = playRound(deck, currentDealer);
            List<ShoveItPlayer> playersOut = new ArrayList<>();
            for (Player player : outcome.getLosers()) {
                final ShoveItPlayer shoveItPlayer = (ShoveItPlayer) player;
                shoveItPlayer.pay();
//                System.out.println("shoveItPlayer.coins() = " + shoveItPlayer.coins());
                if (shoveItPlayer.coins() < 0) {
                    playersOut.add(shoveItPlayer);
                    System.out.println(shoveItPlayer.getName() + " is out");
                }
            }
            players.removeAll(playersOut);

            trace("deck size = " + deck.cards().size());
            trace("Number of players still left = " + players.size());
            System.out.println();
            if (players.size() > 1) {
                currentDealer = setNextDealer(currentDealer);

                if (playersOut.contains(currentDealer)) {
                    System.out.println("stop the bus!");
                }
            }
        }
        System.out.println("rounds = " + rounds);
        stats.setNumberOfPlayersLeft(players.size());
        if (players.size() == 1)
            stats.setOverallWinner(players.get(0));
        return stats;
    }

    private Player setNextDealer(Player currentDealer) {
        int index = players.indexOf(currentDealer) + 1;
        if (index == -1) {
            // dealer is out -- who should deal next??
            index = 0;
        }
        else if (index == players.size()) {
            index = 0;
        }
        currentDealer.notDealer();
        currentDealer = players.get(index);
        return currentDealer;
    }

    private void trace(String msg) {
        if (TRACE) {
            System.out.println(msg);
        }
    }

    private Outcome playRound(Deck deck, Player randomPlayer) {
        Dealer dealer = randomPlayer.setAsDealer(deck);
        System.out.println(randomPlayer.getName() + " deals");
        dealer.dealTo(players, 1);

        for (Player player : players) {
            trace("start: player = " + player);
        }

        Queue<ShoveItPlayer> playersInTurn = determineOrderOfPlayers(players, dealer);
        ShoveItPlayer player;
        int numberOfStays = 0;
        while ((player = playersInTurn.poll()) != null) {

            final Decision decision = stayOrSwitch(player, numberOfStays);
            if (decision == Decision.SWITCH) {
                if (player.isDealer()) {
                    final Card card = deck.next();
                    System.out.println(
                            player.getName() + " draws " + card + " from the deck");
                    player.setCard(card);
                }
                else {
                    Card player1Card = player.getCard();
                    final ShoveItPlayer player2 = playersInTurn.peek();
                    Card player2Card = player2.getCard();
                    if (player2Card.getRank() == 13) {
                        System.out.println(
                                player.getName() + " can't switch because " + player2.getName
                                        () + " has a king!");
                    }
                    else {
                        System.out.println(player.getName() + " trades " + decision.getReasonOrDefault(
                                player1Card.toString()) + " and gets " + player2Card);
                        player.swapCard(player2Card);
                        player2.swapCard(player1Card);
                    }
                }
            }
            else {
                numberOfStays++;
                System.out.println(
                        player.getName() + " keeps " + player.getCard() + decision.getReasonOrDefault(
                                ""));
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
            System.out.println("Tie between " + losingOutcome.getLosers().stream()
                                                             .map(_player -> "Player " + _player.getName() + " with "
                                                                     + _player.getCard())
                                                             .collect(Collectors.joining(" and ")) + " and " +
                    "both/all? pay");
        }
        else {
            System.out.println(
                    losingOutcome.getLosers().get(0).getName() + " is low with " + losingOutcome.getLosers()
                                                                                                       .get(0)
                                                                                                       .getCard
                            () + " and pays and has " + ((ShoveItPlayer) losingOutcome.getLosers().get(0)).coins());
        }

        for (Player player2 : players) {
            trace("end: player = " + player2);
            player2.clearHand();
        }
        return losingOutcome;
    }

    private Outcome determineLoser(List<? extends Player> players) {
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
        else {
            return new Outcome(lowestPlayer);
        }
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

        if (highestPlayer == null) {
            return Outcome.TIE;
        }
        else {
            return new Outcome(highestPlayer);
        }
    }

    private Queue<ShoveItPlayer> determineOrderOfPlayers(List<? extends Player> players, Dealer dealer) {
        int index = dealer.getIndexOfPlayerToLeftOfDealer(players);

        final ArrayBlockingQueue<ShoveItPlayer> ordered = new ArrayBlockingQueue<>(players.size());
        for (int i = index; i < players.size(); i++) {
            final ShoveItPlayer player = (ShoveItPlayer) players.get(i);
            ordered.add(player);
        }
        for (int i = 0; i < index; i++) {
            final ShoveItPlayer player = (ShoveItPlayer) players.get(i);
            ordered.add(player);
        }

        return ordered;
    }


    private Decision stayOrSwitch(ShoveItPlayer player, int numberOfPreviousStays) {
        return player.stayOrSwitch(numberOfPreviousStays);
    }

    private Player randomPlayer() {
        return players.get((new Random()).nextInt(players.size()));
    }

    public enum Decision {
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
