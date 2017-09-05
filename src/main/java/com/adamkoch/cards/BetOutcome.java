package com.adamkoch.cards;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-09-04.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class BetOutcome {
    private final Player player;
    private final Bet bet;
    private final Card card;

    public BetOutcome(Player player, Bet bet, Card card) {
        this.player = player;
        this.bet = bet;
        this.card = card;
    }

    public BetOutcome(Player player, Bet bet) {
        this(player, bet, null);
    }

    public Player getPlayer() {
        return player;
    }

    public Card getCard() {
        return card;
    }

    public Bet getBet() {
        return bet;
    }
}
