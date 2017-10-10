Feature: Dealer

  Scenario: The dealer can shuffle cards
    Given A dealer
    And the dealer is given a deck
    And the dealer shuffles the deck
    Then the deck is random in order