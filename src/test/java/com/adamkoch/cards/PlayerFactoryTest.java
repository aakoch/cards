package com.adamkoch.cards;

import org.junit.Before;
import org.junit.Test;

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

}