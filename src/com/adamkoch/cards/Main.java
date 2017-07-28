package com.adamkoch.cards;

//import static com.adamkoch.cards.SpecialsCards.JOKER;

public class Main {

    public static void main(String[] args) {
        Players players = new Players(PlayerFactory.initializePlayers(5));
        SevenGame game = new SevenGame(players);
        game.play();

    }

}
