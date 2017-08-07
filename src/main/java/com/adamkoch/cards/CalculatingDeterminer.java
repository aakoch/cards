package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.adamkoch.cards.RuleFactory.rankAndPickCardsToDiscard;
import static com.adamkoch.cards.utils.CardUtil.addingCardWouldMake2OfTheSameSuit;
import static com.adamkoch.cards.utils.CardUtil.addingCardWouldMake3OfTheSameSuit;
import static com.adamkoch.cards.utils.CardUtil.createSuitListMap;

/**
 * <p>Created by aakoch on 2017-08-05.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class CalculatingDeterminer extends Determiner {
    private static final Logger LOGGER = LogManager.getLogger(CalculatingDeterminer.class);

    public CalculatingDeterminer(Player player) {
        super(player);
    }

    @Override
    public boolean cardWouldImproveHand(Card card, List<Card> cards, GameContext gameContext) {

        final boolean chanceOfGettingBetterCardWithSameSuit;
        if (gameContext == null) {
            chanceOfGettingBetterCardWithSameSuit = false;
        }
        else {
            final double percentOfGettingBetterCardWithSameSuit = gameContext.chanceOfGettingBetterCardWithSameSuit(
                    card);
            LOGGER.debug(String.format("chanceOfGettingBetterCardWithSameSuit=%.2f%%",
                    percentOfGettingBetterCardWithSameSuit * 100));
            chanceOfGettingBetterCardWithSameSuit = percentOfGettingBetterCardWithSameSuit > .18;
        }

        if (addingCardWouldMake3OfTheSameSuit(card, cards)) {
            return true;
        }
        else if (addingCardWouldMake2OfTheSameSuit(card, cards)) {
            // rank and choose
            List<Card> newCardsList = new ArrayList<>(cards);
            newCardsList.add(card);
            Map<Suit, List<Card>> map = createSuitListMap(newCardsList);
            List<Card> list = rankAndPickCardsToDiscard(map);

            // if the discarded cards includes the card passed in, then return false
            return list.contains(card);
        }
        else if (newPairBetterThanCurrentPair(cards, card)) {
            return true;
        }
        else {
            return false;
        }
    }
}
