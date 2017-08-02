package com.adamkoch.cards;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 *
 * <p>Created by aakoch on 2017-07-28.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class PlayerFactoryTest {
    private Card hearts1;
    private Card hearts5;
    private Card hearts7;
    private Card hearts8;
    private Card hearts9;
    private Card spades1;

    @Before
    public void setUp() {
        hearts1 = new Card(Suit.HEARTS, Rank.ACE);
        hearts5 = new Card(Suit.HEARTS, Rank.FIVE);
        hearts7 = new Card(Suit.HEARTS, Rank.SEVEN);
        hearts8 = new Card(Suit.HEARTS, Rank.EIGHT);
        hearts9 = new Card(Suit.HEARTS, Rank.NINE);
        spades1 = new Card(Suit.SPADES, Rank.ACE);
    }

    @Test
    public void testAfter() throws Exception {
        assertTrue(PlayerFactory.after(hearts7, hearts8));
        assertTrue(PlayerFactory.after(hearts8, hearts9));
        assertTrue(PlayerFactory.after(hearts7, hearts5));
        assertTrue(PlayerFactory.after(hearts5, hearts1));

        assertFalse(PlayerFactory.after(hearts8, hearts7));
        assertFalse(PlayerFactory.after(hearts9, hearts8));
        assertFalse(PlayerFactory.after(hearts5, hearts7));
        assertFalse(PlayerFactory.after(hearts1, hearts5));

        assertFalse(PlayerFactory.after(hearts5, spades1));
    }

    @Test
    public void testSameSuit() {
        List<Card> cards = new ArrayList();
        cards.add(this.hearts1);
        cards.add(this.hearts5);
        cards.add(this.hearts7);
        cards.add(this.hearts8);
        assertEquals(this.hearts5, this.determineCardToDiscard(cards));
    }

    private Card determineCardToDiscard(List<Card> cards) {

        Chain chain = new Chain();
        chain.addRule(createAnotherRule());
        chain.addRule(createNumericRankRule());

        Card returnCard = null;

        while(returnCard == null) {
            returnCard = chain.nextRule(cards, returnCard);
        }

        return returnCard;
    }

    private Rule createAnotherRule() {
        return new Rule() {
            @Override
            public Card apply(List<Card> cards) {
                Card returnCard = null;
                Map<Suit, Integer> numberOfCardsPerSuit = getNumberOfCardsPerSuit(cards);
                if (threeOfOneSuit(numberOfCardsPerSuit)) {
                    final Card oddSuit = oddSuit(cards, numberOfCardsPerSuit);
                    if (!discardingCardWillResultInAnotherPlayerGetting31(oddSuit)) {
                        returnCard = oddSuit;
                    }
                }
                return returnCard;
            }
        };
    }

    private Rule createNumericRankRule() {
        return new Rule() {

            @Override
            public Card apply(List<Card> cards) {
                cards.sort(Comparator.comparingInt(o -> o.getRank().getNumericRank(true)));
                return cards.get(0);
            }
        };
    }

    private boolean discardingCardWillResultInAnotherPlayerGetting31(Card oddSuit) {
        return false;
    }

    private Card oddSuit(List<Card> cards, Map<Suit, Integer> numberOfCardsPerSuit) {
        Suit oddSuit = getOddSuit(numberOfCardsPerSuit);
        return cards.parallelStream().filter(card -> card.getSuit() == oddSuit).findFirst().orElseThrow(RuntimeException::new);
    }

    private Suit getOddSuit(Map<Suit, Integer> numberOfCardsPerSuit) {
        return numberOfCardsPerSuit.entrySet().stream().filter(suitIntegerEntry -> suitIntegerEntry.getValue() == 1).findFirst().orElseThrow(RuntimeException::new).getKey();
    }

    private boolean threeOfOneSuit(Map<Suit, Integer> numberOfCardsPerSuit) {
        return numberOfCardsPerSuit.values().contains(3);
    }


    @Test
    public void test3SameSuit() {
        List<Card> cards = new ArrayList();
        cards.add(this.hearts1);
        cards.add(this.hearts5);
        cards.add(this.hearts7);
        cards.add(this.spades1);
        assertEquals(this.spades1, this.determineCardToDiscard(cards));
    }

    @Test
    public void testGetNumberOfCardsPerSuit() {
        List<Card> cards = new ArrayList();
        cards.add(this.hearts1);
        cards.add(this.hearts5);
        cards.add(this.hearts7);
        cards.add(this.hearts8);
        assertEquals(4, this.getNumberOfCardsPerSuit(cards).get(Suit.HEARTS).intValue());

        cards.clear();
        cards.add(this.hearts1);
        cards.add(this.hearts5);
        cards.add(this.hearts7);
        cards.add(this.spades1);
        final Map<Suit, Integer> numberOfCardsPerSuit = this.getNumberOfCardsPerSuit(cards);
        assertEquals(3, numberOfCardsPerSuit.get(Suit.HEARTS).intValue());
        assertEquals(1, numberOfCardsPerSuit.get(Suit.SPADES).intValue());
    }

    private Map<Suit, Integer> getNumberOfCardsPerSuit(List<Card> cards) {
        return cards.parallelStream().collect(Collectors.toMap(Card::getSuit, card -> 1, Integer::sum));
    }

}