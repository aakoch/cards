package com.adamkoch.cards.loveletter;

/**
 * <p>Created by aakoch on 2017-09-25.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class KnownHand {
    private final Player player;
    private final Card card;

    public KnownHand(Player player, Card card) {
        this.player = player;
        this.card = card;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getCard() {
        return card;
    }
}
