package com.adamkoch.cards.loveletter;

import org.junit.Test;

import static com.adamkoch.cards.loveletter.Card.*;
import static org.junit.Assert.*;

/**
 * <p>Created by aakoch on 2017-09-20.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class PlayerTest {
    @Test
    public void testDetermineCardToPlay() throws Exception {
        assertEquals(GUARD, Player.determineCardToPlay(PRINCESS, GUARD));
        assertEquals(GUARD, Player.determineCardToPlay(GUARD, PRINCESS));
        assertEquals(COUNTESS, Player.determineCardToPlay(COUNTESS, PRINCE));
        assertEquals(COUNTESS, Player.determineCardToPlay(COUNTESS, KING));
        assertEquals(COUNTESS, Player.determineCardToPlay(PRINCE, COUNTESS));
        assertEquals(COUNTESS, Player.determineCardToPlay(KING, COUNTESS));
        assertEquals(COUNTESS, Player.determineCardToPlay(COUNTESS, PRINCESS));
        assertEquals(COUNTESS, Player.determineCardToPlay(PRINCESS, COUNTESS));

        assertEquals(GUARD, Player.determineCardToPlay(GUARD, BARON));
        assertEquals(GUARD, Player.determineCardToPlay(BARON, GUARD));


        assertEquals(GUARD, Player.determineCardToPlay(KING, GUARD));

    }

}