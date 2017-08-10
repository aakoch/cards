package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by aakoch on 2017-08-08.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class DefaultDiscardStrategy implements DiscardStrategy {

    private static final Logger LOGGER = LogManager.getLogger(DefaultDiscardStrategy.class);

    @Override
    public Card chooseWhichCardToDiscard(Player player, GameContext gameContext) {

        final Determiner determiner = new Determiner();
        Card cardToDiscard = determiner.chooseCardToDiscard(player, gameContext);

//        if (nextPlayerWillWinWithCard(cardToDiscard, gameContext)) {
//            LOGGER.error("Next player will win if " + player.getName() + " plays " + cardToDiscard);
//            final List<Card> newCardList = new ArrayList<>(player.getHand());
//            newCardList.remove(cardToDiscard);
//            cardToDiscard = determiner.chooseCardToDiscard(this, gameContext);
//        }

        return cardToDiscard;
    }
}
