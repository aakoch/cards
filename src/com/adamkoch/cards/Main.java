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

        for (int i = 0; i < 100; i++) {

        shoveIt();
        }
    }

    private static void shoveIt() {
        System.out.println("-----+++++====== Start ======+++++-----");
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
        final ShoveItPlayer overallWinner = stats.getOverallWinner();
        if (overallWinner == null) {
            System.out.println("It's a tie");
        }
        else
        System.out.println("overall winner is " + overallWinner.getName());
        System.out.println(numberOfGames + " games were played");
    }

    private static void war() {
        Game game = new GameOfWar();
        Deck deck = DeckFactory.standard();
        game.play(deck);
    }

}
