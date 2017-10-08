Feature: Handmaid Rules

  Scenario: Player who played a Handmaid is removed from the list of players that can be attacked
    Given A game with 2 players
    When Player 1 is set to be safe
    Then Player 2 doesn't have any opponent

  Scenario: Player who has played a Handmaid can't be attacked
    Given 2 players each with a Guard
    When Player 1 draws a Handmaid
    And Player 1 plays a Handmaid
    And Player 2 draws a Priest
    And Player 2 plays a Priest
    Then no opponent was chosen
    And the card has no effect

  Scenario: Player who has played a Guard can be attacked
    Given 2 players each with a Guard
    When Player 1 draws a Guard
    And Player 1 plays a Guard
    And Player 2 draws a Guard
    And Player 2 plays a Guard
    Then Player 2 chooses the first player as the opponent
    And the action taken is "guess"
