package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Created by aakoch on 2017.08.04.
 *
 * @author Adam Koch (aakoch)
 */
public class EasyPlayer extends Player {
    private static final Logger LOGGER = LogManager.getLogger(PlayerFactory.class);

    private Iterator<Card> handIterator;
    private List<Card> discardPile;
    private Determiner determiner;

    public EasyPlayer(String name, int knockLimit) {
        super(name, knockLimit);

        determiner = new Determiner(this);
    }

    @Override
    public Chain getChain(GameContext gameContext) {

        Chain chain = new Chain();
        chain.addRule(RuleFactory.createGameContextRule(gameContext));
        chain.addRule(RuleFactory.createOddSuitRule());
        chain.addRule(RuleFactory.createComparePairsRule());
        chain.addRule(RuleFactory.createSameRankChooseLast());
        chain.addRule(RuleFactory.createNumericRankRule());
        chain.addRule(RuleFactory.createKeepTensRule());
        chain.addRule(RuleFactory.createRandomRule());

        return chain;
    }

    @Override
    protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
        Collections.shuffle(cardsThatCanPlay);
        return (List<Card>) cardsThatCanPlay;
    }

    @Override
    public Card chooseWhichCardToDiscard(DrawPile drawPile, DiscardPile discardPile, GameContext gameContext) {

        if (super.discardCard != null) {
            Card temp = discardCard;
            discardCard = null;
            return temp;
        }

        Card cardToDiscard = determiner.chooseCardToDiscard(getHand(), gameContext);

        if (nextPlayerWillWinWithCard(cardToDiscard, gameContext)) {
            LOGGER.error("Next player will win if " + getName() + " plays " + cardToDiscard);
            final List<Card> newCardList = new ArrayList<>(getHand());
            newCardList.remove(cardToDiscard);
            cardToDiscard = determiner.chooseCardToDiscard(newCardList, gameContext);
        }

        getHand().remove(cardToDiscard);

        return cardToDiscard;
    }

    private boolean nextPlayerWillWinWithCard(Card card, GameContext gameContext) {
        boolean win = false;
        final Player nextPlayer = getNextPlayer(gameContext);
        List<Card> nextPlayersHand = new ArrayList<Card>(gameContext.getKnownHand(nextPlayer));
        nextPlayersHand.add(card);
        nextPlayersHand.removeIf(card1 -> card1.getRank().lessThan(Rank.TEN));

        if (Calculator.totalCards(nextPlayersHand) >= 31) {
            // TODO:
            LOGGER.warn("Next player, " + nextPlayer.getName() + "'s hand=" + nextPlayersHand + ", card=" + card);
            win = true;
        }

        return win;
    }

    private Player getNextPlayer(GameContext gameContext) {
        return gameContext.getNextPlayer(this);
    }

    @Override
    public boolean decidesToKnock(GameContext gameContext) {
        final List<Card> cards = getHand();
        final int total = Calculator.totalCards(cards);
        final int rounds = Math.round(gameContext.getNumberOfPlays() / gameContext.getNumberOfPlayers());
        final int currentKnockLimit = this.getKnockLimit() + rounds;
        final boolean totalGreater = total > currentKnockLimit;
        boolean decidesToKnock = Determiner.areThreeCardsWithSameSuit(cards) && totalGreater;
        if (decidesToKnock) {
            LOGGER.debug("after " + rounds + " rounds, " + getName() + " has " + total +
                    " points, which is greater than their limit of " + currentKnockLimit + ", and decides to knock");
        }
        else if (total >= 30) {
            if (rounds > 100) {
                LOGGER.info("Stalemate. after " + rounds + " rounds, " + getName() + " has " + total +
                        " points, which is not greater than their limit of " + currentKnockLimit + ", but decides to knock anyway");
                decidesToKnock = true;
            }
            else {
                LOGGER.info("after " + rounds + " rounds, " + getName() + " has " + total +
                        " points, but decides not to knock. starting knock limit=" + this.getKnockLimit() + ", current=" +
                        currentKnockLimit);
            }
        }

        return decidesToKnock;

    }

    @Override
    public boolean chooseCardFromDiscardPile(Card card, GameContext gameContext) {
        return Determiner.cardWouldImproveHand(card, getHand(), gameContext);
    }

    @Override
    public String toString() {
        return name + ':' + hand +
                ":knockLimit=" + getKnockLimit();
    }
}
