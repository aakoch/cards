package com.adamkoch.cards.loveletter;

/**
 * <p>Created by aakoch on 2017-10-20.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public abstract class AbstractAction implements Action {

    private final Player player;
    private final Game game;

    protected AbstractAction(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public class Builder {
        private Player player;
        private Game game;

        public Builder player(Player player) {
            this.player = player;
            return this;
        }
        public Builder game(Game game) {
            this.game = game;
            return this;
        }
        public AbstractAction build() {
            return instance(player, game);
        }
    }

    protected abstract AbstractAction instance(Player player, Game game);
}
