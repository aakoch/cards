package com.adamkoch.cards;

import com.adamkoch.utils.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * <p>Created by aakoch on 2017-07-30.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class ThirtyOneRound {
    private static final Logger LOGGER = LogManager.getLogger(ThirtyOneRound.class);

    private final Dealer dealer;
    private final Queue<Player> playerQueue;
    private GameContext gameContext;

    public ThirtyOneRound(Dealer dealer, Queue<Player> playerQueue) {
        this.dealer = dealer;
        this.playerQueue = playerQueue;
        gameContext = new GameContext();
    }

    public List<Player> play() {
        LOGGER.info("************************ Start round ************************");
        final List<Player> playerList = playerQueue.stream().collect(Collectors.toList());
        LOGGER.debug(playerList.size() + " players");
        gameContext.setNumberOfPlayers(playerList.size());

        final List<Card> remainingCards = dealer.dealTo(playerList, 3);
        DrawPile drawPile = new DrawPile(remainingCards);
        DiscardPile discardPile = new DiscardPile(drawPile);

        Iterator<Player> r = ListUtils.constructRotator(playerList, playerList.indexOf(dealer));

        Player playerWhoKnocked = null;
        boolean playerKnocked = false;
        boolean playerHas31 = false;
        Player player = null;
        int playerTurnCount = 0;
        int turnsLeft = Integer.MAX_VALUE;
        while (!playerHas31 && r.hasNext() && turnsLeft-- > 0) {
            player = r.next();

            Outcome outcome = playTurn(player, drawPile, discardPile);

            final Card outcomeCard = outcome.getCard();
            if (outcomeCard != null) {
                discardPile.add(outcomeCard);
            }

            if (outcome.getHas31()) {
                LOGGER.info(player + " got 31\n\n");
                playerHas31 = true;
            }
            else if (outcome.playerKnocks() && !playerKnocked) {
                playerWhoKnocked = player;
                LOGGER.info(player.getName() + " knocks with " + player.total() + "\n");
                playerKnocked = true;
                turnsLeft = playerList.size() - 1;
            }

            playerTurnCount++;
            gameContext.incrementNumberOfPlays();
        }
        LOGGER.debug("playerTurnCount = " + playerTurnCount);
        LOGGER.debug("playerTurnCount / player count = " + (double) (playerTurnCount / playerList.size()));
        if (playerHas31 && player != null) {
            playerList.remove(player);
            playerList.forEach(Player::pay);
            return playerList;
        }
        else if (playerKnocked) {
            List<Player> lowestPlayers = findLowestPlayers(playerWhoKnocked, playerList);

            return lowestPlayers;
        }

        return null;
    }

    private List<Player> findLowestPlayers(Player playerWhoKnocked, List<Player> playerList) {
        int playerWhoKnockedTotal = Calculator.totalCards(playerWhoKnocked.getHand().cards());

        for (Player player : playerList) {
            if (!player.equals(playerWhoKnocked)) {
                int total = Calculator.totalCards(player.getHand().cards());
                if (total > playerWhoKnockedTotal) {
                    LOGGER.info(player.getName() + " has " + total + " points which is more than the "
                            + playerWhoKnockedTotal + " " + playerWhoKnocked.getName() + " has!");
                    return Arrays.asList(playerWhoKnocked);
                }
            }
        }

        int lowestTotal = Integer.MAX_VALUE;
        for (Player player : playerList) {
            if (!player.equals(playerWhoKnocked)) {
                int total = Calculator.totalCards(player.getHand().cards());
                lowestTotal = Math.min(lowestTotal, total);
            }
        }

        List<Player> lowestPlayers = new ArrayList<>();
        for (Player player : playerList) {
            if (!player.equals(playerWhoKnocked)) {
                int total = Calculator.totalCards(player.getHand().cards());
                if (total == lowestTotal) {
                    lowestPlayers.add(player);
                }
            }
        }

        return lowestPlayers;
    }

    private Outcome playTurn(Player player, DrawPile drawPile, DiscardPile discardPile) {
        LOGGER.debug("Start of " + player.getName() + "'s turn: " + player);

        if (player.getHand().cards().get(0).equals(player.getHand().cards().get(1)) ||
                player.getHand().cards().get(1).equals(player.getHand().cards().get(2)) ||
                player.getHand().cards().get(0).equals(player.getHand().cards().get(2))) {
            LOGGER.error("all cards equal");
        }

        Outcome outcome = new Outcome();
//        if (!player.chooseCardFromDiscardPile(drawPile, discardPile)) {
        if (Determiner.cardWouldImproveHand(discardPile.peekAtTopCard(), player.hand)) {
            LOGGER.debug(player.getName() + " takes " + discardPile.peekAtTopCard() + " from discard pile");
            player.addCardToHand(discardPile.removeTopCard());
        }
        else {
//            player.drawFromDrawPile(drawPile, discardPile);
            final Card drawnCard = drawPile.draw();
            LOGGER.debug(player.getName() + " draws " + drawnCard);
            player.addCardToHand(drawnCard);
        }

        Card discardCard = player.chooseWhichCardToDiscard(drawPile, discardPile);
        LOGGER.debug(player.getName() + " discards " + discardCard);
        player.removeFromHand(discardCard);
        discardPile.add(discardCard);
        outcome.setDiscard(discardCard);

        if (player.has31()) {
            outcome.setHas31();
        }
        else if (shouldPlayerKnock(player, gameContext)) {
            outcome.setPlayerKnocks();
        }

        return outcome;
    }

    private boolean shouldPlayerKnock(Player player, GameContext gameContext) {
        return player.decidesToKnock(gameContext);
    }

}
