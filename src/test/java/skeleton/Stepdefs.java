package skeleton;

import com.adamkoch.cards.loveletter.Card;
import com.adamkoch.cards.loveletter.Player;
import com.adamkoch.cards.loveletter.SingleCardHandPlayer;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Stepdefs {

    Player player = new SingleCardHandPlayer();
    private Exception thrownException;

    @Given("^A player has a ([^\"]*)$")
    public void a_player_has_a(String cardName) throws Exception {
        player.setHand(Card.valueOf(cardName.toUpperCase()));
    }

    @When("^the player draws a ([^\"]*)$")
    public void the_player_draws_a(String cardName) throws Exception {
        try {
            player.addToHand(Card.valueOf(cardName.toUpperCase()));
        }
        catch (RuntimeException e) {
            thrownException = e;
        }
    }

    @Then("^the player plays the ([^\"]*)$")
    public void the_player_plays_the(String cardName) throws Exception {
        assertThat(player.determineCardToPlay(), is(Card.valueOf(cardName.toUpperCase())));
    }

    @Given("^A player$")
    public void a_player() throws Exception {
        thrownException = null;
    }

    @When("^the player turn starts$")
    public void the_player_turn_starts() throws Exception {
        // start turn
        thrownException = null;
    }

    @When("^the player plays a card$")
    public void the_player_plays_a_card() throws Exception {
        try {
            player.determineCardToPlay();
        }
        catch (RuntimeException e) {
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
            player.chooseOpponent();
        }
        catch (RuntimeException e) {
            thrownException = e;
        }
    }

    @When("^the player performs action$")
    public void the_player_performs_action() throws Exception {
        try {
            player.performsAction();
        }
        catch (RuntimeException e) {
            if (thrownException != null) {
                thrownException.printStackTrace();
                org.junit.Assert.fail("Exception already thrown! " + e.getMessage());
            }
            thrownException = e;
        }
    }

    @Then("^an exception is not thrown$")
    public void an_exception_is_not_thrown() throws Exception {
        assertThat(thrownException, nullValue());
    }

}
