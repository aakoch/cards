Feature: Dealer

  Scenario: The dealer can shuffle cards
    Given A dealer
    And the dealer is given a deck
    And the dealer shuffles the deck
    Then the deck is random in order

  Scenario: The dealer deals one card to each player
    Given A dealer
    And the dealer is given a deck
    And the dealer deals to 5 players
    Then each of the 5 players have a card
