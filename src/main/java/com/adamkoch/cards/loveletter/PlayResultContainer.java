package com.adamkoch.cards.loveletter;

/**
 * <p>Created by aakoch on 2017-09-25.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class PlayResultContainer {
    private final Player player;
    private final Card playedCard;
    private final PlayResult playResult;

    public PlayResultContainer(Player player, Card playedCard, PlayResult playResult) {
        this.player = player;
        this.playedCard = playedCard;
        this.playResult = playResult;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getPlayedCard() {
        return playedCard;
    }

    public PlayResult getPlayResult() {
        return playResult;
    }

    @Override
    public String toString() {
        return "PlayResultContainer{" +
                "player=" + player +
                ", playedCard=" + playedCard +
                '}';
    }
}
