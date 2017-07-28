package com.adamkoch.cards;

//import static com.adamkoch.cards.SpecialsCards.JOKER;

public class Main {

    public static void main(String[] args) {


//        try {
//            war();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }


        shoveIt();
    }

    private static void shoveIt() {
        int numberOfPlayers = 6;
        Stats stats = null;
        int numberOfGames = 0;
        while (numberOfPlayers > 1) {
            Game game = new ShoveIt(numberOfPlayers);
            Deck deck = DeckFactory.standard();
            stats = game.play(deck);
            numberOfGames++;
            numberOfPlayers = stats.getNumberOfPlayersLeft();
        }
        System.out.println("overall winner is " + stats.getOverallWinner().getName());
        System.out.println(numberOfGames + " games were played");
    }

    private static void war() {
        Game game = new GameOfWar();
        Deck deck = DeckFactory.standard();
        game.play(deck);
    }

}
