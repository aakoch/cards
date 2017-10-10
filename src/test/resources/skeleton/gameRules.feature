Feature: Game Rules

  Scenario: Game continues until there is only one player left
    Given A game with 2 players
    When Player 1 is removed
    Then the game ends
    And Player 2 is declared the winner

  Scenario: Game continues when 2 players are left
    Given A game with 3 players
    When Player 1 is removed
    Then the game continues

  Scenario: 3 Player game ends when 2 players are removed
    Given A game with 3 players
    When Player 1 is removed
    When Player 2 is removed
    Then the game ends
    And Player 3 is declared the winner

  Scenario: Game continues until cards run out
    Given A game with 3 players
    When a card is drawn 15 times
    Then the game continues

  Scenario: Game continues until cards run out
    Given A game with 3 players
    When a card is drawn 16 times
    Then the game ends