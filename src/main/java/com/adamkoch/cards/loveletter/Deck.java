package com.adamkoch.cards.loveletter;

import com.adamkoch.cards.ICard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.adamkoch.cards.loveletter.Card.*;

/**
 * <p>Created by aakoch on 2017-09-19.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Deck {

    public static final List<Card> CARDS = Arrays.asList(PRINCESS, COUNTESS, KING, PRINCE,
            PRINCE, HANDMAID, HANDMAID, BARON, BARON, PRIEST, PRIEST,
            GUARD, GUARD, GUARD, GUARD, GUARD);

    private final List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>(CARDS);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
}
