Feature: Card Action Rules

  Scenario Outline: Card action rules
    Given A "<card>" is played
    Then the action is "<action>"
    Examples:
      | card     | action             |
      | Guard    | GuessAction        |
      | Priest   | ShowAction         |
      | Baron    | CompareHandsAction |
      | Handmaid | EmptyAction        |
      | Prince   | DiscardHandAction  |
      | King     | SwitchHandsAction  |
      | Countess | EmptyAction        |
      | Princess | LoseAction         |