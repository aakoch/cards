package com.adamkoch.cards;

import java.util.List;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-26.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class DeckFactory {
    public static Deck standard() {
        return removeJokers(new StandardDeck());
    }

    public static Deck removeJokers(StandardDeck standardDeck) {
//        standardDeck.remove(JOKER);
//        standardDeck.remove(JOKER);
        return standardDeck;
    }
}
