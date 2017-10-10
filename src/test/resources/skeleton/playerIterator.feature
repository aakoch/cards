Feature: Player Iterator

  Scenario: The playerIterator never ends
    Given A game with 2 players
    When Player 2 is set as the dealer
    And next player
    And next player
    Then the next player is Player 1

