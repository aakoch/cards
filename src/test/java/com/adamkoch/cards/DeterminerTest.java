package com.adamkoch.cards;

import com.adamkoch.cards.utils.CardUtil;
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
    private Determiner determiner;
    private KnockStrategy knockStrategy;
    private PickUpStrategy pickupStrategy;
    private Player player;
    private DiscardStrategy discardStrategy;

    @Before
    public void setUp() {
        hearts1 = new Card(Rank.ACE, Suit.HEARTS);
        hearts2 = new Card(Rank.TWO, Suit.HEARTS);
        hearts5 = new Card(Rank.FIVE, Suit.HEARTS);
        hearts7 = new Card(Rank.SEVEN, Suit.HEARTS);
        hearts8 = new Card(Rank.EIGHT, Suit.HEARTS);
        hearts9 = new Card(Rank.NINE, Suit.HEARTS);
        spades1 = new Card(Rank.ACE, Suit.SPADES);
        clubs1 = new Card(Rank.ACE, Suit.CLUBS);
        clubs2 = new Card(Rank.TWO, Suit.CLUBS);
        clubs3 = new Card(Rank.THREE, Suit.CLUBS);

        knockStrategy = new DefaultKnockStrategy();
        determiner = new Determiner();
        pickupStrategy = new DefaultPickUpStrategy(determiner);
        discardStrategy = new DefaultDiscardStrategy();

        player = new EasyPlayer("Test", 22, knockStrategy, pickupStrategy, discardStrategy);
    }
    
    @Test
    public void testChooseCardToDiscard_3Hearts_1Club() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.HEARTS));
        cards.add(new Card(Rank.KING, Suit.HEARTS));
        cards.add(new Card(Rank.QUEEN, Suit.HEARTS));
        final Card twoOfClubs = new Card(Rank.TWO, Suit.CLUBS);
        cards.add(twoOfClubs);
        Collections.shuffle(cards);
        player.setHand(cards);
        assertEquals(twoOfClubs, determiner.chooseCardToDiscard(player, null));
    }

    @Test
    public void testChooseCardToDiscard_3Clubs_1Heart() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(clubs1);
        cards.add(new Card(Rank.KING, Suit.CLUBS));
        cards.add(new Card(Rank.NINE, Suit.CLUBS));
        cards.add(clubs2);
        Collections.shuffle(cards);
        player.setHand(cards);
        assertEquals(clubs2, determiner.chooseCardToDiscard(player, null));
    }

    @Test
    public void testChooseCardToDiscard_3Aces_1Deuce() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.HEARTS));
        cards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        cards.add(new Card(Rank.ACE, Suit.CLUBS));
        final Card twoOfClubs = new Card(Rank.TWO, Suit.SPADES);
        cards.add(twoOfClubs);
        Collections.shuffle(cards);
        player.setHand(cards);
        assertEquals(twoOfClubs, determiner.chooseCardToDiscard(player, null));
    }

    @Test
    public void testChooseCardToDiscard_2Aces() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(hearts1);
        cards.add(clubs1);
        cards.add(clubs2);
        cards.add(clubs3);
        Collections.shuffle(cards);
        player.setHand(cards);
        assertEquals(hearts1, determiner.chooseCardToDiscard(player, null));
    }

    @Test
    public void testChooseCardToDiscard_lowestOfSuit() {
        List<Card> cards = new ArrayList();
        cards.add(this.hearts1);
        cards.add(this.hearts5);
        cards.add(this.hearts7);
        cards.add(this.hearts8);
        player.setHand(cards);
        assertEquals(this.hearts5, determiner.chooseCardToDiscard(player, null));
    }

    @Test
    public void testGetNumberOfCardsPerSuit() {
        List<Card> cards = new ArrayList();
        cards.add(this.hearts1);
        cards.add(this.hearts5);
        cards.add(this.hearts7);
        cards.add(this.hearts8);
        assertEquals(4, CardUtil.getNumberOfCardsPerSuit(cards).get(Suit.HEARTS).intValue());

        cards.clear();
        cards.add(this.hearts1);
        cards.add(this.hearts5);
        cards.add(this.hearts7);
        cards.add(this.spades1);
        final Map<Suit, Integer> numberOfCardsPerSuit = CardUtil.getNumberOfCardsPerSuit(cards);
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
        player.setHand(cards);
        assertEquals(hearts2, determiner.chooseCardToDiscard(player, null));
    }

    @Test
    public void testChooseCardToDiscard_real1() throws Exception {
        List<Card> cards = new ArrayList<>();
        final Card heartsJack = new Card(Rank.JACK, Suit.HEARTS);
        cards.add(heartsJack);
        cards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        cards.add(new Card(Rank.SIX, Suit.CLUBS));
        cards.add(new Card(Rank.TEN, Suit.CLUBS));
        player.setHand(cards);
        assertEquals(heartsJack, determiner.chooseCardToDiscard(player, null));
    }

    @Test
    public void testChooseCardToDiscard_real2() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.JACK, Suit.CLUBS));
        cards.add(new Card(Rank.FOUR, Suit.CLUBS));
        cards.add(new Card(Rank.SEVEN, Suit.SPADES));

        Card newCard = new Card(Rank.FIVE, Suit.SPADES);
        cards.add(newCard);

        player.setHand(cards);
        final Card actualCard = determiner.chooseCardToDiscard(player, null);
        assertEquals("Discarded card should have been " + newCard + " out of " + cards + ", but was " + actualCard,
                newCard, actualCard);
    }

    @Test
    public void testChooseCardToDiscard_real3() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.HEARTS));
        cards.add(new Card(Rank.EIGHT, Suit.DIAMONDS));
        cards.add(new Card(Rank.KING, Suit.HEARTS));

        Card newCard = new Card(Rank.EIGHT, Suit.SPADES);
        cards.add(newCard);

        player.setHand(cards);
        final Card actualCard = determiner.chooseCardToDiscard(player, null);
        assertEquals("Discarded card should have been " + newCard + " out of " + cards + ", but was " + actualCard,
                newCard, actualCard);
    }

    @Test
    public void testCardWouldNotImproveHand() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.SPADES));
        cards.add(new Card(Rank.ACE, Suit.CLUBS));
        cards.add(new Card(Rank.KING, Suit.SPADES));

        Card newCard = new Card(Rank.EIGHT, Suit.HEARTS);
        assertFalse("Adding " + newCard + " should NOT improve hand " + cards + ", but it returned true",
                determiner.cardWouldImproveHand(newCard, cards, null));
    }

    @Test
    public void testCardWouldImproveHand3() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.JACK, Suit.CLUBS));
        cards.add(new Card(Rank.FOUR, Suit.CLUBS));
        cards.add(new Card(Rank.SEVEN, Suit.SPADES));

        Card newCard = new Card(Rank.FIVE, Suit.SPADES);
        assertFalse("Adding " + newCard + " should NOT improve hand " + cards + ", but it returned true",
                determiner.cardWouldImproveHand(newCard, cards, null));
    }

    @Test
    public void testCardWouldImproveHand2() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.SPADES));
        cards.add(new Card(Rank.ACE, Suit.CLUBS));
        cards.add(new Card(Rank.KING, Suit.SPADES));

        Card newCard = new Card(Rank.EIGHT, Suit.SPADES);
        assertTrue(determiner.cardWouldImproveHand(newCard, cards, null));
    }

    @Test
    public void testCardWouldImproveHand_real() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.THREE, Suit.CLUBS));
        cards.add(new Card(Rank.TEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.THREE, Suit.DIAMONDS));

        Card newCard = new Card(Rank.ACE, Suit.CLUBS);
        assertTrue("Adding " + newCard + " should improve hand " + cards + ", but it returned false",
                determiner.cardWouldImproveHand(newCard, cards, null));
    }

    @Test
    public void testCardWouldImproveHand_real2() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.QUEEN, Suit.HEARTS));
        cards.add(new Card(Rank.TWO, Suit.SPADES));
        cards.add(new Card(Rank.FIVE, Suit.HEARTS));

        Card newCard = new Card(Rank.TWO, Suit.HEARTS);
        GameContext gameContext = new GameContext();
        final List<Card> cardsLeft = new StandardDeck().cards();
        cardsLeft.removeAll(cards);
        gameContext.cardsStillOutThere(cardsLeft);

        assertTrue("Adding " + newCard + " should improve hand " + cards + ", but it returned false", determiner
                .cardWouldImproveHand(newCard, cards, gameContext));
    }

    @Test
    public void testCardWouldImproveHand_real3() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.NINE, Suit.HEARTS));
        cards.add(new Card(Rank.SIX, Suit.CLUBS));
        cards.add(new Card(Rank.THREE, Suit.CLUBS));

        Card newCard = new Card(Rank.EIGHT, Suit.HEARTS);
        GameContext gameContext = new GameContext();
        final List<Card> cardsLeft = new StandardDeck().cards();
        cardsLeft.removeAll(cards);
        gameContext.cardsStillOutThere(cardsLeft);

        assertTrue("Adding " + newCard + " should improve hand " + cards + ", but it returned false", determiner
                .cardWouldImproveHand(newCard, cards, gameContext));
    }

    @Test
    public void testCardWouldImproveHand_real4() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TEN, Suit.SPADES));
        cards.add(new Card(Rank.FOUR, Suit.DIAMONDS));
        cards.add(new Card(Rank.EIGHT, Suit.CLUBS));

        Card newCard = new Card(Rank.ACE, Suit.HEARTS);
        GameContext gameContext = new GameContext();
        final List<Card> cardsLeft = new StandardDeck().cards();
        cardsLeft.removeAll(cards);
        gameContext.cardsStillOutThere(cardsLeft);

        assertTrue("Adding " + newCard + " should improve hand " + cards + ", but it returned false", determiner
                .cardWouldImproveHand(newCard, cards, gameContext));
    }

    @Test
    public void testCardWouldImproveHand_real5() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.KING, Suit.DIAMONDS));
        cards.add(new Card(Rank.TEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.EIGHT, Suit.DIAMONDS));

        Card newCard = new Card(Rank.THREE, Suit.DIAMONDS);
        GameContext gameContext = new GameContext();

        assertFalse("Adding " + newCard + " should NOT improve hand " + cards + ", but it returned true", determiner
                .cardWouldImproveHand(newCard, cards, gameContext));
    }

    @Test
    public void testCardWouldImproveHand_fake2() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.QUEEN, Suit.HEARTS));
        cards.add(new Card(Rank.TWO, Suit.SPADES));
        cards.add(new Card(Rank.FIVE, Suit.HEARTS));

        Card newCard = new Card(Rank.FIVE, Suit.HEARTS);
        GameContext gameContext = new GameContext();

        assertTrue("Adding " + newCard + " should improve hand " + cards + ", but it returned false", determiner
                .cardWouldImproveHand(newCard, cards, gameContext));
    }

    @Test
    public void testNewPairBetterThanCurrentPair() throws Exception {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.QUEEN, Suit.HEARTS));
        cards.add(new Card(Rank.FIVE, Suit.SPADES));
        cards.add(new Card(Rank.TWO, Suit.HEARTS));

        Card newCard = new Card(Rank.QUEEN, Suit.SPADES);

        assertTrue("New pair should be better than old, but it returned false. cards=" + cards + ", newCard=" + newCard,
                Determiner.newPairBetterThanCurrentPair(cards, newCard));
    }

    @Test
    public void testRankAndPick() throws Exception {

        Map<Suit, List<Card>> map = new HashMap<>();

        List<Card> hearts = new ArrayList<>();
        List<Card> spades = new ArrayList<>();

        hearts.add(new Card(Rank.TEN, Suit.HEARTS));
        hearts.add(new Card(Rank.FOUR, Suit.HEARTS));

        spades.add(new Card(Rank.SEVEN, Suit.SPADES));
        spades.add(new Card(Rank.FIVE, Suit.SPADES));

        map.put(Suit.HEARTS, hearts);
        map.put(Suit.SPADES, spades);

        assertEquals(spades, RuleFactory.rankAndPickCardsToDiscard(map));
    }

    @Test
    public void testRankAndPick_real() throws Exception {

        Map<Suit, List<Card>> map = new HashMap<>();

        List<Card> clubs = new ArrayList<>();
        List<Card> diamonds = new ArrayList<>();

        clubs.add(new Card(Rank.THREE, Suit.CLUBS));
        clubs.add(new Card(Rank.ACE, Suit.CLUBS));

        diamonds.add(new Card(Rank.TEN, Suit.DIAMONDS));
        diamonds.add(new Card(Rank.THREE, Suit.DIAMONDS));

        map.put(Suit.CLUBS, clubs);
        map.put(Suit.DIAMONDS, diamonds);

        assertEquals(diamonds, RuleFactory.rankAndPickCardsToDiscard(map));
    }
}