package com.adamkoch.cards.loveletter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    public static List<Card> deck() {
        List<Card> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(GUARD);
        }
        for (int i = 0; i < 2; i++) {
            list.add(PRIEST);
            list.add(BARON);
            list.add(HANDMAID);
            list.add(PRINCE);
        }
        list.add(KING);
        list.add(COUNTESS);
        list.add(PRINCESS);
        return list;
    }
}
