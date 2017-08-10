package com.adamkoch.cards;

/**
 * <p>Created by aakoch on 2017-08-08.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class PlayerAction {

    private final DrawnLocation drawnLocation;
    private final ExtraAction extraAction;

    public PlayerAction(DrawnLocation drawnLocation, ExtraAction extraAction) {
        this.drawnLocation = drawnLocation;
        this.extraAction = extraAction;
    }

    enum DrawnLocation {
        DISCARD_PILE,
        DRAW_PILE
    }

    enum ExtraAction {
        KNOCKED,
        PLAYED_31,
        NONE
    }

}
