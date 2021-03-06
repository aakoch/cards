package com.adamkoch.cards;

import com.google.common.base.Throwables;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;

/**
 *
 * <p>Created by aakoch on 2017-07-28.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public enum Rank {

    ACE(1, "A"), TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"), SIX(6, "6"), SEVEN(7, "7"), EIGHT(8, "8"),
    NINE(9, "9"), TEN(10, "T"), JACK(11, "J"), QUEEN(12, "Q"), KING(13, "K"), JOKER(0, "\ud83c\udcdf");

    private final Cache<Integer, Rank> cache = CacheBuilder.newBuilder().build();

    private final int innerRank;
    private final String abbreviation;

    Rank(int innerRank, String abbreviation) {
        this.innerRank = innerRank;
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public int value() {
        return ordinal() + 1;
    }

    public static Rank[] standardRanks() {
        return new Rank[] {ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING};
    }

    public boolean lessThan(Rank rank) {
        return innerRank < rank.innerRank;
    }

    public Rank offsetRank(int offset) {

        try {
            return cache.get(offset, () -> {
                int thisRank = this.innerRank;
                for (Rank rank : values()) {
                    if (thisRank + offset == rank.innerRank) {
                        return rank;
                    }
                }

                throw new RuntimeException("Could not find a rank that is " + offset + " away from " + this);
            });
        }
        catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean greaterThan(Rank rank) {
        return innerRank > rank.innerRank;
    }

    @Override
    public String toString() {
        return abbreviation;
    }

    public int getNumericRank(boolean aceIsHigh) {
        if (aceIsHigh && this == ACE) {
            return 14;
        }
        return innerRank;
    }

    public int getValue(boolean aceIsEleven) {
        return convert(this, aceIsEleven);
    }

    private static int convert(Rank rank, boolean aceIsEleven) {
        int value = 0;
        if (rank == ACE) {
            if (aceIsEleven)
                value = 11;
            else
                value = 1;
        }
        else if (rank == JACK || rank == QUEEN || rank == KING) {
            value = 10;
        }
        else {
            value = rank.getNumericRank(true);
        }
        return value;
    }

    public static Rank valueOf(char c) {
        Rank rank;
        switch (c) {
            case 'A':
            case '1':
                rank = ACE;
                break;
            case '2':
                rank = TWO;
                break;
            case '3':
                rank = THREE;
                break;
            case '4':
                rank = FOUR;
                break;
            case '5':
                rank = FIVE;
                break;
            case '6':
                rank = SIX;
                break;
            case '7':
                rank = SEVEN;
                break;
            case '8':
                rank = EIGHT;
                break;
            case '9':
                rank = NINE;
                break;
            case 'T':
                rank = TEN;
                break;
            case 'J':
                rank = JACK;
                break;
            case 'Q':
                rank = QUEEN;
                break;
            case 'K':
                rank = KING;
                break;
            default:
                rank = null;
        }
        return rank;
    }
}
