Feature: Dealer

  Scenario: There are only 16 cards in the deck
    Given A game with 2 players
    When 16 cards are played
    Then next card throws an exception

  Scenario: There are only 16 cards in the deck
    Given A game with 2 players
    When 16 cards are played
    Then next card throws an exception
    And the game ends