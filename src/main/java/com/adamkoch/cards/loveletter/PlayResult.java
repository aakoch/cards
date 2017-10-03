package com.adamkoch.cards.loveletter;

/**
 * <p>Created by aakoch on 2017-09-25.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public interface PlayResult {
    Player getTargetedPlayer();
    Card getGuessedCard();

    class Builder {
        public PlayResult build() {
            PlayResult playResult = new DefaultPlayResult();
            return playResult;
        }

        public PlayResult guess(Player opponent, Card guessedCard) {

            return new DefaultPlayResult(opponent, guessedCard);
        }

        private class DefaultPlayResult implements PlayResult {
            private Player opponent;
            private Card guessedCard;

            public DefaultPlayResult(Player opponent, Card guessedCard) {
                this.opponent = opponent;
                this.guessedCard = guessedCard;
            }

            public DefaultPlayResult() {

            }

            @Override
            public Player getTargetedPlayer() {
                return opponent;
            }

            @Override
            public Card getGuessedCard() {
                return guessedCard;
            }
        }
    }
}
