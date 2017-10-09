package com.adamkoch.cards.loveletter;

/**
 * <p>Created by aakoch on 2017-10-06.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public enum Card {
    GUARD(new GuessAction()),
    PRIEST(new ShowAction()),
    BARON(new CompareHandsAction()),
    HANDMAID(new EmptyAction()),
    PRINCE(new DiscardHandAction()),
    KING(new SwitchHandsAction()),
    COUNTESS(new EmptyAction()),
    PRINCESS(new LoseAction());

    private final Action action;

    Card(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
