package com.adamkoch.cards.loveletter;

import com.adamkoch.utils.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * <p>Created by aakoch on 2017-09-19.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Game {
    private static final Logger LOGGER = LogManager.getLogger(Game.class);

    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.shuffle();
        List<Player> players = createPlayers();
        final Card burnedCard = deck.getCards().remove(0);
        Dealer dealer = new Dealer(RandomUtils.getRandom(players), deck.getCards());
        final List<Card> drawPile = dealer.dealTo(players, 1);
        LOGGER.debug("players = " + players);
        LOGGER.debug("burnedCard = " + burnedCard);

        GameContext context = new GameContext(drawPile, players, dealer);

//        final Iterator<Player> playerIterator = ListUtils.constructRotator(players, players.indexOf(dealer.asPlayer()));

        List<Card> playedCards = new ArrayList<>();
        while (context.shouldContinue()) {
            LOGGER.trace("drawPile (" + drawPile.size() + ") = " + drawPile);
            try {
                final Player player = context.nextPlayer();
                final Card card = drawPile.remove(0);

                LOGGER.debug(player.getName() + " draws " + card);

                Card playedCard = player.determineCardToPlay(card, context);
                LOGGER.debug(player.getName() + " plays " + playedCard + " and keeps " + player.getHand());
                playedCards.add(playedCard);

                context.addResult(player, playedCard, playedCard.action(context, player, playedCards,
                        drawPile, burnedCard));
            }
            catch (NoOpponentException e) {
                LOGGER.debug("No opponent. Do nothing");
            }
        }

        final List<Player> playersLeft = context.getPlayers();

        if (playersLeft.size() > 1) {
            LOGGER.debug("playersLeft = " + playersLeft);

            playersLeft.sort(Comparator.comparingInt(a -> a.getHand().ordinal()));
        }

        LOGGER.debug("winner = " + players.get(0).getName());
    }


    private static List<Player> createPlayers() {
        final List<Player> players = new ArrayList<>();
        players.add(new Player("Adam"));
        players.add(new Player("Joel"));
        players.add(new Player("Alyssa"));
        return players;
    }
}
