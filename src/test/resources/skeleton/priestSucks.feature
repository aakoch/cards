Feature: Priests Suck

  Scenario Outline: Player has a priest, generally, don't keep it
    Given A player has a <hand>
    When the player draws a <drawnCard>
    Then the player plays the <playedCard>
    Examples:
      | hand   | drawnCard | playedCard |
      | Priest | Guard     | Priest     |
      | Priest | Priest    | Priest     |
      | Priest | Baron     | Priest     |
      | Priest | Handmaid  | Priest     |
      | Priest | Prince    | Priest     |
      | Priest | King      | Priest     |
      | Priest | Countess  | Priest     |
      | Priest | Princess  | Priest     |
