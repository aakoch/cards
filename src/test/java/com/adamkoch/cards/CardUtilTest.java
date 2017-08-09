package com.adamkoch.cards;

import com.adamkoch.cards.utils.CardUtil;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.adamkoch.cards.utils.CardUtil.getNumberOfCardsPerSuit;
import static org.junit.Assert.*;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-08-06.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class CardUtilTest {
    @Test
    public void testFindMajoritySuit() throws Exception {
        List<Card> cards = CardUtil.asCardList("4♣︎", "7♣︎", "8♣︎", "4♦︎");
        assertEquals(Suit.CLUBS, CardUtil.findMajoritySuit(cards).get());
    }

    @Test
    public void testFindMinoritySuit() throws Exception {
        List<Card> cards = CardUtil.asCardList("4♣︎", "7♣︎", "8♣︎", "4♦︎");
        assertEquals(Suit.DIAMONDS, CardUtil.findMinoritySuit(cards).get());
    }

    @Test
    public void testName() throws Exception {
        List<Card> cards = CardUtil.asCardList("K♥",
                "3♣",
                "Q♥",
                "4♦",
                "7♦",
                "3♦",
                "A♦",
                "8♣",
                "4♣",
                "5♦",
                "9♣",
                "9♥",
                "4♠",
                "5♣",
                "J♥",
                "7♣",
                "8♦",
                "3♠",
                "9♦",
                "6♥",
                "6♠",
                "T♥",
                "A♣",
                "K♦",
                "Q♦",
                "5♠",
                "2♣",
                "6♣",
                "5♥",
                "Q♣",
                "3♥",
                "7♥",
                "6♦",
                "K♣",
                "2♦",
                "4♥",
                "8♥",
                "T♣",
                "J♦",
                "2♥",
                "T♦",
                "2♠",
                "J♣",
                "A♥");


        final Map<Suit, Integer> numberOfCardsPerSuit = getNumberOfCardsPerSuit(cards);

        assertEquals(Suit.SPADES, CardUtil.findMinoritySuit(cards).get());
        assertEquals(Suit.DIAMONDS, CardUtil.findMajoritySuit(cards).get());

    }
}