package com.adamkoch.cards.loveletter;

import com.adamkoch.utils.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.adamkoch.cards.loveletter.Card.*;

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

        PlayerIterator playerIterator = new PlayerIterator(players, players.indexOf(dealer.asPlayer()));
//        final Iterator<Player> playerIterator = ListUtils.constructRotator(players, players.indexOf(dealer.asPlayer()));

        List<Card> playedCards = new ArrayList<>();
        while (playerIterator.size() > 1 && !drawPile.isEmpty()) {
            LOGGER.trace("drawPile = " + drawPile);
            try {
                final Player player = playerIterator.next();
                final Card card = drawPile.remove(0);

                LOGGER.debug(player.getName() + " draws " + card);

                Card playedCard = player.determineCardToPlay(card);
                LOGGER.debug(player.getName() + " plays " + playedCard + " and keeps " + player.getHand());
                playedCards.add(playedCard);

                if (playedCard == GUARD) {
                    Player opponent = player.chooseOpponentToGuess(players);
                    List<Card> cardsNotPlayed = new ArrayList<>(deck.getCards());
                    cardsNotPlayed.removeAll(playedCards);
                    Card rank = RandomUtils.getRandom(cardsNotPlayed);
                    LOGGER.debug(player.getName() + " guesses " + opponent.getName() + " has a " + rank);
                    if (opponent.getHand() == rank) {
                        LOGGER.debug(player + " guesses right and " + opponent.getName() + " is out");
                        playerIterator.remove(opponent);
                    }
                    else {
                        LOGGER.debug(player.getName() + " guesses wrong");
                    }
                }
                else if (playedCard == Card.PRIEST) {
                    Player opponent = player.chooseOpponentToReveal(players);
                    LOGGER.debug(opponent.getName() + " shows their " + opponent.getHand() + " to " +
                            player.getName());
                }
                else if (playedCard == Card.BARON) {
                    Player opponent = player.chooseOpponentToCompare(players);
                    LOGGER.debug(player.getName() + " compares their " + player.getHand() + " to " +
                            opponent.getName() + "'s " + opponent.getHand());
                    if (player.getHand().ordinal() < opponent.getHand().ordinal()) {
                        LOGGER.debug(player.getName() + " loses");
                        playerIterator.remove(player);
                    }
                    if (player.getHand().ordinal() > opponent.getHand().ordinal()) {
                        LOGGER.debug(opponent.getName() + " loses");
                        playerIterator.remove(opponent);
                    }
                }
                else if (playedCard == Card.KING) {
                    Player opponent = player.chooseOpponentToTrade(players);
                    LOGGER.debug(player.getName() + " trades their " + player.getHand() + " with " +
                            opponent.getName() + "'s " + opponent.getHand());
                    Card card1 = player.getHand();
                    Card card2 = opponent.getHand();
                    player.setHand(card2);
                    opponent.setHand(card1);
                }

                else if (playedCard == PRINCE) {

                    Player opponent = player.choosePlayerToDiscardTheirCard(players);
                    LOGGER.debug(player.getName() + " chooses " + opponent.getName() + " to discard their card");

                    Card drawnCard;
                    if (drawPile.size() > 0) {
                        drawnCard = drawPile.remove(0);
                    }
                    else {
                        drawnCard = burnedCard;
                    }
                    LOGGER.debug(opponent.getName() + " discards their " + opponent.getHand() + " and " +
                            "draws a " + drawnCard);
                    opponent.setHand(drawnCard);
                }
            }
            catch (NoOpponentException e) {
                LOGGER.debug("No opponent. Do nothing");
            }
        }

        Collections.sort(players, (a, b) -> {
            return b.getHand().ordinal() - a.getHand().ordinal();
        });

        LOGGER.debug("winner = " + players.get(0));
    }

    private static List<Player> createPlayers() {
        final List<Player> players = new ArrayList<>();
        players.add(new Player("Adam"));
        players.add(new Player("Joel"));
        players.add(new Player("Alyssa"));
        return players;
    }
}
