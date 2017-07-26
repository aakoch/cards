package com.adamkoch.cards;

//import static com.adamkoch.cards.SpecialsCards.JOKER;

public class Main {

    public static void main(String[] args) {
        Game war = new GameOfWar();
	    Deck deck = newDeckWithoutJokers();
        war.play(deck);
    }

    private static Deck newDeckWithoutJokers() {
        return removeJokers(new StandardDeck());
    }

    private static Deck removeJokers(StandardDeck standardDeck) {
//        standardDeck.remove(JOKER);
//        standardDeck.remove(JOKER);
        return standardDeck;
    }
}
