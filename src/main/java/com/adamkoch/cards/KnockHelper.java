package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Stream;

/**
 * <p>Created by aakoch on 2017-08-06.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class KnockHelper {
    private static final Logger LOGGER = LogManager.getLogger(KnockHelper.class);

    public static List<Player> findLowestPlayers(Player playerWhoKnocked, List<Player> playerList) {
        int playerWhoKnockedTotal = Calculator.totalCards(playerWhoKnocked.getHand());

        for (Player player : playerList) {
            if (!player.equals(playerWhoKnocked)) {
                int total = Calculator.totalCards(player.getHand());
                if (total >= playerWhoKnockedTotal) {
                    LOGGER.info(player.getName() + " has " + total + " points which is more than or equal to the "
                            + playerWhoKnockedTotal + " " + playerWhoKnocked.getName() + " has!");
                    return Arrays.asList(playerWhoKnocked);
                }
            }
        }

//        playerList.stream().min(Comparator.comparingInt(player -> Calculator.totalCards(player.getHand())));

        int lowestTotal = findLowestTotal(playerWhoKnocked, playerList);

        List<Player> lowestPlayers = findPlayersWithScore(playerWhoKnocked, playerList, lowestTotal);

        return lowestPlayers;
    }

    public static int findLowestTotal(Player playerWhoKnocked, List<Player> playerList) {
        final Optional<List<Card>> cardList = playerList.stream()
                                                        .filter(player -> !player.equals(playerWhoKnocked))
                                                        .map(Player::getHand)
                                                        .min(Comparator.comparingInt(Calculator::totalCards));


        final Optional<Player> min = playerList.stream()
                                               .filter(player -> !player.equals(playerWhoKnocked))
                                               .min(Comparator.comparingInt(
                                                       player2 -> Calculator.totalCards(player2.getHand())));


        return playerList.stream()
            .filter(player -> !player.equals(playerWhoKnocked))
            .map(Player::getHand)
            .mapToInt(Calculator::totalCards)
            .min()
            .getAsInt();
    }

    public static List<Player> findPlayersWithScore(Player playerWhoKnocked, List<Player> playerList, int lowestTotal) {
        List<Player> lowestPlayers = new ArrayList<>();
        for (Player player : playerList) {
            if (!player.equals(playerWhoKnocked)) {
                int total = Calculator.totalCards(player.getHand());
                if (total == lowestTotal) {
                    lowestPlayers.add(player);
                }
            }
        }
        return lowestPlayers;
    }
}
