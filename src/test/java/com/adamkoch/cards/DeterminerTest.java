package com.adamkoch.cards;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-08-01.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class DeterminerTest {
    @Test
    public void testChooseCardToDiscard() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.HEARTS, Rank.KING));
        cards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        final Card twoOfClubs = new Card(Suit.CLUBS, Rank.TWO);
        cards.add(twoOfClubs);
        Collections.shuffle(cards);
        Hand hand = new Hand(cards);
        assertEquals(twoOfClubs, Determiner.chooseCardToDiscard(hand, null));
    }

    @Test
    public void testChooseCardToDiscard2() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUBS, Rank.ACE));
        cards.add(new Card(Suit.CLUBS, Rank.KING));
        cards.add(new Card(Suit.CLUBS, Rank.NINE));
        final Card twoOfClubs = new Card(Suit.HEARTS, Rank.TWO);
        cards.add(twoOfClubs);
        Collections.shuffle(cards);
        Hand hand = new Hand(cards);
        assertEquals(twoOfClubs, Determiner.chooseCardToDiscard(hand, null));
    }

    @Test
    public void testChooseCardToDiscard3() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        cards.add(new Card(Suit.CLUBS, Rank.ACE));
        final Card twoOfClubs = new Card(Suit.SPADES, Rank.TWO);
        cards.add(twoOfClubs);
        Collections.shuffle(cards);
        Hand hand = new Hand(cards);
        assertEquals(twoOfClubs, Determiner.chooseCardToDiscard(hand, null));
    }

}