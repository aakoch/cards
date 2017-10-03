package com.adamkoch.cards.loveletter;

import com.adamkoch.utils.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * <p>Created by aakoch on 2017-09-28.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class GameContextTest {

    private static final Logger LOGGER = LogManager.getLogger(GameContextTest.class);

    @Test
    public void testGetPlayResultsSinceLastTurn() throws Exception {
        List<Card> drawPile = new Deck().getCards();
        Collections.shuffle(drawPile);
        List<Player> players = PlayerTest.randomListOfPlayers(5);
        Dealer dealer = new Dealer(players.get(0), drawPile);
        GameContext gameContext = new GameContext(drawPile, players, dealer);

        LOGGER.debug("players = " + players.stream().map(Player::getName).collect(Collectors.joining(", ")));

        PlayerIterator playerIterator = new PlayerIterator(players, players.indexOf(players.get(0)));
        Iterator<Card> drawPileIterator = drawPile.iterator();
        for (int i = 0; i < 12; i++) {
            Player player = playerIterator.next();
            Card card = drawPileIterator.next();
            PlayResult result = new PlayResult.Builder().build();
            gameContext.addResult(player, card, result);
        }

        for (int i = 0; i < RandomUtils.nextInt(0, 5); i++) {

            Player player = playerIterator.next();
            Card card = RandomUtils.nextBoolean() ? Card.GUARD : drawPileIterator.next();
            PlayResult result = new PlayResult.Builder().build();
            gameContext.addResult(player, card, result);

        }
        final Player player = playerIterator.next();
        LOGGER.debug("player = " + player.getName());
        final String collectionString = gameContext.getPlayResultsSinceLastTurn(player)
                                          .stream()
                                          .map(PlayResultContainer::toString)
                                          .collect
                                                  (Collectors.joining(("\n")));
        LOGGER.debug("collectionString = " + collectionString);
        assertTrue(collectionString,
                gameContext.wasAGuardPlayedSincePlayersLastTerm(player));

    }

    @Test
    public void testgetPlayersAndGuessSinceLastTurn() {
        List<Card> drawPile = new Deck().getCards();
        Collections.shuffle(drawPile);
        List<Player> players = PlayerTest.randomListOfPlayers(5);
        Dealer dealer = new Dealer(players.get(0), drawPile);
        dealer.dealTo(players, 1);
        GameContext gameContext = new GameContext(drawPile, players, dealer);

        LOGGER.debug("players = " + players.stream().map(Player::getName).collect(Collectors.joining(", ")));

        PlayerIterator playerIterator = new PlayerIterator(players, players.indexOf(players.get(0)));
        Iterator<Card> drawPileIterator = drawPile.iterator();
        for (int i = 0; i < 12; i++) {
            Player player = playerIterator.next();
            Card card = drawPileIterator.next();
            Card playedCard = player.determineCardToPlay(card, gameContext);
            gameContext.addResult(player, card, playedCard.action(gameContext, player, null, drawPile, card));
        }

        for (int i = 0; i < RandomUtils.nextInt(0, 5); i++) {

            Player player = playerIterator.next();
            Card card = RandomUtils.nextBoolean() ? Card.GUARD : drawPileIterator.next();
            Card playedCard = player.determineCardToPlay(card, gameContext);
            gameContext.addResult(player, card, playedCard.action(gameContext, player, null, drawPile, card));

        }
        final Player player = playerIterator.next();
        LOGGER.debug("player = " + player.getName());
        final Map<Player, List<Card>> playersAndGuessSinceLastTurn = gameContext.getPlayersAndGuessSinceLastTurn(
                player);
        LOGGER.debug("playersAndGuessSinceLastTurn = " + playersAndGuessSinceLastTurn);
    }

}