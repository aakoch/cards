Feature: Common Sense Rules

  Scenario: Player has a King and draws a Guard, plays the Guard
    Given A player has a King
    When the player draws a Guard
    Then the player plays the Guard

  Scenario: Player has a Guard and draws a King, plays the Guard
    Given A player has a Guard
    When the player draws a King
    Then the player plays the Guard

  Scenario: Player has a Guard and draws a Baron, plays the Guard (because Guard is lowest)
    Given A player has a Guard
    When the player draws a Baron
    Then the player plays the Guard

  Scenario Outline: Player has a Princess and draws anything, plays anything but Princess
    Given A player has a <hand>
    When the player draws a <drawnCard>
    Then the player plays the <playedCard>
    Examples:
      | hand     | drawnCard | playedCard |
      | Guard    | Princess  | Guard      |
      | Priest   | Princess  | Priest     |
      | Baron    | Princess  | Baron      |
      | Handmaid | Princess  | Handmaid   |
      | Prince   | Princess  | Prince     |
      | King     | Princess  | King       |
      | Countess | Princess  | Countess   |
      | Princess | Guard     | Guard      |
      | Princess | Priest    | Priest     |
      | Princess | Baron     | Baron      |
      | Princess | Handmaid  | Handmaid   |
      | Princess | Prince    | Prince     |
      | Princess | King      | King       |
      | Princess | Countess  | Countess   |
