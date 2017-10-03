package com.adamkoch.cards.loveletter;

import com.adamkoch.cards.NotYetImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Created by aakoch on 2017-09-25.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class GameContext {
    private List<Card> drawPile;
    private PlayerIterator playerIterator;
    private List<PlayResultContainer> playResults;

    public GameContext(List<Card> drawPile, List<Player> players, Dealer dealer) {
        this.drawPile = drawPile;
        this.playerIterator = new PlayerIterator(players, players.indexOf(dealer.asPlayer()));
        playResults = new ArrayList<>();
    }

    public boolean shouldContinue() {
        return playerIterator.size() > 1 && !drawPile.isEmpty();
    }

    public Player nextPlayer() {
        return playerIterator.next();
    }

    public void removePlayer(Player player) {
        playerIterator.remove(player);
    }

    public void addResult(Player player, Card playedCard, PlayResult playResult) {
        playResults.add(new PlayResultContainer(player, playedCard, playResult));
    }

    public List<Player> getPlayers() {
        final ArrayList<Player> players = new ArrayList<>();
        players.addAll(playerIterator.getPlayerList());
        return players;
    }

    /**
     * Get each player and which cards were guessed.
     */
    public Map<Player, List<Card>> getPlayersAndGuessSinceLastTurn(Player player) {
        final List<PlayResultContainer> subList = getPlayResultsSinceLastTurn(player, playResults);

        return subList.stream().filter(playResultContainer -> playResultContainer.getPlayedCard() == Card.GUARD)
                      .map(PlayResultContainer::getPlayResult)
               .collect(Collectors.groupingBy(PlayResult::getTargetedPlayer,
                Collectors.mapping(PlayResult::getGuessedCard, Collectors.toList())));
    }

    public boolean wasAGuardPlayedSincePlayersLastTerm(Player player) {
        final List<PlayResultContainer> subList = getPlayResultsSinceLastTurn(player, playResults);
        return subList.stream().anyMatch(playResultContainer -> playResultContainer.getPlayedCard() == Card.GUARD);
    }

    public static List<PlayResultContainer> getPlayResultsSinceLastTurn(Player player, List<PlayResultContainer> playResults) {
        final int fromIndex = playResults.stream()
                                 .map(PlayResultContainer::getPlayer)
                                 .collect(Collectors.toList())
                                 .lastIndexOf(player);
        return playResults.subList(fromIndex + 1, playResults.size());
    }

    public List<PlayResultContainer> getPlayResultsSinceLastTurn(Player player) {
        return getPlayResultsSinceLastTurn(player, playResults);
    }
}
