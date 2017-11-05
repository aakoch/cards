package com.adamkoch.cards.loveletter;

import com.adamkoch.Console;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Created by aakoch on 2017-11-04.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class RealPlayer extends SingleCardHandPlayer {

    private static final Logger LOGGER = LogManager.getLogger(RealPlayer.class);

    private String name;

    private Console console;

    public RealPlayer() {
        super("real player");
        this.console = new Console();
        name = "Adam"; //console.prompt("What is your name?");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setHand(Card... cards) {
        final String action = playedCard == Card.KING ? "given" : "dealt";
        tell("You were " +
                action +
                " a " + cards[0]);
        super.setHand(cards);
    }

    private void tell(String msg) {
        LOGGER.info(msg);
    }

    @Override
    public void startTurn() {
        chosenOpponent = Optional.empty();
    }

    @Override
    public void addToHand(Card card) {
        tell("You drew a " + card);
        super.addToHand(card);
    }

    @Override
    public Card determineCardToPlay() {
        playedCard = null;
        String input = null;
        while (playedCard == null) {
            try {
                input = console.prompt("Which card do you want to play?");
                playedCard = parseInputAsCard(input);
                if (playedCard == getHand() || playedCard == super.drawnCard) {
                    Card hand = getHand();
                    if (playedCard != Card.COUNTESS && (mustPlayCountess(hand, drawnCard) || mustPlayCountess(drawnCard,
                            hand))) {
                        tell("Ummm... don't you think you should play the countess? ;)");
                        playedCard = null;
                    }
                }
                else {
                    tell("Nice try, but you don't have a " + playedCard);
                    playedCard = null;
                }
            }
            catch (RuntimeException e) {
                tell("I'm sorry, but I don't know what card that is.");
                final LevenshteinDistance levenshteinDistance = LevenshteinDistance.getDefaultInstance();
                String finalInput = input;

                final Optional<Card> cardOptional = Stream.of(getHand(), drawnCard)
                                                          .min(Comparator.comparingInt(
                                                                  o -> levenshteinDistance.apply(
                                                                          finalInput.toUpperCase(),
                                                                          o.name()).intValue()));
                cardOptional.ifPresent(card1 -> {
                    tell("Did you mean " + card1.name() + "?");
                });
            }
        }
        return playedCard;
    }

    private boolean mustPlayCountess(Card card1, Card card2) {
        return card1 == Card.COUNTESS && (card2 == Card.KING || card2 == Card.PRINCE);
    }

    private Card parseInputAsCard(String answer) {
        return Card.valueOf(answer.toUpperCase());
    }

    @Override
    public Optional<Player> chooseOpponent() {
        if (playedCard == Card.HANDMAID) {
            return Optional.empty();
        }

        if (game.getPlayersThatCanBeAttacked(this).isEmpty()) {
            tell("Sorry, it looks like you can't attack anyone");
            chosenOpponent = Optional.empty();
        }
        else {
            while (!chosenOpponent.isPresent()) {
                Optional<Player> parsedPlayer = parseInputAsPlayer(console.prompt("Who do you want to attack?"));
                if (parsedPlayer.isPresent()) {
                    final List<Player> playersThatCanBeAttacked = game.getPlayersThatCanBeAttacked(this);
                    if (playersThatCanBeAttacked.contains(parsedPlayer.get())) {
                        chosenOpponent = parsedPlayer;
                    }
                    else {
                        tell("That player can't be attacked");
                    }
                }
                else {
                    tell("I'm don't know who that is. There is " + game.getPlayersStillInGame()
                                                                       .stream()
                                                                       .map(Player::getName)
                                                                       .collect(Collectors.joining(", ")));
                }
            }
        }

        return chosenOpponent;
    }

    private Optional<Player> parseInputAsPlayer(String input) {
        List<Player> players = game.getPlayersStillInGame();
        for (Player player : players) {
            if (player.getName().endsWith(input)) {
                return Optional.of(player);
            }
        }

        return Optional.empty();
    }


    @Override
    public void plays(Card card) {
        super.plays(card);
        playedCard = null;
    }

    @Override
    public Card determineCardToGuess() {
        Card card = null;
        while (card == null) {
            card = parseInputAsCard(console.prompt("Which card do you want to guess?"));
            if (card == Card.GUARD) {
                tell("You can't guess a Guard");
                card = null;
            }
        }

        return card;
    }

    @Override
    public void isShownHand(Player opponent) {
        if (playedCard != Card.KING) {
            tell(opponent.getName() + " shows you a " + opponent.getHand());
        }
    }
}
