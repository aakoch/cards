package com.adamkoch.cards;

//import static com.adamkoch.cards.SpecialsCards.JOKER;

public class Main {

    public static void main(String[] args) {


        try {
            war();
        } catch (Exception e) {
            e.printStackTrace();
        }


        shoveIt();
    }

    private static void shoveIt() {
        Game game = new ShoveIt(3);
        Deck deck = DeckFactory.standard();
        game.play(deck);
    }

    private static void war() {
        Game game = new GameOfWar();
        Deck deck = DeckFactory.standard();
        game.play(deck);
    }

}
