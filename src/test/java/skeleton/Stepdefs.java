package skeleton;

import com.adamkoch.cards.loveletter.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class Stepdefs extends AbstractStepDefinitions {

    private Exception thrownException;
    Game game;

    @Given("^A player has a ([^\"]*)$")
    public void a_player_has_a(String cardName) throws Exception {
        game = GameCreater.withNPlayers(2, Card.valueOf(cardName.toUpperCase()));
    }

    @Given("^Player (\\d+) has a ([^\"]*)$")
    public void player_has_a(int playerNumber, String cardName) throws Exception {
        game.getPlayer(playerNumber - 1).setHand(Card.valueOf(cardName.toUpperCase()));
    }

    @When("^the player draws a ([^\"]*)$")
    public void the_player_draws_a(String cardName) throws Exception {
        try {
            game.getPlayer(0).addToHand(Card.valueOf(cardName.toUpperCase()));
        }
        catch (RuntimeException e) {
            if (thrownException != null) {
                org.junit.Assert.fail("Exception already thrown! " + thrownException.getMessage());
                thrownException.printStackTrace();
            }
            thrownException = e;
        }
    }

    @Then("^the player plays the ([^\"]*)$")
    public void the_player_plays_the(String cardName) throws Exception {
        assertThat(game.getPlayer(0).determineCardToPlay(), is(Card.valueOf(cardName.toUpperCase())));
    }

    @Given("^A player$")
    public void a_player() throws Exception {
        thrownException = null;
        game = GameCreater.withNPlayers(1);
    }

    @When("^the player turn starts$")
    public void the_player_turn_starts() throws Exception {
        // start turn
        game.getPlayer(0).startTurn();
        thrownException = null;
    }

    @When("^the player plays a card$")
    public void the_player_plays_a_card() throws Exception {
        try {
            game.getPlayer(0).determineCardToPlay();
        }
        catch (RuntimeException e) {
            if (thrownException != null) {
                org.junit.Assert.fail("Exception already thrown! " + thrownException.getMessage());
                thrownException.printStackTrace();
            }
            thrownException = e;
        }
    }

    @Then("^an exception is thrown$")
    public void an_exception_is_thrown() throws Exception {
        assertThat(thrownException, notNullValue());
    }

    @When("^the player chooses an opponent$")
    public void the_player_chooses_an_opponent() throws Exception {
        try {
            game.getPlayer(0).chooseOpponent();
        }
        catch (RuntimeException e) {
            if (thrownException != null) {
                org.junit.Assert.fail("Exception already thrown! " + thrownException.getMessage());
                thrownException.printStackTrace();
            }
            thrownException = e;
        }
    }

    @When("^the player performs action$")
    public void the_player_performs_action() throws Exception {
        try {
            game.getPlayer(0).performsAction(game);
        }
        catch (RuntimeException e) {
            if (thrownException != null) {
                org.junit.Assert.fail("Exception already thrown! " + thrownException.getMessage());
                thrownException.printStackTrace();
            }
            thrownException = e;
        }
    }

    @Then("^an exception is not thrown$")
    public void an_exception_is_not_thrown() throws Exception {
        assertThat(thrownException, nullValue());
    }

    @Given("^(\\d+) players each with a ([^\"]*)$")
    public void players_each_with_a(int numberOfPlayers, String cardName) throws Exception {
        game = GameCreater.withNPlayers(numberOfPlayers, Card.valueOf(cardName.toUpperCase()));
    }

    @Then("^the card has no effect$")
    public void the_card_has_no_effect() throws Exception {
        final Player player2 = game.getPlayer(1);
        Outcome outcome = player2.performsAction(game);
        assertThat(outcome, is(Outcome.NO_EFFECT));
    }

    @Then("^the action taken is \"([^\"]*)\"$")
    public void the_action_taken_is(String actionName) throws Exception {
        assertThat(game.getPlayer(1).performsAction(game).getAction(), is(instanceOf(getClassWithActionName(actionName)
        )));
    }

    @Then("^no opponent was chosen$")
    public void no_opponent_was_chosen() throws Exception {
        try {
            assertThat(game.getPlayer(1).chooseOpponent(), is(equalTo(Optional.empty())));
        }
        catch (RuntimeException e) {
            if (thrownException != null) {
                org.junit.Assert.fail("Exception already thrown! " + thrownException.getMessage());
                thrownException.printStackTrace();
            }
            thrownException = e;
        }
    }


    @Given("^A game with (\\d+) players$")
    public void a_game_with_players(int numberOfPlayers) throws Exception {
        game = GameCreater.withNPlayers(numberOfPlayers);
    }

    @When("^Player (\\d+) is set to be safe$")
    public void player_is_set_to_be_safe(int playerNumber) throws Exception {
        game.getPlayer(playerNumber - 1).setSafe();
    }

    @Then("^Player (\\d+) doesn't have any opponent$")
    public void player_doesn_t_have_any_opponent(int playerNumber) throws Exception {
        game.getPlayersThatCanBeAttacked(game.getPlayer(playerNumber - 1));
    }

    @When("^Player (\\d+) draws a ([^\"]*)$")
    public void player_draws_a(int playerNumber, String cardName) throws Exception {
        game.getPlayer(playerNumber - 1).addToHand(Card.valueOf(cardName.toUpperCase()));
    }

    @When("^Player (\\d+) plays a ([^\"]*)$")
    public void player_plays_a(int playerNumber, String cardName) throws Exception {
        game.getPlayer(playerNumber - 1).plays(Card.valueOf(cardName.toUpperCase()));
    }

    @Then("^Player (\\d+) chooses the first player as the opponent$")
    public void player_chooses_the_first_player_as_the_opponent(int playerNumber) throws Exception {
        assertThat(game.getPlayer(playerNumber - 1).chooseOpponent().get(), is(equalTo(game.getPlayer(0))));
    }

    @When("^Player (\\d+) is removed$")
    public void player_is_removed(int playerNumber) throws Exception {
        game.removePlayer(playerNumber - 1);
    }

    @Then("^Player (\\d+) is removed2$")
    public void player_is_removed2(int playerNumber) throws Exception {
        assertThat(game.getPlayersStillInGame(), not(hasItem(game.getPlayer(playerNumber - 1))));
    }

    @Then("^the game ends$")
    public void the_game_ends() throws Exception {
        assertThat(game.shouldContinue(), is(false));
    }

    @Then("^Player (\\d+) is declared the winner$")
    public void player_is_declared_the_winner(int playerNumber) throws Exception {
        assertThat(game.getWinner(), is(equalTo(game.getPlayer(playerNumber - 1))));
    }

    @Then("^the game continues$")
    public void the_game_continues() throws Exception {
        assertThat(game.shouldContinue(), is(true));
    }

    @Given("^Player (\\d+) action is \"([^\"]*)\"$")
    public void player_action_is(int playerNumber, String actionClassName) throws Exception {
        Player player = game.getPlayer(playerNumber - 1);
        final Class<? extends Action> actionClass = super.getActionClass(actionClassName);
        final Action action = actionClass.newInstance();
        action.resolve(player, player.chooseOpponent().get(), game);
    }

    @When("^(\\d+) cards are played$")
    public void cards_are_played(int numberOfTurns) throws Exception {
        for (int i = 0; i < numberOfTurns; i++) {
            game.getDeck().remove(0);
        }
    }

    @Then("^next card throws an exception$")
    public void next_card_throws_an_exception() throws Exception {
        try {
            game.getDeck().remove(0);
            fail("Expected an exception to be thrown");
        }
        catch (RuntimeException e) {
        }
    }

    @Given("^A game$")
    public void a_game() throws Exception {
        game = new Game();
    }

    @When("^Player (\\d+) is set as the dealer$")
    public void player_is_set_as_the_dealer(int playerNumber) throws Exception {
        Dealer dealer = new Dealer(game.getPlayer(playerNumber - 1));
        game.setDealer(dealer);
    }

    @Then("^Player (\\d+) is the first to play$")
    public void player_is_the_first_to_play(int playerNumber) throws Exception {
        assertThat(game.nextPlayer(), is(equalTo(game.getPlayer(playerNumber - 1))));
    }

    @When("^the last game's winner is Player (\\d+)$")
    public void the_last_game_s_winner_is_Player(int playerNumber) throws Exception {
        game.setWinner(game.getPlayer(playerNumber - 1));
    }

    @Given("^next player$")
    public void next_player() throws Exception {
        game.nextPlayer();
    }

    @Then("^the next player is Player (\\d+)$")
    public void the_next_player_is_Player(int playerNumber) throws Exception {
        assertThat(game.nextPlayer(), is(equalTo(game.getPlayer(playerNumber - 1))));
    }

    @When("^a card is drawn (\\d+) times$")
    public void a_card_is_drawn_times(int times) throws Exception {
        for (int i = 0; i < times; i++) {
            game.drawCard();
        }
    }


    private Class getClassWithActionName(String actionName) {
        return GuessAction.class;
    }

}
