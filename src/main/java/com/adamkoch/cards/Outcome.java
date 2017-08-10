package com.adamkoch.cards;

/**
 *
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Outcome {

    private PlayerAction playerAction;

    private Card discard;
    private boolean has31;
    private boolean playerKnocks;
    private boolean playerDrewFromDiscardPile;
    private Card cardTakenFromDiscardPile;
    private Player player;
    private boolean playerDrewCard;
    private Card drawnCard;

    public Outcome() {
        playerDrewFromDiscardPile = false;
    }

    public void setDiscard(Card discard) {
        this.discard = discard;
    }

    public void setHas31() {
        has31 = true;
    }

    public boolean getHas31() {
        return has31;
    }

    public void setPlayerKnocks() {
        playerKnocks = true;
    }

    public boolean playerKnocks() {
        return playerKnocks;
    }

    public boolean didPlayerDrewFromDiscardPile() {
        return playerDrewFromDiscardPile;
    }

    public Card getCardTakenFromDiscardPile() {
        return cardTakenFromDiscardPile;
    }

    public void setCardTakenFromDiscardPile(Card cardTakenFromDiscardPile) {
        playerDrewFromDiscardPile = true;
        this.cardTakenFromDiscardPile = cardTakenFromDiscardPile;
    }

    public Card getDiscardCard() {
        return discard;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setDrawnCard(Card drawnCard) {
        this.drawnCard = drawnCard;
    }

    public PlayerAction getPlayerAction() {
        return playerAction;
    }

    public void setPlayerAction(PlayerAction playerAction) {
        this.playerAction = playerAction;
    }
}
