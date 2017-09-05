package com.adamkoch.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Created by aakoch on 2017-09-01.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class EuchreDeck extends Deck {

    public EuchreDeck() {
        super(createCards());
    }

    private static List<Card> createCards() {
        List<Card> cards = new ArrayList<>();
        for (Suit suit : Suit.standardSuits()) {
            cards.addAll(Stream.of(Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING, Rank.ACE)
                  .map(rank -> new Card(rank, suit)).collect(Collectors.toList()));
        }
        return cards;

//        final Stream<List<Card>> listStream = Arrays.stream(Suit.standardSuits())
//                                                    .map(suit ->
//                                                            Stream.of(Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN,
//                                                                    Rank.KING, Rank.ACE)
//                                                                  .map(rank -> new Card(rank, suit)));
    }

}
