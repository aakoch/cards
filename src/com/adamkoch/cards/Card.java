package com.adamkoch.cards;

/**
 * <a href=""></a>
 *
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Card {
    private final Suit suit;
    private final int rank;

    public Card(Suit suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int getRank() {
        if (rank == 1) {
            return 14;
        }
        return rank;
    }

    @Override
    public String toString() {
        return formatRank() + suit;
    }

    private String formatRank() {
        if (rank == 1) {
            return " A";
        }
        else if (rank > 10) {
            if (rank == 11) {
                return " J";
            }
            else if (rank == 12) {
                return " Q";
            }
            else {
                return " K";
            }
        }
        else {
            return String.format("%2d", rank);
        }
    }
}
