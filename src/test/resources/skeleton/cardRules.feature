Feature: Card Rules

  Scenario: Player has a King and draws a Countess, must play the Countess
    Given A player has a King
    When the player draws a Countess
    Then the player plays the King

  Scenario: Player has a Prince and draws a Countess, must play the Countess
    Given A player has a Prince
    When the player draws a Countess
    Then the player plays the Prince

  Scenario: Player has a Countess and draws a King, must play the Countess
    Given A player has a Countess
    When the player draws a King
    Then the player plays the King

  Scenario: Player has a Countess and draws a Prince, must play the Countess
    Given A player has a Countess
    When the player draws a Prince
    Then the player plays the Prince
