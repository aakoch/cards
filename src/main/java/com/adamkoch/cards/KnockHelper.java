package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
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

        final Optional<Player> playerWithHigherPoints = playerList.stream().filter(player -> !player.equals(playerWhoKnocked))
                                     .filter(player -> Calculator.totalCards(player.getHand()) >= playerWhoKnockedTotal)
                                     .findFirst();
        if (playerWithHigherPoints.isPresent()) {
            LOGGER.info(playerWithHigherPoints.get().getName() + " has " + Calculator.totalCards(
                    playerWithHigherPoints.get().getHand()) + " points which is more than or equal to the "
                    + playerWhoKnockedTotal + " " + playerWhoKnocked.getName() + " has!");
            return Collections.singletonList(playerWithHigherPoints.get());
        }

//        playerList.stream().min(Comparator.comparingInt(player -> Calculator.totalCards(player.getHand())));

        int lowestTotal = findLowestTotal(playerWhoKnocked, playerList);

        List<Player> lowestPlayers = findPlayersWithScore(playerWhoKnocked, playerList, lowestTotal);

        return lowestPlayers;
    }

    public static int findLowestTotal(Player playerWhoKnocked, List<Player> playerList) {
        return playerList.stream()
                         .filter(player -> !player.equals(playerWhoKnocked))
                         .map(Player::getHand)
                         .mapToInt(Calculator::totalCards)
                         .min()
                         .getAsInt();
    }

    public static List<Player> findPlayersWithScore(Player playerWhoKnocked, List<Player> playerList, int lowestTotal) {
        return playerList.stream()
                         .filter(player -> !player.equals(playerWhoKnocked))
                         .filter(player -> Calculator.totalCards(player.getHand()) == lowestTotal)
                         .collect(Collectors.toList());
    }
}
