Feature: Actions

  Scenario: The lose action removes the player who played it from the game
    Given A game with 2 players
    When Player 1 plays a Princess
    And Player 1 action is "LoseAction"
    Then Player 1 is removed2
    And Player 2 is declared the winner

  Scenario: Lowest ranked card loses when a Baron is played
    Given A game with 2 players
    And Player 1 has a King
    And Player 2 has a Priest
    When Player 1 draws a Baron
    And Player 1 plays a Baron
    And Player 1 action is "CompareHandsAction"
    Then Player 2 is removed2