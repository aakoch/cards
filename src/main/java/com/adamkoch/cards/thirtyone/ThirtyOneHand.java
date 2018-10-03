package com.adamkoch.cards.thirtyone;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.adamkoch.cards.Hand;
import com.adamkoch.cards.Rank;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class ThirtyOneHand extends Hand<ThirtyOneCard> {

    private final Cache<ThirtyOneHand, Integer> cache = CacheBuilder.newBuilder()
            .maximumSize(1).build();

    ThirtyOneHand() {
        this(Collections.emptyList());
    }

    ThirtyOneHand(List<ThirtyOneCard> cards) {
        super(cards);
    }

    /**
     * Cachable score
     */
    public int currentScore() {
        try {
            return cache.get(this, () -> {
                return 0;
            });
        } catch (ExecutionException e) {
            throw new RuntimeException("Could not get cache for " + this, e);
        }
    }

    public void addCard(ThirtyOneCard card) {
        cache.invalidateAll();
        super.addCard(card);
    }
}
