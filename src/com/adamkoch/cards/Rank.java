package com.adamkoch.cards;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-28.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public enum Rank {
    ACE("A"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("T"),
    JACK("J"), QUEEN("Q"), KING("K");

    private final String abbreviation;

    Rank(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public int value() {
        return ordinal() + 1;
    }
}
