package com.adamkoch.cards;

import com.adamkoch.utils.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * <p>Created by aakoch on 2017-07-30.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class ThirtyOneRound {
    private static final Logger LOGGER = LogManager.getLogger(ThirtyOneRound.class);

    private final Dealer dealer;
    private final Queue<Player> playerQueue;

    public ThirtyOneRound(Dealer dealer, Queue<Player> playerQueue) {
        this.dealer = dealer;
        this.playerQueue = playerQueue;
    }

    public List<Player> play() {
        LOGGER.info("Start round");
        final List<Player> playerList = playerQueue.stream().collect(Collectors.toList());

        final List<Card> remainingCards = dealer.dealTo(playerList, 3);
        DrawPile drawPile = new DrawPile(remainingCards);
        DiscardPile discardPile = new DiscardPile(drawPile);

        Iterator<Player> r = ListUtils.constructRotator(playerList, playerList.indexOf(dealer));

        boolean playerKnocked = false;
        boolean playerHas31 = false;
        Player player = null;
        while (!playerHas31 && r.hasNext()) {
            player = r.next();

            Outcome outcome = playTurn(player, drawPile, discardPile);

            final Card outcomeCard = outcome.getCard();
            if (outcomeCard != null) {
                discardPile.add(outcomeCard);
            }

            if (outcome.getHas31()) {
                LOGGER.debug(player + " won");
                playerHas31 = true;
            }
        }
        if (playerHas31 && player != null) {
            playerList.remove(player);
            playerList.forEach(Player::pay);
            return playerList;
        }

        return null;
    }

    private Outcome playTurn(Player player, DrawPile drawPile, DiscardPile discardPile) {
        LOGGER.debug("Start of " + player.getName() + "'s turn: " + player);

        Outcome outcome = new Outcome();
        if (player.chooseCardFromDiscardPile(drawPile, discardPile)) {
            Card discardCard = player.chooseWhichCardToDiscard(drawPile, discardPile);
            LOGGER.debug(player.getName() + " discards " + discardCard);
            discardPile.add(discardCard);
            outcome.setDiscard(discardCard);
        }
        else {
            player.drawFromDrawPile(drawPile, discardPile);

            Card discardCard = player.chooseWhichCardToDiscard(drawPile, discardPile);
            LOGGER.debug(player.getName() + " discards " + discardCard);
            discardPile.add(discardCard);
            outcome.setDiscard(discardCard);
        }

        if (player.has31()) {
            outcome.setHas31();
        }

        return outcome;
    }

}
