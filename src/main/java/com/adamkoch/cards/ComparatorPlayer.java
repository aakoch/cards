package com.adamkoch.cards;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-28.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class ComparatorPlayer extends Player {
    private final Comparator<Card> comparator;

    public ComparatorPlayer(String name, Comparator<Card> comparator) {
        super(name);
        this.comparator = comparator;
    }

    @Override
    protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
        Collections.sort(cardsThatCanPlay, comparator);
        return (List<Card>) cardsThatCanPlay;
    }

}
