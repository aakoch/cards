Feature: Players

  Scenario: The player next to the dealer starts when no games have already been played
    Given A game with 3 players
    When Player 1 is set as the dealer
    Then Player 2 is the first to play

  Scenario: The player next to the dealer starts when no games have already been played
    Given A game with 3 players
    When Player 2 is set as the dealer
    Then Player 3 is the first to play

  Scenario: The player next to the dealer starts when no games have already been played
    Given A game with 3 players
    When Player 3 is set as the dealer
    Then Player 1 is the first to play

  Scenario: The winner of the last game starts the next round
    Given A game with 3 players
    When the last game's winner is Player 2
    Then Player 3 is the first to play

  Scenario: The winner of the last game starts the next round
    Given A game with 3 players
    When the last game's winner is Player 3
    Then Player 1 is the first to play

