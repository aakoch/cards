package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.adamkoch.cards.Rank.*;

/**
 * <p>Created by aakoch on 2017-08-03.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Calculator {
    private static final Logger LOGGER = LogManager.getLogger(Calculator.class);

    public static int totalCards(List<Card> cards) {
        Map<Suit, List<Card>> map = Determiner.createSuitListMap(cards);

        int sum = 0;
        for (Map.Entry<Suit, List<Card>> entry : map.entrySet()) {
            sum = Math.max(sum, entry.getValue().stream().mapToInt(card -> convert(card.getRank())).sum());
        }

        LOGGER.debug("cards = " + cards + ", sum = " + sum);
        return sum;
    }

    private static int convert(Rank rank) {
        int value = 0;
        if (rank == ACE) {
            value = 11;
        }
        else if (rank == JACK || rank == QUEEN || rank == KING) {
            value = 10;
        }
        else {
            value = rank.getNumericRank(true);
        }
        return value;
    }
}
