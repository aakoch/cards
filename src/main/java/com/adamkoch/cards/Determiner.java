package com.adamkoch.cards;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>Created by aakoch on 2017-08-01.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Determiner {

    public static Card chooseCardToDiscard(Hand hand, DiscardPile discardPile) {
        final List<Card> cards = hand.cards();
//        Collections.sort(cards, new Comparator<Card>() {
//            @Override
//            public int compare(Card o1, Card o2) {
//                if (o1.getSuit() == o2.getSuit()) {
//                if (suit == 0) {
//                    return o2.getRank().getNumericRank(true) - o1.getRank().getNumericRank(true);
//                }
//                return suit;
//            }
//        });

        return cards.remove(cards.size() - 1);
    }
}
