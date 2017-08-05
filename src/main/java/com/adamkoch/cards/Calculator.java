package com.adamkoch.cards;

import com.google.common.base.Suppliers;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.synchronizedList;

/**
 * <p>Created by aakoch on 2017-08-03.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Calculator {
    private static final Logger LOGGER = LogManager.getLogger(Calculator.class);

    private static final Cache<List<Card>, Integer> cache = CacheBuilder.newBuilder()
                                                                        .build();

    public static int totalCards(List<Card> cards) {
        try {
            List<Card> synchronizedList = synchronizedList(cards);
            return cache.get(cards, new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Map<Suit, List<Card>> map = Determiner.createSuitListMap(synchronizedList);
                    int sum = 0;
                    for (Map.Entry<Suit, List<Card>> entry : map.entrySet()) {
                        sum = Math.max(sum, entry.getValue().stream().mapToInt(card -> card.getRank().getValue(true)).sum());
                    }

                    LOGGER.debug("cards = " + cards + ", sum = " + sum);
                    return sum;
                }
            });
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
