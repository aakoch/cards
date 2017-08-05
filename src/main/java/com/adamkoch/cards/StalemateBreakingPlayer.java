package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>
 * Created by aakoch on 2017.08.04.
 *
 * @author Adam Koch (aakoch)
 */
public class StalemateBreakingPlayer extends EasyPlayer {
    private static final Logger LOGGER = LogManager.getLogger(StalemateBreakingPlayer.class);

    private int handHasNotChangeCounter;

    public StalemateBreakingPlayer(String name, int knockLimit) {
        super(name, knockLimit);
        handHasNotChangeCounter = 0;
    }

    @Override
    public Card chooseWhichCardToDiscard(DrawPile drawPile, DiscardPile discardPile, GameContext gameContext) {
//        int beforeHashCode = getHand().cards().hashCode();
        int totalBefore = total();
        final Card cardToDiscard = super.chooseWhichCardToDiscard(drawPile, discardPile, gameContext);
//        int afterHashCode = getHand().cards().hashCode();
//        if (beforeHashCode == afterHashCode) {
//            handChanged = false;
        if (total() == totalBefore) {
            handHasNotChangeCounter++;
        }
        else {
            handHasNotChangeCounter = 0;
        }
        LOGGER.debug("player = " + this);
//        }
//        else {
//            handChanged = true;
//            handHasNotChangeCounter = 0;
//        }
        return cardToDiscard;
    }

    @Override
    public boolean decidesToKnock(GameContext gameContext) {
        boolean decidesToKnock = super.decidesToKnock(gameContext);
        if (!decidesToKnock && handHasNotChangedIn4Turns()) {
            decidesToKnock = true;
            LOGGER.debug(getName() + " breaks stalemate by knocking");
        }
        return decidesToKnock;
    }

    private boolean handHasNotChangedIn4Turns() {
        final boolean handHasNotChanged = handHasNotChangeCounter > 40;
        LOGGER.debug("handHasNotChangeCounter = " + handHasNotChangeCounter+", handHasNotChanged = " + handHasNotChanged);
        if (handHasNotChanged)
            System.exit(0);
        return handHasNotChanged;
    }

    @Override
    public void clearHand() {
        super.clearHand();
        handHasNotChangeCounter = 0;
    }
}
