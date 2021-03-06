package com.adamkoch.cards;

import com.adamkoch.utils.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ChoiceFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Created by aakoch on 2017-07-30.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class ThirtyOneRound {
    private static final Logger AUDIT = LogManager.getLogger("outcome.auditor");
    private static final Logger LOGGER = LogManager.getLogger(ThirtyOneRound.class);

    private final Dealer dealer;
    private final List<Player> players;
    private GameContext gameContext;

    public ThirtyOneRound(Dealer dealer, List<Player> players, GameContext gameContext) {
        this.dealer = dealer;
        this.players = players;
        this.gameContext = gameContext;
    }

    public Result play() {
        LOGGER.info("************************ Start round ************************");
        gameContext.startNewRound();

        orderPlayers(players, dealer.asPlayer());

        Result result = new Result();

        LOGGER.debug(players.size() + " players: " + players.stream().map(Player::getName).collect(Collectors
                .joining(", ")));

        final List<Card> remainingCards = dealer.dealTo(players, 3);
        DrawPile drawPile = new DrawPile(remainingCards, gameContext);
        DiscardPile discardPile = new DiscardPile(drawPile);
        //gameContext.cardsStillOutThere(new StandardDeck().cards());

        Iterator<Player> r = ListUtils.constructRotator(players, -getStartIndex() - 1);

        Player playerWhoKnocked = null;
        boolean playerKnocked = false;
        boolean playerHas31 = false;
        Player player = null;
        int playerTurnCount = 0;
        int turnsLeft = Integer.MAX_VALUE;
        while (!playerHas31 && r.hasNext() && turnsLeft-- > 0) {
            if (playerTurnCount > 10000) {
                LOGGER.error("Over 10000 turns taken");
                throw new StalemateException("Over 10000 turns taken");
            }
            player = r.next();

            Outcome outcome = playTurn(player, drawPile, discardPile, gameContext);
            AUDIT.info(outcome);
            gameContext.addOutcome(outcome);

            final Card discardedCard = outcome.getDiscardCard();
            if (discardedCard != null) {
                try {
                    if (outcome.didPlayerDrewFromDiscardPile()) {
                        if (discardedCard.equals(outcome.getCardTakenFromDiscardPile())) {
                            throw new IllegalStateException(
                                    "Player can't pick up the card from the discard pile and then" +
                                            " discard it: " + discardedCard);
                        }
                        gameContext.addCardToHandForPlayer(player, outcome.getCardTakenFromDiscardPile());
                    }
                }
                finally {
                    gameContext.removeCardFromHandForPlayer(player, discardedCard);
                    gameContext.addCardToThoseDiscardedByPlayer(player, discardedCard);
                }
            }

            if (outcome.getHas31()) {
                LOGGER.info(player.getName() + " got 31! " + player.getHand() + "\n\n");
                playerHas31 = true;
            }
            else if (outcome.playerKnocks() && !playerKnocked) {
                playerWhoKnocked = player;
                LOGGER.info(player.getName() + " knocks with " + player.total());
                playerKnocked = true;
                turnsLeft = players.size() - 1;
            }

            playerTurnCount++;
            gameContext.incrementNumberOfPlays();
        }
        LOGGER.debug("playerTurnCount = " + playerTurnCount);
        LOGGER.debug("playerTurnCount / player count = " + (double) (playerTurnCount / players.size()));
        final RoundEndMethod roundEndMethod;
        if (playerHas31 && player != null) {

            LOGGER.info(player.getName() + " won so the rest have to pay");

            result.setWinners(Arrays.asList(player));
            List<Player> losers = new ArrayList<>(players);
            losers.remove(player);
            result.setLosers(losers);
            roundEndMethod = RoundEndMethod.THIRTY_ONE;
        }
        else if (playerKnocked) {
            List<Player> lowestPlayers = KnockHelper.findLowestPlayers(playerWhoKnocked, players);

            ChoiceFormat wasFormat = new ChoiceFormat("1#was|were");
            ChoiceFormat hasFormat = new ChoiceFormat("1#has|have");

            LOGGER.info(lowestPlayers + " " + wasFormat.format(lowestPlayers.size()) + " lowest and " + hasFormat
                    .format(lowestPlayers.size()) + " to pay");

            if (lowestPlayers.contains(playerWhoKnocked)) {
                roundEndMethod = RoundEndMethod.KNOCK_BACKFIRED;
                result.setKnockerLost(playerWhoKnocked);
                List<Player> winners = new ArrayList<>(players);
                winners.remove(playerWhoKnocked);
                result.setWinners(winners);
            }
            else {
                result.setWinners(Arrays.asList(playerWhoKnocked));
                roundEndMethod = RoundEndMethod.KNOCK;
            }

            result.setLosers(lowestPlayers);
        }
        else {
            roundEndMethod = RoundEndMethod.UNKNOWN;
        }

        result.setRoundEndMethod(roundEndMethod);
        players.forEach(Player::clearHand);

        return result;
    }

    protected int getStartIndex() {
        final int index = players.indexOf(dealer.asPlayer());

        return index;
    }


    private void orderPlayers(List<Player> players, Player dealer) {
        Collections.rotate(players, players.indexOf(dealer));
    }



    private Outcome playTurn(Player player, DrawPile drawPile, DiscardPile discardPile, GameContext gameContext) {
        LOGGER.debug(
                "Start of " + player.getName() + "'s turn: " + player.getHand() + "=" + Calculator.totalCards(player
                        .getHand()));

//        if (player.getHand().get(0).equals(player.getHand().get(1)) ||
//                player.getHand().get(1).equals(player.getHand().get(2)) ||
//                player.getHand().get(0).equals(player.getHand().get(2))) {
//            LOGGER.error("all cards equal");
//        }

        Outcome outcome = new Outcome();
        outcome.setPlayer(player);
        final Card topDiscardCard = discardPile.peekAtTopCard();
        if (player.shouldTakeCardFromDiscardPile(topDiscardCard, gameContext)) {
            LOGGER.debug(player.getName() + " takes " + topDiscardCard + " from discard pile");
            outcome.setCardTakenFromDiscardPile(topDiscardCard);
            player.addCardToHand(discardPile.removeTopCard());
        }
        else {
            final Card drawnCard = drawPile.draw();
            LOGGER.debug(player.getName() + " leaves " + topDiscardCard + " and draws " + drawnCard);
            player.addCardToHand(drawnCard);
            outcome.setDrawnCard(drawnCard);
        }

        Card discardCard = player.chooseWhichCardToDiscard(drawPile, discardPile, gameContext);
        LOGGER.debug(player.getName() + " discards " + discardCard);
        if (!player.removeFromHand(discardCard)) {
            LOGGER.debug(discardCard + " wasn't in hand");
        }
        discardPile.add(discardCard);
        outcome.setDiscard(discardCard);

        if (player.has31()) {
            outcome.setHas31();
        }
        else if (!this.gameContext.someoneElseHasKnocked() && shouldPlayerKnock(player, this.gameContext)) {
            outcome.setPlayerKnocks();
            this.gameContext.setSomeoneElseKnocked();
        }
        LOGGER.debug("End  of " + player.getName() + "'s turn: " + player.getHand() + "=" + Calculator.totalCards(player
                .getHand()));

        return outcome;
    }

    private boolean shouldPlayerKnock(Player player, GameContext gameContext) {
        return player.decidesToKnock(gameContext);
    }

}
