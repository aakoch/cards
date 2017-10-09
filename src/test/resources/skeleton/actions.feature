Feature: Actions

  Scenario: The lose action removes the player who played it from the game
    Given A game with 2 players
    And Player 1 action is "LoseAction"
    When Player 1 is removed
    And Player 2 is declared the winner