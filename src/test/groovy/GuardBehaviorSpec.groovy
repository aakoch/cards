import com.adamkoch.card.loveletter.Behavior
import com.adamkoch.card.loveletter.Card
import com.adamkoch.card.loveletter.GuardBehavior
import spock.lang.Specification
import spock.lang.Unroll


/**
 *
 * <p>Created by aakoch on 2017-10-02.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
@Unroll
class GuardBehaviorSpec extends Specification {

    def "when a player has a #CardA and a #CardB, they must play #Outcome"() {
        expect:
        cardPicker.pickCard(CardA, CardB) == Outcome

        where:
        CardA       | CardB         || Outcome
        Card.PRINCE | Card.COUNTESS || Card.COUNTESS
        Card.KING   | Card.COUNTESS || Card.COUNTESS
    }

    def "when a player plays a #CardA, they must #Outcome"() {
        expect:
        playCard(CardA) == GuardBehavior

        where:
        CardA      || Outcome
        Card.GUARD || GuardBehavior
    }

    Class<Behavior> playCard(Card card) {
        GuardBehavior.class
    }

    Card pickCard(Card card, Card card2) {
        max(card, card2)
    }

    Card max(Card card, Card card2) {
        if (card.ordinal() > card2.ordinal())
            card
        else
            card2
    }
}