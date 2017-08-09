package com.adamkoch.cards;

import com.adamkoch.cards.utils.CardUtil;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * <p>Created by aakoch on 2017-08-06.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class KnockHelperTest {

    @Test
    public void testFindLowestPlayers() throws Exception {
        Player player = makePlayerWithHand("5♦", "9♦", "2♦");
        List<Player> players = new ArrayList<>();
        players.add(player);
        final Player player1 = makePlayerWithHand("4♥", "Q♥", "K♣");
        players.add(player1);
        players.add(makePlayerWithHand("3♠", "Q♣", "4♣"));
        players.add(makePlayerWithHand("3♣", "T♦", "4♦"));

        final List<Player> playersWithScore = KnockHelper.findLowestPlayers(player, players);
        assertThat(playersWithScore, CoreMatchers.hasItem(player1));
    }

    @Test
    public void testFindLowestTotal() throws Exception {
        Player player = makePlayerWithHand("5♦", "9♦", "2♦");
        List<Player> players = new ArrayList<>();
        players.add(player);
        players.add(makePlayerWithHand("3♥", "Q♥", "K♥"));
        players.add(makePlayerWithHand("3♠", "Q♣", "4♣"));
        players.add(makePlayerWithHand("3♣", "T♦", "4♦"));

        final int lowestTotal = KnockHelper.findLowestTotal(player, players);
        assertEquals(14, lowestTotal);
    }

    @Test
    public void testFindPlayersWithScore() throws Exception {
        Player player = makePlayerWithHand("5♦", "9♦", "2♦");
        List<Player> players = new ArrayList<>();
        players.add(player);
        final Player player1 = makePlayerWithHand("4♥", "Q♥", "K♣");
        players.add(player1);
        players.add(makePlayerWithHand("3♠", "Q♣", "4♣"));
        players.add(makePlayerWithHand("3♣", "T♦", "4♦"));

        final List<Player> playersWithScore = KnockHelper.findPlayersWithScore(player, players, 14);
        assertThat(playersWithScore, CoreMatchers.hasItem(player1));
    }

    public Player makePlayerWithHand(String... cards) {
        Player player = new EasyPlayer("test", 30);
        player.getHand().addAll(CardUtil.asCardList(cards));
        return player;
    }

}