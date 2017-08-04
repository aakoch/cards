package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * <p>
 * Created by aakoch on 2017.08.04.
 *
 * @author Adam Koch (aakoch)
 */
public class StalemateBreakingDecorator extends Player {
    private static final Logger LOGGER = LogManager.getLogger(Player.class);

    private final Player player;
    private int timesChooseCardToDiscard;
    private boolean handChanged;
    private int handHasNotChangeCounter = 0;

    public StalemateBreakingDecorator(Player player) {
        super(player.getName());
        this.player = player;
        timesChooseCardToDiscard = 0;
    }

    @Override
    protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
        return player.rank(cardsThatCanPlay, hand);
    }

    @Override
    public Card chooseWhichCardToDiscard(DrawPile drawPile, DiscardPile discardPile) {
        timesChooseCardToDiscard++;
        int beforHashCode = player.getHand().hashCode();
        final Card cardToDiscard = player.chooseWhichCardToDiscard(drawPile, discardPile);
        int afterHashCode = player.getHand().hashCode();
        if (beforHashCode == afterHashCode) {
            handChanged = false;
            handHasNotChangeCounter++;
        }
        else {
            handChanged = true;
            handHasNotChangeCounter = 0;
        }
        return cardToDiscard;
    }

    @Override
    public boolean decidesToKnock(GameContext gameContext) {
        boolean decidesToKnock = player.decidesToKnock(gameContext);
        if (!decidesToKnock && handHasNotChangedIn4Turns()) {
            decidesToKnock = true;
            LOGGER.debug(player.getName() + " breaks stalemate by knocking");
        }
        return decidesToKnock;
    }

    private boolean handHasNotChangedIn4Turns() {
        final boolean handHasNotChanged = !handChanged && handHasNotChangeCounter > 4;
        LOGGER.debug("handChanged = " + handChanged+", handHasNotChangeCounter = " + handHasNotChangeCounter+", handHasNotChanged = " + handHasNotChanged);
        return handHasNotChanged;
    }
}
