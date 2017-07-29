package com.adamkoch.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-29.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class DiscardPilePlayer extends Player {
    private static final Logger LOGGER = LogManager.getLogger(DiscardPilePlayer.class);
    private List<Card> discardPile;
    private Iterator<Card> handIterator;

    public DiscardPilePlayer(String name) {
        super(name);
        discardPile = new ArrayList<>();
    }

    @Override
    protected List<Card> rank(List<? extends Card> cardsThatCanPlay, List<Card> hand) {
        return null;
    }


    public List<Card> getDiscardPile() {
        return discardPile;
    }

    public void shuffleDiscardPileIntoHand() {
        Collections.shuffle(discardPile);
        hand = new ArrayList<>(discardPile);
        discardPile.clear();
    }

    public boolean hasAnotherCard() {
        if (handIterator == null) {
            handIterator = hand.iterator();
        }
        if (!handIterator.hasNext()) {
            final int size = getDiscardPile().size();
            if (size == 0) {
                hand.clear();
                throw new RuntimeException("player " + name + " lost");
            }
            LOGGER.info("player " + name + " ran out but has " + size + " cards in his " +
                    "discard pile");
            shuffleDiscardPileIntoHand();
            handIterator = hand.iterator();
        }
        return handIterator.hasNext();
    }

    public Card getNextCard() {
        if (!hasAnotherCard()) {
            throw new RuntimeException("ran out of cards");
        }
        final Card card = handIterator.next();
        handIterator.remove();
        return card;
    }
}
