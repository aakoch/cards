package com.adamkoch.cards;

/**
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Card {
    private static final boolean ACE_IS_HIGH = false;
    private final Suit suit;
    private final int rank;

    public Card(Suit suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int getRank() {
        if (ACE_IS_HIGH && rank == 1) {
            return 14;
        }
        return rank;
    }

    @Override
    public String toString() {
        return formatRank() + suit.toString();
    }

    public String formatRank() {
        String str;
        switch (rank) {
            case 1:
                str = "A";
                break;
            case 10:
                str = "T";
                break;
            case 11:
                str = "J";
                break;
            case 12:
                str = "Q";
                break;
            case 13:
                str = "K";
                break;
            default:
                str = String.valueOf(rank);
        }
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Card card = (Card) o;

        if (rank != card.rank) {
            return false;
        }
        return suit == card.suit;
    }

    @Override
    public int hashCode() {
        int result = suit != null ? suit.hashCode() : 0;
        result = 31 * result + rank;
        return result;
    }

    public Suit getSuit() {
        return suit;
    }
}
