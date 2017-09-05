package com.adamkoch.cards;

import com.adamkoch.cards.utils.CardUtil;
import javafx.geometry.InsetsBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.adamkoch.cards.Bet.PASS;
import static com.adamkoch.cards.Bet.PICK_UP_ALONE;
import static org.junit.Assert.*;

/**
 * <p>Created by aakoch on 2017-09-04.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class EuchreDeterminerTest {
    @Test
    public void test() {
        EuchreDeterminer euchreDeterminer = new EuchreDeterminer();
        List<Card> hand = CardUtil.asCardList("T♣︎", "J♣︎", "Q♣︎", "K♦︎", "A♠︎︎︎");
        Card topCard = new Card(Rank.ACE, Suit.HEARTS);
        Bet bet = euchreDeterminer.shouldOrderUpTrump(hand, topCard, true);
        assertEquals(PASS, bet);
    }

    @Test
    public void test2() {
        hand("T♣︎", "J♣︎", "Q♣︎", "T♦︎", "9♦︎").top("A♥︎").bet(PASS);
    }

    @Test
    public void testPickUpAlone() {
        hand("J♥︎", "J♦", "Q♥︎", "K♥︎︎", "T♥︎︎").top("A♥︎").bet(PICK_UP_ALONE);
    }

    @Test
    public void testScore0() {
        hand("J♥︎", "J♦", "Q♥︎", "K♥︎︎", "T♥︎︎").top("A♠︎︎︎").score(0);
        hand("J♥︎", "J♦", "A♥︎", "K♥︎︎", "T♥︎︎").top("Q♣︎").score(0);
    }

    @Test
    public void testScore100() {
        hand("J♥︎", "J♦", "Q♥︎", "K♥︎︎", "T♥︎︎").top("A♥︎").score(100);
        hand("J♥︎", "J♦", "A♥︎", "K♥︎︎", "T♥︎︎").top("Q♥︎").score(100);
        hand("J♥︎", "J♦", "A♥︎", "Q♥︎︎", "T♥︎︎").top("K♥︎").score(100);
    }

    @Test
    public void testScoreNot100() {
        hand("J♥︎", "J♦", "Q♥︎", "K♥︎︎", "T♥︎︎").top("A♦").scoreNot(100);
    }

    @Test
    public void testHasRightBower() {
        assertTrue(EuchreDeterminer.hasRightBower(Suit.HEARTS, CardUtil.asCardList("J♥︎")));
        assertTrue(EuchreDeterminer.hasRightBower(Suit.DIAMONDS, CardUtil.asCardList("J♦︎")));
        assertFalse(EuchreDeterminer.hasRightBower(Suit.DIAMONDS, CardUtil.asCardList("J♥")));
    }

    @Test
    public void testHasLeftBower() {
        assertTrue(EuchreDeterminer.hasLeftBower(Suit.DIAMONDS, CardUtil.asCardList("J♥︎")));
        assertTrue(EuchreDeterminer.hasLeftBower(Suit.HEARTS, CardUtil.asCardList("J♦︎")));
        assertFalse(EuchreDeterminer.hasLeftBower(Suit.HEARTS, CardUtil.asCardList("J♥")));
    }

    private Builder hand(String... s) {
        Builder builder = new Builder();
        builder.setHand(CardUtil.asCardList(s));
        return builder;
    }

    private class Builder {
        private List<Card> cards;
        private Card topCard;

        public Builder top(String str) {
            topCard = new Card(Rank.valueOf(str.charAt(0)), Suit.valueOf(str.charAt(1)));
            return this;
        }

        public void bet(Bet expectedBet) {
            EuchreDeterminer euchreDeterminer = new EuchreDeterminer();
            Bet actualBet = euchreDeterminer.shouldOrderUpTrump(cards, topCard, true);
            assertEquals(expectedBet, actualBet);
        }

        public Builder setHand(List<Card> cards) {
            this.cards = cards;
            return this;
        }

        public void score(int expectedScore) {
            EuchreDeterminer euchreDeterminer = new EuchreDeterminer();
            int actualScore = euchreDeterminer.score(cards, topCard, true);
            assertEquals(expectedScore, actualScore);
        }

        public void scoreNot(int expectedScore) {
            EuchreDeterminer euchreDeterminer = new EuchreDeterminer();
            int actualScore = euchreDeterminer.score(cards, topCard, true);
            assertNotEquals(expectedScore, actualScore);
        }
    }
}