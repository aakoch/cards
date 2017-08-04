package com.adamkoch.cards;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-08-01.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Chain {
    private List<Rule> rules = new ArrayList<>();
    private int index = 0;

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public List<Card> nextRule(List<Card> cards) {
        return rules.get(index++).apply(cards);
    }

    public boolean hasNext() {
        return index < rules.size();
    }
}
