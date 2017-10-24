Feature: Deck

  Scenario: There are only 16 cards in the deck and one is burned
    Given A game with 2 players
    When 13 cards are played
    Then next card throws an exception
    And the game ends