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
    public void testObj() {
        final Object obj = null;
        System.out.println("String.valueOf(obj) = " + String.valueOf(obj));
    }

    @Test
    public void testString() {
        final String str = null;
        System.out.println("String.valueOf(str) = " + String.valueOf(str));
    }

    @Test
    public void testNull() {
        System.out.println("String.valueOf(null) = " + String.valueOf((String) null));
    }




    @Test
    public void testTotalCards_empty() throws Exception {
        List<Card> cards = new ArrayList<>();

        assertEquals(0, Calculator.totalCards(cards));
    }

    @Test
    public void testTotalCards_ace() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.HEARTS));
        assertEquals(11, Calculator.totalCards(cards));
    }

    @Test
    public void testTotalCards_2cards() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.HEARTS));
        cards.add(new Card(Rank.THREE, Suit.HEARTS));
        assertEquals(14, Calculator.totalCards(cards));
    }

    @Test
    public void testTotalCards_3cards() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.HEARTS));
        cards.add(new Card(Rank.THREE, Suit.HEARTS));
        cards.add(new Card(Rank.FIVE, Suit.HEARTS));
        assertEquals(19, Calculator.totalCards(cards));
    }

    @Test
    public void testTotalCards_3cardsDifferent() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.HEARTS));
        cards.add(new Card(Rank.THREE, Suit.CLUBS));
        cards.add(new Card(Rank.FIVE, Suit.SPADES));
        assertEquals(11, Calculator.totalCards(cards));
    }

    @Test
    public void testTotalCards_3cardsDifferent2() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.JACK, Suit.HEARTS));
        cards.add(new Card(Rank.THREE, Suit.HEARTS));
        cards.add(new Card(Rank.FIVE, Suit.SPADES));
        assertEquals(13, Calculator.totalCards(cards));
    }

    @Test
    public void testTotalCards_3cardsDifferent3() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.HEARTS));
        cards.add(new Card(Rank.ACE, Suit.CLUBS));
        cards.add(new Card(Rank.QUEEN, Suit.HEARTS));
        assertEquals(21, Calculator.totalCards(cards));
    }

}