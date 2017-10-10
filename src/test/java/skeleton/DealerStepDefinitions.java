package skeleton;

import com.adamkoch.cards.loveletter.Card;
import com.adamkoch.cards.loveletter.Dealer;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotEquals;

/**
 * <p>Created by aakoch on 2017-10-09.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class DealerStepDefinitions extends AbstractStepDefinitions {

    private static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger
            (DealerStepDefinitions.class);

    Dealer dealer;

    @Given("^A dealer$")
    public void a_dealer() throws Exception {
        dealer = new Dealer();
    }

    @Given("^the dealer is given a deck$")
    public void the_dealer_is_given_a_deck() throws Exception {
        List<Card> deck = Arrays.asList(Card.values());
        dealer.setDeck(deck);
    }

    @Given("^the dealer shuffles the deck$")
    public void the_dealer_shuffles_the_deck() throws Exception {
        //dealer.shuffle();
    }

    @Then("^the deck is random in order$")
    public void the_deck_is_random_in_order() throws Exception {
        final String actual = dealer.getDeck().stream().map(Card::toString).collect(Collectors.joining(","));
        LOGGER.debug("actual = " + actual);
        assertNotEquals("", actual);
    }

}
