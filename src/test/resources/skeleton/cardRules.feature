Feature: Card Rules

  Scenario: Player has a King and draws a Countess, must play the Countess
    Given A player has a King
    When the player draws a Countess
    Then the player plays the Countess

  Scenario: Player has a Prince and draws a Countess, must play the Countess
    Given A player has a Prince
    When the player draws a Countess
    Then the player plays the Countess

  Scenario: Player has a Countess and draws a King, must play the Countess
    Given A player has a Countess
    When the player draws a King
    Then the player plays the Countess

  Scenario: Player has a Countess and draws a Prince, must play the Countess
    Given A player has a Countess
    When the player draws a Prince
    Then the player plays the Countess

  Scenario Outline: Countess rules
    Given A player has a <hand>
    When the player draws a <drawnCard>
    Then the player plays the <playedCard>
    Examples:
      | hand     | drawnCard | playedCard |
      | Prince   | Countess  | Countess   |
      | King     | Countess  | Countess   |
      | Countess | Prince    | Countess   |
      | Countess | King      | Countess   |