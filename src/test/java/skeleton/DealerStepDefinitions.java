package skeleton;

import com.adamkoch.cards.loveletter.Card;
import com.adamkoch.cards.loveletter.Dealer;
import com.adamkoch.cards.loveletter.GameCreater;
import com.adamkoch.cards.loveletter.Player;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
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
    List<Player> players;

    @Given("^A dealer$")
    public void a_dealer() throws Exception {
        dealer = new Dealer(null);
    }

    @Given("^the dealer is given a deck$")
    public void the_dealer_is_given_a_deck() throws Exception {
        dealer.setDeck(Card.deck());
    }

    @Given("^the dealer shuffles the deck$")
    public void the_dealer_shuffles_the_deck() throws Exception {
        dealer.shuffle();
    }

    @Then("^the deck is random in order$")
    public void the_deck_is_random_in_order() throws Exception {
        final String actual = dealer.getDeck().stream().map(Card::toString).collect(Collectors.joining(","));
        assertNotEquals("GUARD,GUARD,GUARD,GUARD,GUARD,PRIEST,BARON,HANDMAID,PRINCE,PRIEST,BARON,HANDMAID,PRINCE,KING,COUNTESS,PRINCESS", actual);
    }

    @Given("^the dealer deals to (\\d+) players$")
    public void the_dealer_deals_to_players(int numberOfPlayers) throws Exception {
        players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(GameCreater.createPlayer(i + 1));
        }
        dealer.setDeck(Card.deck());
        dealer.deal(players);
    }

    @Then("^each of the (\\d+) players have a card$")
    public void each_of_the_players_have_a_card(int numberOfPlayers) throws Exception {
        assertEquals(players.size(), numberOfPlayers);
    }

}
