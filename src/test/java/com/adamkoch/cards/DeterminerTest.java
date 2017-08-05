package com.adamkoch.cards;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <p>Created by aakoch on 2017-08-01.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class DeterminerTest {

    private Card hearts1;
    private Card hearts2;
    private Card hearts5;
    private Card hearts7;
    private Card hearts8;
    private Card hearts9;
    private Card spades1;
    private Card clubs1;
    private Card clubs2;
    private Card clubs3;

    @Before
    public void setUp() {
        hearts1 = new Card(Suit.HEARTS, Rank.ACE);
        hearts2 = new Card(Suit.HEARTS, Rank.TWO);
        hearts5 = new Card(Suit.HEARTS, Rank.FIVE);
        hearts7 = new Card(Suit.HEARTS, Rank.SEVEN);
        hearts8 = new Card(Suit.HEARTS, Rank.EIGHT);
        hearts9 = new Card(Suit.HEARTS, Rank.NINE);
        spades1 = new Card(Suit.SPADES, Rank.ACE);
        clubs1 = new Card(Suit.CLUBS, Rank.ACE);
        clubs2 = new Card(Suit.CLUBS, Rank.TWO);
        clubs3 = new Card(Suit.CLUBS, Rank.THREE);
    }
    
    @Test
    public void testChooseCardToDiscard_3Hearts_1Club() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.HEARTS, Rank.KING));
        cards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        final Card twoOfClubs = new Card(Suit.CLUBS, Rank.TWO);
        cards.add(twoOfClubs);
        Collections.shuffle(cards);
        assertEquals(twoOfClubs, Determiner.chooseCardToDiscard(cards, null));
    }

    @Test
    public void testChooseCardToDiscard_3Clubs_1Heart() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(clubs1);
        cards.add(new Card(Suit.CLUBS, Rank.KING));
        cards.add(new Card(Suit.CLUBS, Rank.NINE));
        cards.add(clubs2);
        Collections.shuffle(cards);
        assertEquals(clubs2, Determiner.chooseCardToDiscard(cards, null));
    }

    @Test
    public void testChooseCardToDiscard_3Aces_1Deuce() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        cards.add(new Card(Suit.CLUBS, Rank.ACE));
        final Card twoOfClubs = new Card(Suit.SPADES, Rank.TWO);
        cards.add(twoOfClubs);
        Collections.shuffle(cards);
        assertEquals(twoOfClubs, Determiner.chooseCardToDiscard(cards, null));
    }

    @Test
    public void testChooseCardToDiscard_2Aces() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(hearts1);
        cards.add(clubs1);
        cards.add(clubs2);
        cards.add(clubs3);
        Collections.shuffle(cards);
        assertEquals(hearts1, Determiner.chooseCardToDiscard(cards, null));
    }

    @Test
    public void testChooseCardToDiscard_lowestOfSuit() {
        List<Card> cards = new ArrayList();
        cards.add(this.hearts1);
        cards.add(this.hearts5);
        cards.add(this.hearts7);
        cards.add(this.hearts8);
        assertEquals(this.hearts5, Determiner.chooseCardToDiscard(cards, null));
    }

    @Test
    public void testGetNumberOfCardsPerSuit() {
        List<Card> cards = new ArrayList();
        cards.add(this.hearts1);
        cards.add(this.hearts5);
        cards.add(this.hearts7);
        cards.add(this.hearts8);
        assertEquals(4, Determiner.getNumberOfCardsPerSuit(cards).get(Suit.HEARTS).intValue());

        cards.clear();
        cards.add(this.hearts1);
        cards.add(this.hearts5);
        cards.add(this.hearts7);
        cards.add(this.spades1);
        final Map<Suit, Integer> numberOfCardsPerSuit = Determiner.getNumberOfCardsPerSuit(cards);
        assertEquals(3, numberOfCardsPerSuit.get(Suit.HEARTS).intValue());
        assertEquals(1, numberOfCardsPerSuit.get(Suit.SPADES).intValue());
    }

    @Test
    public void testChooseCardToDiscard_2Aces_2Suits() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(hearts1);
        cards.add(clubs1);
        cards.add(hearts2);
        cards.add(clubs3);
        Collections.shuffle(cards);
        assertEquals(hearts2, Determiner.chooseCardToDiscard(cards, null));
    }

    @Test
    public void testChooseCardToDiscard_real1() throws Exception {
        List<Card> cards = new ArrayList<>();
        final Card heartsJack = new Card(Suit.HEARTS, Rank.JACK);
        cards.add(heartsJack);
        cards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        cards.add(new Card(Suit.CLUBS, Rank.SIX));
        cards.add(new Card(Suit.CLUBS, Rank.TEN));
        assertEquals(heartsJack, Determiner.chooseCardToDiscard(cards, null));
    }

    @Test
    public void testChooseCardToDiscard_real2() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUBS, Rank.JACK));
        cards.add(new Card(Suit.CLUBS, Rank.FOUR));
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));

        Card newCard = new Card(Suit.SPADES, Rank.FIVE);
        cards.add(newCard);

        final Card actualCard = Determiner.chooseCardToDiscard(cards, null);
        assertEquals("Discarded card should have been " + newCard + " out of " + cards + ", but was " + actualCard,
                newCard, actualCard);
    }

    @Test
    public void testCardWouldNotImproveHand() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));
        cards.add(new Card(Suit.CLUBS, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.KING));

        Card newCard = new Card(Suit.HEARTS, Rank.EIGHT);
        assertFalse("Adding " + newCard + " should NOT improve hand " + cards + ", but it returned true",
                Determiner.cardWouldImproveHand(newCard, cards, null));
    }

    @Test
    public void testCardWouldImproveHand3() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUBS, Rank.JACK));
        cards.add(new Card(Suit.CLUBS, Rank.FOUR));
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));

        Card newCard = new Card(Suit.SPADES, Rank.FIVE);
        assertFalse("Adding " + newCard + " should NOT improve hand " + cards + ", but it returned true", Determiner.cardWouldImproveHand(newCard, cards, null));
    }

    @Test
    public void testCardWouldImproveHand2() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));
        cards.add(new Card(Suit.CLUBS, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.KING));

        Card newCard = new Card(Suit.SPADES, Rank.EIGHT);
        assertTrue(Determiner.cardWouldImproveHand(newCard, cards, null));
    }

    @Test
    public void testCardWouldImproveHand_real() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUBS, Rank.THREE));
        cards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.THREE));

        Card newCard = new Card(Suit.CLUBS, Rank.ACE);
        assertTrue("Adding " + newCard + " should improve hand " + cards + ", but it returned false",
                Determiner.cardWouldImproveHand(newCard, cards, null));
    }

    @Test
    public void testCardWouldImproveHand_real2() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        cards.add(new Card(Suit.SPADES, Rank.TWO));
        cards.add(new Card(Suit.HEARTS, Rank.FIVE));

        Card newCard = new Card(Suit.HEARTS, Rank.TWO);
        GameContext gameContext = new GameContext();
        final List<Card> cardsLeft = new StandardDeck().cards();
        cardsLeft.removeAll(cards);
        gameContext.cardsStillOutThere(cardsLeft);

        assertTrue("Adding " + newCard + " should improve hand " + cards + ", but it returned false", Determiner
                .cardWouldImproveHand(newCard, cards, gameContext));
    }

    @Test
    public void testCardWouldImproveHand_real3() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.NINE));
        cards.add(new Card(Suit.CLUBS, Rank.SIX));
        cards.add(new Card(Suit.CLUBS, Rank.THREE));

        Card newCard = new Card(Suit.HEARTS, Rank.EIGHT);
        GameContext gameContext = new GameContext();
        final List<Card> cardsLeft = new StandardDeck().cards();
        cardsLeft.removeAll(cards);
        gameContext.cardsStillOutThere(cardsLeft);

        assertTrue("Adding " + newCard + " should improve hand " + cards + ", but it returned false", Determiner
                .cardWouldImproveHand(newCard, cards, gameContext));
    }

    @Test
    public void testCardWouldImproveHand_real4() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.SPADES, Rank.TEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.FOUR));
        cards.add(new Card(Suit.CLUBS, Rank.EIGHT));

        Card newCard = new Card(Suit.HEARTS, Rank.ACE);
        GameContext gameContext = new GameContext();
        final List<Card> cardsLeft = new StandardDeck().cards();
        cardsLeft.removeAll(cards);
        gameContext.cardsStillOutThere(cardsLeft);

        assertTrue("Adding " + newCard + " should improve hand " + cards + ", but it returned false", Determiner
                .cardWouldImproveHand(newCard, cards, gameContext));
    }

    @Test
    public void testCardWouldImproveHand_real5() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.DIAMONDS, Rank.KING));
        cards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.EIGHT));

        Card newCard = new Card(Suit.DIAMONDS, Rank.THREE);
        GameContext gameContext = new GameContext();

        assertFalse("Adding " + newCard + " should NOT improve hand " + cards + ", but it returned true", Determiner
                .cardWouldImproveHand(newCard, cards, gameContext));
    }

    @Test
    public void testCardWouldImproveHand_fake2() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        cards.add(new Card(Suit.SPADES, Rank.TWO));
        cards.add(new Card(Suit.HEARTS, Rank.FIVE));

        Card newCard = new Card(Suit.HEARTS, Rank.FIVE);
        GameContext gameContext = new GameContext();

        assertTrue("Adding " + newCard + " should improve hand " + cards + ", but it returned false", Determiner
                .cardWouldImproveHand(newCard, cards, gameContext));
    }


    @Test
    public void testNewPairBetterThanCurrentPair() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        cards.add(new Card(Suit.SPADES, Rank.FIVE));
        cards.add(new Card(Suit.HEARTS, Rank.TWO));

        Card newCard = new Card(Suit.SPADES, Rank.QUEEN);

        assertTrue("New pair should be better than old, but it returned false. cards=" + cards + ", newCard=" + newCard,
                Determiner.newPairBetterThanCurrentPair(cards, newCard));
    }

    @Test
    public void testRankAndPick() throws Exception {

        Map<Suit, List<Card>> map = new HashMap<>();

        List<Card> hearts = new ArrayList<>();
        List<Card> spades = new ArrayList<>();

        hearts.add(new Card(Suit.HEARTS, Rank.TEN));
        hearts.add(new Card(Suit.HEARTS, Rank.FOUR));

        spades.add(new Card(Suit.SPADES, Rank.SEVEN));
        spades.add(new Card(Suit.SPADES, Rank.FIVE));

        map.put(Suit.HEARTS, hearts);
        map.put(Suit.SPADES, spades);

        assertEquals(spades, Determiner.rankAndPickCardsToDiscard(map));
    }

    @Test
    public void testRankAndPick_real() throws Exception {

        Map<Suit, List<Card>> map = new HashMap<>();

        List<Card> clubs = new ArrayList<>();
        List<Card> diamonds = new ArrayList<>();

        clubs.add(new Card(Suit.CLUBS, Rank.THREE));
        clubs.add(new Card(Suit.CLUBS, Rank.ACE));

        diamonds.add(new Card(Suit.DIAMONDS, Rank.TEN));
        diamonds.add(new Card(Suit.DIAMONDS, Rank.THREE));

        map.put(Suit.CLUBS, clubs);
        map.put(Suit.DIAMONDS, diamonds);

        assertEquals(diamonds, Determiner.rankAndPickCardsToDiscard(map));
    }
}