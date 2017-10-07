Feature: Turn Steps

  Scenario: A Player starts their turn by drawing a card
    Given A player
    When the player turn starts
    Then the player draws a King

  Scenario: A Player can't play a card until one is drawn
    Given A player
    When the player turn starts
    And the player plays a card
    Then an exception is thrown

  Scenario: A Player can't choose an opponent until a card is drawn
    Given A player
    When the player turn starts
    And the player chooses an opponent
    Then an exception is thrown

  Scenario: A Player can't choose an opponent until a card is played
    Given A player
    When the player turn starts
    And the player draws a King
    And the player chooses an opponent
    Then an exception is thrown

  Scenario: A Player can't draw more than one card
    Given A player
    When the player turn starts
    And the player draws a King
    And the player draws a Prince
    Then an exception is thrown

  Scenario: A Player can't do the action until an opponent is chosen
    Given A player has a Guard
    When the player turn starts
    And the player draws a King
    And the player plays a card
    And the player performs action
    Then an exception is thrown

  Scenario: A Player does all the correct steps
    Given A player has a Guard
    When the player turn starts
    And the player draws a King
    And the player plays a card
    And the player chooses an opponent
    And the player performs action
    Then an exception is not thrown
