package com.adamkoch.cards;

import java.util.Objects;

/**
 * <p>Created by aakoch on 2017-07-13.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Card {
    private final Suit suit;
    private final Rank rank;

    public Card(Rank rank, Suit suit) {
        Objects.requireNonNull(suit, "suit");
        Objects.requireNonNull(rank, "rank");
        this.suit = suit;
        this.rank = rank;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return formatRank() + suit.toString();
    }

    public String formatRank() {
        String str;
        switch (rank) {
            case ACE:
                str = "A";
                break;
            case TEN:
                str = "T";
                break;
            case JACK:
                str = "J";
                break;
            case QUEEN:
                str = "Q";
                break;
            case KING:
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
        return suit == card.suit && rank == card.rank;
    }

    @Override
    public int hashCode() {
        int result = suit != null ? suit.hashCode() : 0;
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        return result;
    }

    public Suit getSuit() {
        return suit;
    }
}
