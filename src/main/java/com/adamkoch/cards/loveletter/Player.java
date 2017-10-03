package com.adamkoch.cards.loveletter;

import com.adamkoch.utils.ListUtils;
import com.adamkoch.utils.RandomUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.adamkoch.cards.loveletter.Card.HANDMAID;
import static com.adamkoch.cards.loveletter.Card.PRINCESS;

/**
 * <p>Created by aakoch on 2017-09-19.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Player {
    private final String name;

    private Card hand;
    private Card lastPlayedCard;
    private List<KnownHand> knownHands;

    public Player(String name) {
        this.name = name;
        knownHands = new ArrayList<>();
    }

    public static Card determineCardToPlay(Card card1, Card card2) {
        Card card = null;

        if (card1 == PRINCESS || card2 == PRINCESS) {
            card = nonPrincess(card1, card2);
        }
        else if (card1 == Card.COUNTESS && (card2 == Card.KING || card2 == Card.PRINCE)) {
            card = card1;
        }
        else if (card2 == Card.COUNTESS && (card1 == Card.KING || card1 == Card.PRINCE)) {
            card = card2;
        }
        else if (card2.ordinal() < card1.ordinal()) {
            card = card2;
        }
        else if (card1.ordinal() < card2.ordinal()) {
            card = card1;
        }
        else {
            card = card2;
        }

        return card;
    }

    private static Card nonPrincess(Card card1, Card card2) {
        return card1 == PRINCESS ? card2 : card1;
    }

    public void clearHand() {
        hand = null;
    }

    public Card getHand() {
        return hand;
    }

    public void setHand(Card card) {
        hand = card;
    }

    public Card determineCardToPlay(Card cardDrawn, GameContext context) {
        final Card card1 = hand;

        Card card = determineCardToPlay(card1, cardDrawn);

        lastPlayedCard = card;

        if (card == card1) {
            hand = cardDrawn;
        }
        else {
            hand = card1;
        }

        return card;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", hand=" + hand +
                '}';
    }

    public String getName() {
        return name;
    }

    public Player chooseOpponent(List<Player> players) {
        if (knownHands.isEmpty()) {
            List<Player> remainingPlayers = new ArrayList<>(players);
            remainingPlayers.remove(this);
            remainingPlayers.removeIf(Player::isImmune);

            if (remainingPlayers.isEmpty()) {
                throw new NoOpponentException();
            }

            return RandomUtils.getRandom(remainingPlayers);
        }
        else {
            return ListUtils.last(knownHands).getPlayer();
        }
    }

    public boolean isImmune() {
        return lastPlayedCard == HANDMAID;
    }

    public Player choosePlayerToDiscardTheirCard(List<Player> players) {
        List<Player> remainingPlayers = new ArrayList<>(players);
        remainingPlayers.removeIf(Player::isImmune);

        if (remainingPlayers.isEmpty()) {
            throw new NoOpponentException();
        }
        return RandomUtils.getRandom(remainingPlayers);
    }

    public Player chooseOpponentToGuess(List<Player> players, GameContext gameContext) {
        players.removeIf(Player::isImmune);

        if (knownHands.isEmpty()) {
            return chooseOpponent(players);
        }
        else if (gameContext.wasAGuardPlayedSincePlayersLastTerm(this)) {
            Map<Player, List<Card>> otherGuesses = gameContext.getPlayersAndGuessSinceLastTurn(this);
            final Optional<Map.Entry<Player, List<Card>>> entryOptional = otherGuesses.entrySet()
                                                                              .stream()
                                                                              .sorted(Comparator.comparingInt(o -> o.getValue().size()))
                                                                              .findFirst();
            return entryOptional.get().getKey();
        }
        else {
            players.remove(this);

            if (players.isEmpty()) {
                throw new NoOpponentException();
            }
            final List<Player> intersect = ListUtils.intersect(players, knownHands.stream().map(KnownHand::getPlayer)
                                                                                  .collect(Collectors.toList()));

            if (intersect.isEmpty()) {
                return RandomUtils.getRandom(players);
            }
            else {
                assert intersect.size() == 1;
                return intersect.get(0);
            }
        }
    }

    public Player chooseOpponentToReveal(List<Player> players) {
        return chooseOpponent(players);
    }

    public Player chooseOpponentToCompare(List<Player> players) {
        return chooseOpponent(players);
    }

    public Player chooseOpponentToTrade(List<Player> players) {
        return chooseOpponent(players);
    }

    public void shownCard(Player player, Card card) {
        addKnownOpponentAndHand(player, card);
    }

    public Player getLastOpponentToShowACard() {
        return Optional.ofNullable(ListUtils.last(knownHands)).map(KnownHand::getPlayer).orElse(null);
    }

    public Card getLastCardShown() {
        return ListUtils.last(knownHands).getCard();
    }

    public void addKnownOpponentAndHand(Player player, Card card) {
        knownHands.add(new KnownHand(player, card));
    }

    public Card chooseCardToGuess() {
        return knownHands.get(0).getCard();
    }

    public boolean knowsOpponentsHand() {
        return !knownHands.isEmpty();
    }
}
