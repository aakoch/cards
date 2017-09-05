package com.adamkoch.cards;

import com.adamkoch.utils.ListUtils;
import com.adamkoch.utils.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-09-03.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class EuchreGame implements Game {
    private static final Logger LOGGER = LogManager.getLogger(EuchreGame.class);
    private final List<Player> players;

    public EuchreGame(List<Player> players) {
        assert players.size() == 4;
        this.players = players;
    }

    private Optional<BetOutcome> determineTrump(int i, List<Player> players, Card topCard) {
        if (i != players.size() - 1) {
            Collections.rotate(players, -(i + 1));
        }
        final Iterator<Player> playerIterator = players.iterator();
        while (playerIterator.hasNext()) {
            final Player player = playerIterator.next();
            final Bet bet = shouldPickUp(player, topCard);
            LOGGER.debug("{} decides {}", player.getName(), bet);
            if (bet != Bet.PASS) {
                return Optional.of(new BetOutcome(player, bet, topCard));
            }
        }
        return Optional.empty();
    }

    private Optional<BetOutcome> determineTrump(int i, List<Player> players) {
        if (i != players.size() - 1) {
            Collections.rotate(players, -(i + 1));
        }
        final Iterator<Player> playerIterator = players.iterator();
        while (playerIterator.hasNext()) {
            final Player player = playerIterator.next();
            final Bet bet = shouldPickUp(player);
            LOGGER.debug("{} decides {}", player.getName(), bet);
            if (bet != Bet.PASS) {
                Card card = RandomUtils.getRandom(player.getHand());
                return Optional.of(new BetOutcome(player, bet, card));
            }
        }
        return Optional.empty();
    }

    private Bet shouldPickUp(Player player) {
        boolean b = RandomUtils.nextBoolean() && RandomUtils.nextBoolean();
        if (b) {
            return Bet.PICK_UP;
        }
        return Bet.PASS;
    }

    private Bet shouldPickUp(Player player, Card topCard) {
        return player.determineTrump(topCard);
    }

    private Dealer findFirstDealer(List<Player> players, Deck deck) {
        final Iterator<Player> playerIterator = ListUtils.constructRotator(players, 0);
        Player player = null;
        final Iterator<Card> cardIterator = deck.cards().iterator();
        while (player == null && cardIterator.hasNext() && playerIterator.hasNext()) {
            Card card = cardIterator.next();
            Player possibleDealer = playerIterator.next();
            if (isBlackJack(card)) {
                player = possibleDealer;
            }
        }
        return new Dealer(player, deck);
    }

    private boolean isBlackJack(Card card) {
        return card.getRank() == Rank.JACK && (card.getSuit() == Suit.CLUBS || card.getSuit() == Suit.SPADES);
    }

    @Override
    public GameResult play() {
        Deck deck = new EuchreDeck();

        deck.shuffle();
        Dealer dealer = findFirstDealer(players, deck);
        LOGGER.debug("dealer = " + dealer);

        final List<Card> leftOverCards = dealer.dealTo(players, 5);
        LOGGER.debug("leftOverCards = " + leftOverCards);
        final Card possibleTrump = leftOverCards.remove(0);
        LOGGER.info("Top card " + possibleTrump);

        players.stream().forEach(LOGGER::debug);

        Optional<BetOutcome> bet = determineTrump(players.indexOf(dealer.asPlayer()), players, possibleTrump);
        if (bet.isPresent()) {
            LOGGER.debug("{} told the dealer {} to pick up the {}", bet.get().getPlayer().getName(),
                    dealer.asPlayer().getName()
                    , possibleTrump);
        }
        else {
            bet = determineTrump(players.indexOf(dealer.asPlayer()), players);
            if (bet.isPresent()) {
                LOGGER.debug("{} set trump to {}", bet.get().getPlayer().getName(), bet.get().getCard().getSuit());
            }
            else {
                LOGGER.info("No one bid");
            }
        }

        final GameResult gameResult = new GameResult();
        gameResult.setWinner(RandomUtils.getRandom(players));
        return gameResult;
    }
}
