package com.adamkoch.cards.loveletter;

/**
 * <p>Created by aakoch on 2017-10-20.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Outcome {
    public static final Outcome NO_EFFECT;

    static {
        NO_EFFECT = new Outcome();
        NO_EFFECT.setAction(new EmptyAction());
    }

    private Action action;
    private String description;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Outcome{" +
                "action=" + (action == null ? "null" : action.getClass().getSimpleName()) +
                ", description=" + description +
                '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
