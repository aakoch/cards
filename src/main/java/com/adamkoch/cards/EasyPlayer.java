package com.adamkoch.cards;

import com.adamkoch.utils.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Created by aakoch on 2017.08.04.
 *
 * @author Adam Koch (aakoch)
 */
public class EasyPlayer extends Player {
    private static final Logger LOGGER = LogManager.getLogger(PlayerFactory.class);

    private Determiner determiner;

    public EasyPlayer(String name, int knockLimit, KnockStrategy knockStrategy, PickUpStrategy pickupStrategy,
                      DiscardStrategy discardStrategy) {
        super(name, knockLimit, knockStrategy, pickupStrategy, discardStrategy);

        determiner = new Determiner();
    }

    @Override
    public Chain getChain(GameContext gameContext) {
        Chain chain = getBaseChain(gameContext);

        return chain;
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
        return ListUtils.getNext(gameContext.getPlayersStillInTheGame(), this);
    }

    @Override
    public String toString() {
        return name + ':' + hand + ":coins=" + coinsLeft() +
                ":knockLimit=" + getKnockLimit();
    }
}
