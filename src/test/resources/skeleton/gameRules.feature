Feature: Game Rules

  Scenario: Game continues until there is only one player left
    Given A game with 2 players
    When Player 1 is removed
    Then the game ends
    And Player 2 is declared the winner
