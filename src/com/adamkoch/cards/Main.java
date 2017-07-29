package com.adamkoch.cards;

//import static com.adamkoch.cards.SpecialsCards.JOKER;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) {

//        listStandardDeck();
        Map<Player, Integer> map = new ConcurrentHashMap<>();
        Players players = new Players(PlayerFactory.initializePlayers(4));

        for (int i = 0; i < 100; i++) {

            SevenGame game = new SevenGame(players);
            final Player winner = game.play();
            System.out.println(winner.getName() + " won");

            map.putIfAbsent(winner, Integer.valueOf(0));
            map.computeIfPresent(winner, (player, timesWon) -> Integer.valueOf(timesWon + 1));

            resetPlayers(players);

        }

        System.out.println("map = " + map);
    }

    private static void resetPlayers(Players players) {
        players.list().parallelStream().forEach(player -> player.clearHand());
    }

    private static void listStandardDeck() {
        for (Card card : new StandardDeck().cards()) {
            System.out.print(CharacterConverter.getUnicode(card));
        }
    }

}
