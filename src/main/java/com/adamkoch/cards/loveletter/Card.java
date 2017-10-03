package com.adamkoch.cards.loveletter;

import com.adamkoch.utils.RandomUtils;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <p>Created by aakoch on 2017-09-20.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public enum Card {
    GUARD {
        private GuardGuessResults previousGuesses = new GuardGuessResults();

        @Override
        public PlayResult action(GameContext gameContext, Player player, List<Card> playedCards, List<Card> drawPile, Card burnedCard) {
            Player opponent;
            Card rank;
            if (player.knowsOpponentsHand()) {
                opponent = player.chooseOpponentToGuess(gameContext.getPlayers(), gameContext);
                rank = player.chooseCardToGuess();
                LOGGER.debug(player.getName() + " guesses " + opponent.getName() + " has a " + rank);
                if (opponent.getHand() == rank) {
                    LOGGER.debug(player + " guesses right and " + opponent.getName() + " is out");
                    gameContext.removePlayer(opponent);
                }
                else {
                    LOGGER.debug(player.getName() + " guesses wrong");
                }
            }
            else if (player.getLastOpponentToShowACard() == null) {
                List<Card> cardsNotPlayed = new ArrayList<>(drawPile);
                cardsNotPlayed.removeAll(playedCards);
                GuardGuessResult guardGuessResult = new GuardGuess(gameContext.getPlayers(), player, cardsNotPlayed,
                        previousGuesses).invoke(gameContext);
                final Player guessResultOpponent = guardGuessResult.getOpponent();
                LOGGER.debug(player.getName() + " guesses " + guessResultOpponent.getName() + " has a " +
                        guardGuessResult.getCard());
                if (guessResultOpponent.getHand() == guardGuessResult.getCard()) {
                    LOGGER.debug(player + " guesses right and " + guessResultOpponent.getName() + " is out");
                    gameContext.removePlayer(guessResultOpponent);
                }
                else {
                    LOGGER.debug(player.getName() + " guesses wrong");
                }
                previousGuesses.add(guardGuessResult);
            }
            else {
                opponent = player.getLastOpponentToShowACard();
                rank = player.getLastCardShown();
                LOGGER.debug(player.getName() + " guesses " + opponent.getName() + " has a " + rank);
                if (opponent.getHand() == rank) {
                    LOGGER.debug(player.getName() + " guesses right and " + opponent.getName() + " is out");
                    gameContext.removePlayer(opponent);
                }
                else {
                    LOGGER.debug(player.getName() + " guesses wrong");
                }
            }
            return new PlayResult.Builder().build();
        }
    },
    PRIEST {
        @Override
        public PlayResult action(GameContext gameContext, Player player, List<Card> playedCards, List<Card> drawPile, Card burnedCard) {

            Player opponent = player.chooseOpponentToReveal(gameContext.getPlayers());
            LOGGER.debug(opponent.getName() + " shows their " + opponent.getHand() + " to " +
                    player.getName());
            player.shownCard(opponent, opponent.getHand());
            return new PlayResult.Builder().build();
        }
    },
    BARON {
        @Override
        public PlayResult action(GameContext gameContext, Player player, List<Card> playedCards, List<Card> drawPile, Card burnedCard) {

            Player opponent = player.chooseOpponentToCompare(gameContext.getPlayers());
            LOGGER.debug(player.getName() + " compares their " + player.getHand() + " to " +
                    opponent.getName() + "'s " + opponent.getHand());
            if (player.getHand().ordinal() < opponent.getHand().ordinal()) {
                LOGGER.debug(player.getName() + " loses");
                gameContext.removePlayer(player);
            }
            if (player.getHand().ordinal() > opponent.getHand().ordinal()) {
                LOGGER.debug(opponent.getName() + " loses");
                gameContext.removePlayer(opponent);
            }
            return new PlayResult.Builder().build();
        }
    },
    HANDMAID {
        @Override
        public PlayResult action(GameContext gameContext, Player player, List<Card> playedCards, List<Card> drawPile, Card burnedCard) {

            return new PlayResult.Builder().build();
        }
    },
    PRINCE {
        @Override
        public PlayResult action(GameContext gameContext, Player player, List<Card> playedCards, List<Card> drawPile, Card burnedCard) {

            Player opponent = player.choosePlayerToDiscardTheirCard(gameContext.getPlayers());
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
            return new PlayResult.Builder().build();
        }
    },
    KING {
        @Override
        public PlayResult action(GameContext gameContext, Player player, List<Card> playedCards, List<Card> drawPile, Card burnedCard) {

            Player opponent = player.chooseOpponentToTrade(gameContext.getPlayers());
            LOGGER.debug(player.getName() + " trades their " + player.getHand() + " with " +
                    opponent.getName() + "'s " + opponent.getHand());
            Card card1 = player.getHand();
            Card card2 = opponent.getHand();
            player.setHand(card2);
            opponent.setHand(card1);
            return new PlayResult.Builder().build();
        }
    },
    COUNTESS {
        @Override
        public PlayResult action(GameContext gameContext, Player player, List<Card> playedCards, List<Card> drawPile, Card burnedCard) {

            return new PlayResult.Builder().build();
        }
    },
    PRINCESS {
        @Override
        public PlayResult action(GameContext gameContext, Player player, List<Card> playedCards, List<Card> drawPile, Card burnedCard) {
            gameContext.removePlayer(player);
            return new PlayResult.Builder().build();
        }
    };

    private static final Logger LOGGER = LogManager.getLogger(Card.class);

    public static Set<Card> GUESSABLE = Sets.immutableEnumSet(
            PRIEST,
            BARON,
            HANDMAID,
            PRINCE,
            KING,
            COUNTESS,
            PRINCESS);

    public abstract PlayResult action(GameContext gameContext, Player player, List<Card> playedCards,
                                      List<Card> drawPile, Card burnedCard);

    private static class GuardGuessResult {
        private Player opponent;
        private final Card card;

        public GuardGuessResult(Player opponent, Card card) {

            this.opponent = opponent;
            this.card = card;
        }

        public Player getOpponent() {
            return opponent;
        }

        public Card getCard() {
            return card;
        }

    }

    private static class GuardGuessResults {
        private List<GuardGuessResult> list;

        public GuardGuessResults() {
            list = new ArrayList<>();
        }

        public void add(GuardGuessResult guardGuessResult) {
            list.add(guardGuessResult);
        }

        public Optional<GuardGuessResult> last() {
            if (list.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(list.get(list.size() - 1));
        }
    }

    private static class GuardGuess {
        private final GuardGuessResults previousGuesses;
        private List<Player> players;
        private Player player;
        private List<Card> cardsNotPlayed;

        public GuardGuess(List<Player> players, Player player, List<Card> cardsNotPlayed, GuardGuessResults previousGuesses) {
            this.players = players;
            this.player = player;
            this.cardsNotPlayed = cardsNotPlayed;
            this.previousGuesses = previousGuesses;
        }

        public GuardGuessResult invoke(GameContext gameContext) {
            Player opponent;
            if (previousGuesses.last().isPresent()) {
                GuardGuessResult guardGuessResult = previousGuesses.last().get();
                opponent = guardGuessResult.getOpponent();
                cardsNotPlayed.remove(guardGuessResult.getCard());
            }
            else {
                opponent = player.chooseOpponentToGuess(players, gameContext);
            }
            Card rank;
            if (cardsNotPlayed.isEmpty()) {
                rank = PRINCESS;
            }
            else {
                rank = RandomUtils.getRandom(cardsNotPlayed);
            }
            return new GuardGuessResult(opponent, rank);
        }
    }
}
