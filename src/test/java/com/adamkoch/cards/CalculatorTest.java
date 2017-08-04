package com.adamkoch.cards;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * <p>Created by aakoch on 2017-08-03.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class CalculatorTest {
    @Test
    public void testTotalCards_empty() throws Exception {
        List<Card> cards = new ArrayList<>();

        assertEquals(0, Calculator.totalCards(cards));
    }

    @Test
    public void testTotalCards_ace() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        assertEquals(11, Calculator.totalCards(cards));
    }

    @Test
    public void testTotalCards_2cards() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.HEARTS, Rank.THREE));
        assertEquals(14, Calculator.totalCards(cards));
    }

    @Test
    public void testTotalCards_3cards() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.HEARTS, Rank.THREE));
        cards.add(new Card(Suit.HEARTS, Rank.FIVE));
        assertEquals(19, Calculator.totalCards(cards));
    }

    @Test
    public void testTotalCards_3cardsDifferent() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.CLUBS, Rank.THREE));
        cards.add(new Card(Suit.SPADES, Rank.FIVE));
        assertEquals(11, Calculator.totalCards(cards));
    }

    @Test
    public void testTotalCards_3cardsDifferent2() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.JACK));
        cards.add(new Card(Suit.HEARTS, Rank.THREE));
        cards.add(new Card(Suit.SPADES, Rank.FIVE));
        assertEquals(13, Calculator.totalCards(cards));
    }

    @Test
    public void testTotalCards_3cardsDifferent3() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.CLUBS, Rank.ACE));
        cards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        assertEquals(21, Calculator.totalCards(cards));
    }

}