package skeleton;

import com.adamkoch.cards.loveletter.*;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class ActionStepDefinitions extends AbstractStepDefinitions {
    Card card;

    @Given("^A \"([^\"]*)\" is played$")
    public void a_is_played(String cardName) throws Exception {
        card = Card.valueOf(cardName.toUpperCase());
    }

    @Then("^the action is \"([^\"]*)\"$")
    public void the_action_is(String actionClassName) throws Exception {
        final Class<? extends Action> actionClass = getActionClass(actionClassName);
        assertThat(card.getAction(), is(instanceOf(actionClass)));
    }

}
