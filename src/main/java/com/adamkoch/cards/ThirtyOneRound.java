package com.adamkoch.cards;

import com.adamkoch.utils.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ChoiceFormat;
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
    }

    public Result play() {
        LOGGER.info("************************ Start round ************************");
        Result result = new Result();

        gameContext = new GameContext();
        final List<Player> playerList = playerQueue.stream().collect(Collectors.toList());
        LOGGER.debug(playerList.size() + " players");

        gameContext.setPlayers(playerList);

        final List<Card> remainingCards = dealer.dealTo(playerList, 3);
        DrawPile drawPile = new DrawPile(remainingCards);
        DiscardPile discardPile = new DiscardPile(drawPile);
        //gameContext.cardsStillOutThere(new StandardDeck().cards());

        Iterator<Player> r = ListUtils.constructRotator(playerList, playerList.indexOf(dealer));

        Player playerWhoKnocked = null;
        boolean playerKnocked = false;
        boolean playerHas31 = false;
        Player player = null;
        int playerTurnCount = 0;
        int turnsLeft = Integer.MAX_VALUE;
        while (!playerHas31 && r.hasNext() && turnsLeft-- > 0 && playerTurnCount < 1000) {
            player = r.next();

            Outcome outcome = playTurn(player, drawPile, discardPile, gameContext);
            gameContext.addOutcome(outcome);

            final Card outcomeCard = outcome.getCard();
            if (outcomeCard != null) {
                discardPile.add(outcomeCard);
            }
            final Card discardedCard = outcome.getDiscardCard();
            if (discardedCard != null) {
                if (outcome.playerDrewFromDiscardPile()) {
                    if (discardedCard.equals(outcome.getCardTakenFromDiscardPile())) {
                        throw new IllegalStateException("Player can't pick up the card from the discard pile and then" +
                                " discard it: " + discardedCard);
                    }
                    gameContext.addCardToHandForPlayer(player, outcome.getCardTakenFromDiscardPile());
                }
                gameContext.removeCardFromHandForPlayer(player, discardedCard);
                gameContext.addCardToThoseDiscardedByPlayer(player, discardedCard);
            }

            if (outcome.getHas31()) {
                LOGGER.info(player.getName() + " got 31! " + player.getHand().cards() + "\n\n");
                playerHas31 = true;
            }
            else if (outcome.playerKnocks() && !playerKnocked) {
                playerWhoKnocked = player;
                LOGGER.info(player.getName() + " knocks with " + player.total());
                playerKnocked = true;
                turnsLeft = playerList.size() - 1;
            }

            playerTurnCount++;
            gameContext.incrementNumberOfPlays();
        }
        LOGGER.debug("playerTurnCount = " + playerTurnCount);
        LOGGER.debug("playerTurnCount / player count = " + (double) (playerTurnCount / playerList.size()));
        final RoundEndMethod roundEndMethod;
        if (playerHas31 && player != null) {

            LOGGER.info(player.getName() + " won so the rest have to pay");

            result.setWinners(Arrays.asList(player));
            playerList.remove(player);

            result.setLosers(playerList);
            roundEndMethod = RoundEndMethod.THIRTY_ONE;
        }
        else if (playerKnocked) {
            List<Player> lowestPlayers = findLowestPlayers(playerWhoKnocked, playerList);

            ChoiceFormat wasFormat = new ChoiceFormat("1#was|were");
            ChoiceFormat hasFormat = new ChoiceFormat("1#has|have");

            LOGGER.info(lowestPlayers + " " + wasFormat.format(lowestPlayers.size()) + " lowest and " + hasFormat
                    .format(lowestPlayers.size()) + " to pay");

            if (lowestPlayers.contains(playerWhoKnocked)) {
                result.setKnockerLost(playerWhoKnocked);
            }

            result.setLosers(lowestPlayers);
            roundEndMethod = RoundEndMethod.KNOCK;
        }
        else {
            roundEndMethod = RoundEndMethod.UNKNOWN;
        }

        result.setRoundEndMethod(roundEndMethod);

        return result;
    }

    private List<Player> findLowestPlayers(Player playerWhoKnocked, List<Player> playerList) {
        int playerWhoKnockedTotal = Calculator.totalCards(playerWhoKnocked.getHand().cards());

        for (Player player : playerList) {
            if (!player.equals(playerWhoKnocked)) {
                int total = Calculator.totalCards(player.getHand().cards());
                if (total >= playerWhoKnockedTotal) {
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

    private Outcome playTurn(Player player, DrawPile drawPile, DiscardPile discardPile, GameContext gameContext) {
        LOGGER.debug("Start of " + player.getName() + "'s turn: " + player.getHand().cards());

        if (player.getHand().cards().get(0).equals(player.getHand().cards().get(1)) ||
                player.getHand().cards().get(1).equals(player.getHand().cards().get(2)) ||
                player.getHand().cards().get(0).equals(player.getHand().cards().get(2))) {
            LOGGER.error("all cards equal");
        }

        Outcome outcome = new Outcome();
//        if (!player.chooseCardFromDiscardPile(drawPile, discardPile)) {
        if (player.chooseCardFromDiscardPile(discardPile.peekAtTopCard(), gameContext)) {
            LOGGER.debug(player.getName() + " takes " + discardPile.peekAtTopCard() + " from discard pile");
            outcome.setCardTakenFromDiscardPile(discardPile.peekAtTopCard());
            player.addCardToHand(discardPile.removeTopCard());
        }
        else {
//            player.drawFromDrawPile(drawPile, discardPile);
            final Card drawnCard = drawPile.draw();
            LOGGER.debug(player.getName() + " draws " + drawnCard);
            player.addCardToHand(drawnCard);
        }

        Card discardCard = player.chooseWhichCardToDiscard(drawPile, discardPile, gameContext);
        LOGGER.debug(player.getName() + " discards " + discardCard);
        player.removeFromHand(discardCard);
        discardPile.add(discardCard);
        outcome.setDiscard(discardCard);

        if (player.has31()) {
            outcome.setHas31();
        }
        else if (!this.gameContext.someoneElseHasKnocked() && shouldPlayerKnock(player, this.gameContext)) {
            outcome.setPlayerKnocks();
            this.gameContext.setSomeoneElseKnocked();
        }
        LOGGER.debug("End  of " + player.getName() + "'s turn: " + player.getHand().cards());

        return outcome;
    }

    private boolean shouldPlayerKnock(Player player, GameContext gameContext) {
        return player.decidesToKnock(gameContext);
    }

}
