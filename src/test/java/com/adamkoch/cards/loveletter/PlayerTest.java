package com.adamkoch.cards.loveletter;

import com.adamkoch.utils.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Test
    public void testKnowOpponentsCard() {
        Player player = new Player("TEST");
        player.setHand(PRIEST);
        final Player opponent = new Player("TEST2");
        player.addKnownOpponentAndHand(opponent, PRINCESS);
        assertEquals(GUARD, player.determineCardToPlay(GUARD, (GameContext) null));
        final List<Player> randomListOfPlayers = randomListOfPlayers(100);
        randomListOfPlayers.add(opponent);
        assertEquals(opponent, player.chooseOpponentToGuess(randomListOfPlayers, null));
        assertEquals(PRINCESS, player.chooseCardToGuess());
    }

    @Test
    public void testUnknownOpponentsCard() {
        Player player = new Player("TEST");
        player.setHand(PRIEST);
        final Player opponent = new Player("TEST2");
        player.addKnownOpponentAndHand(opponent, PRINCESS);
        assertEquals(GUARD, player.determineCardToPlay(GUARD, (GameContext) null));
        final List<Player> randomListOfPlayers = randomListOfPlayers(100);

        assertNotEquals(opponent, player.chooseOpponentToGuess(randomListOfPlayers, null));
        assertEquals(PRINCESS, player.chooseCardToGuess());
    }

    public static List<Player> randomListOfPlayers(int numberOfPlayers) {
        return IntStream.range(0, numberOfPlayers).mapToObj(i -> new Player(RandomUtils.randomName())).collect(Collectors.toList());
    }

}