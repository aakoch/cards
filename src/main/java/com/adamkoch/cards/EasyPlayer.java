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

    private final int knockLimit;
    private Iterator<Card> handIterator;
    private List<Card> discardPile;

    public EasyPlayer(String name, int knockLimit) {
        super(name);
        this.knockLimit = knockLimit;
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

        Card cardToDiscard = Determiner.chooseCardToDiscard(getHand().cards(), gameContext);

        if (nextPlayerWillWinWithCard(cardToDiscard, gameContext)) {
            LOGGER.error("Next player will win if " + getName() + " plays " + cardToDiscard);
            final List<Card> newCardList = new ArrayList<>(getHand().cards());
            newCardList.remove(cardToDiscard);
            cardToDiscard = Determiner.chooseCardToDiscard(newCardList, gameContext);
        }

        getHand().cards().remove(cardToDiscard);

        return cardToDiscard;
    }

    private boolean nextPlayerWillWinWithCard(Card card, GameContext gameContext) {
        boolean win = false;
        List<Card> nextPlayersHand = new ArrayList<Card>(getNextPlayer(gameContext).getHand().cards());
        nextPlayersHand.add(card);
        nextPlayersHand.removeIf(card1 -> card1.getRank().lessThan(Rank.TEN));

        if (Calculator.totalCards(nextPlayersHand) >= 31) {
            win = true;
        }

        return win;
    }

    private Player getNextPlayer(GameContext gameContext) {
        return gameContext.getNextPlayer(this);
    }

    @Override
    public boolean decidesToKnock(GameContext gameContext) {
        final List<Card> cards = getHand().cards();
        final int total = Calculator.totalCards(cards);
        final int rounds = Math.round(gameContext.getNumberOfPlays() / gameContext.getNumberOfPlayers());
        final int currentKnockLimit = this.knockLimit + rounds;
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
                        " points, but decides not to knock. starting knock limit=" + this.knockLimit + ", current=" + currentKnockLimit);
            }
        }

        return decidesToKnock;

    }

    @Override
    public boolean chooseCardFromDiscardPile(Card card, GameContext gameContext) {
        return Determiner.cardWouldImproveHand(card, getHand().cards(), gameContext);
    }
}
