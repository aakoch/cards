package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * Created by aakoch on 2017.08.04.
 *
 * @author Adam Koch (aakoch)
 */
public class StalemateBreakingPlayer extends EasyPlayer {
    private static final Logger LOGGER = LogManager.getLogger(StalemateBreakingPlayer.class);

    private Suit stalemateSuit;

    public StalemateBreakingPlayer(String name, int knockLimit) {
        super(name, knockLimit);
        stalemateSuit = null;
    }

    @Override
    public Card chooseWhichCardToDiscard(DrawPile drawPile, DiscardPile discardPile, GameContext gameContext) {
        final Card cardToDiscard;
        if (stalemateSuit != null && CardUtil.handContainSuit(super.getHand(), stalemateSuit)) {
            cardToDiscard = RandomUtils.getRandom(
                    super.getHand().stream().filter(card -> card.getSuit() == stalemateSuit)
                         .collect
                                 (Collectors
                                         .toList()));
        }
        else {
            cardToDiscard = super.chooseWhichCardToDiscard(drawPile, discardPile, gameContext);

            if (gameContext.getNumberOfTimesDiscardPileShuffled() > 20) {
                LOGGER.debug("Probably stalemate");
                if (CardUtil.areThreeCardsWithSameSuit(super.getHand())) {

                    Optional<Suit> suit = CardUtil.findMajoritySuit(super.getHand());

                    if (suit.isPresent()) {
                        final boolean allPlayersHaveSameSuit = doAllPlayersHaveSameSuit(gameContext, suit);
                        LOGGER.debug("allPlayersHaveSameSuit = " + allPlayersHaveSameSuit);
                        if (allPlayersHaveSameSuit || gameContext
                                .getNumberOfTimesDiscardPileShuffled() > 40) {
                            stalemateSuit = suit.get();
                            LOGGER.info("From now on, not collecting " + stalemateSuit);
                        }
                    }
                }

            }
        }
        return cardToDiscard;
    }

    protected boolean doAllPlayersHaveSameSuit(GameContext gameContext, Optional<Suit> suit) {
        LOGGER.debug("doAllPlayersHaveSameSuit(): suit=" + suit);
        return gameContext.getPlayers()
                       .stream()
                       .filter(player -> player != this)
                       .allMatch(player -> {
                           final Suit suitPlayerLikelyHas = gameContext.suitPlayerLikelyHas(player);
                           LOGGER.debug(player.getName() + " likely has " + suitPlayerLikelyHas);
                           return suitPlayerLikelyHas == suit.get();
                       });
    }

    @Override
    public boolean decidesToKnock(GameContext gameContext) {
        boolean decidesToKnock = super.decidesToKnock(gameContext);

        if (!decidesToKnock) {
            final List<Card> cards = getHand();
            final int total = Calculator.totalCards(cards);
            final int rounds = Math.round(gameContext.getNumberOfPlays() / gameContext.getNumberOfPlayers());
            final int currentKnockLimit = this.getKnockLimit() + rounds;
            if (total == 30) {
                if (rounds > 100) {
                    LOGGER.info("Stalemate. after " + rounds + " rounds, " + getName() + " has " + total +
                            " points, which is not greater than their limit of " + currentKnockLimit + ", but decides to " +
                            "knock anyway. numberOfTimesDiscardPileShuffled=" + gameContext
                            .getNumberOfTimesDiscardPileShuffled());
                    decidesToKnock = true;
                }
                else {
                    LOGGER.info("after " + rounds + " rounds, " + getName() + " has " + total +
                            " points, but decides not to knock. starting knock limit=" + this.getKnockLimit() + ", current=" +
                            currentKnockLimit);
                }
            }
        }

        return decidesToKnock;
    }

    @Override
    public void clearHand() {
        super.clearHand();
        stalemateSuit = null;
    }
}
