package org.jocamo.cardgames.battleroyale;

import java.util.ArrayList;
import java.util.List;
import org.jocamo.cardgames.core.Deck;

/**
 *
 * @author Joe
 */
public class BattleRoyaleGame {

    public static final int MIN_PLAYERS = 3;
    public static final int MAX_PLAYERS = 3;
    private final List<Player> players;
    private final BattleRules rules;
    private final List<Round> roundHistory = new ArrayList<>();
    private int currentRoundNumber = 0;
    private Player winner = null;
    
    /**
     * Represents the cards that go to the victor of the very first round of the game. Usually, they contain leftover
     * cards from the initial distribution of cards. However, the game pot is accessible to rules, which may use
     * the game pot to collect cards between rounds.
     */
    private final Deck gamePot = new Deck();

    public BattleRoyaleGame(List<Player> players, BattleRules rules, Deck deck) {
        if (players == null) {
            throw new NullPointerException("Players arg cannot be null.");
        }
        if (players.size() < MIN_PLAYERS || players.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException("Number of players must be between " + MIN_PLAYERS + " and " + MAX_PLAYERS);
        }
        this.players = players;
        this.rules = rules;
    }

    /**
     * In typical rules, the game pot holds the cards leftover from the deal which go to the victor of the very first 
     * round of play. More generically speaking, the game pot is a collection of cards at the game's scope, thus 
     * allowing cards to be collected in a round subsequent to when they were placed in the pot.
     * 
     * @return the game pot - never null
     */
    public Deck getGamePot() {
        return gamePot;
    }
    
    /**
     * Return everyone who is playing or has played this game of BATTLE ROYALE!!!
     * 
     * @return 
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the current round number. A value of 0 indicates the game has not started.
     * 
     * @return 
     */
    public int getCurrentRoundNumber() {
        return currentRoundNumber;
    }

    /**
     * Returns the Game's Round instance for the given round number.
     * 
     * @param roundNumber - must be between one and the currentRoundNumber inclusive
     * @return null if the round number is invalid: less than 0 or greater than the current round number.
     */
    public Round getRound(int roundNumber) {
        if (roundNumber < 1 || roundNumber > currentRoundNumber) {
            throw new IllegalArgumentException("Round nbr arg, " + roundNumber + " must be at least one and no greater than "
                    + currentRoundNumber);
        }
        /* rounds are 1-indexed so we must subtract. */
        return roundHistory.get(roundNumber - 1);
    }
    
    /**
     * Returns the current round. This method will throw an exception if the game is not started.
     * 
     * @return 
     */
    public Round getCurrentRound() {
        return getRound(currentRoundNumber);
    }

    /**
     * Returns the winner of the game. If null, it means the game has not concluded yet.
     * 
     * @return 
     */
    public Player getWinner() {
        return winner;
    }
    
    /**
     * When this method is called in the midst of a round or at the conclusion of a round, the game ends. If the
     * provided winner is null, this method has no effect.
     * 
     * @param winner - must be a player who's playing the game.
     * @throws IllegalArgumentException - if the winner is not a player of this game.
     */
    public void setWinner(Player winner) {
        if (!players.contains(winner)) {
            throw new IllegalArgumentException("Winner must be playing the game.");
        }
        this.winner = winner;
    }

    /**
     * Returns the players who can actively play.
     * 
     * @return 
     */
    public List<Player> getActivePlayers() {
        List<Player> active = new ArrayList<>(players.size());
        for (Player player : players) {
            if (player.isActive()) {
                active.add(player);
            }
        }
        return active;
    }

    /**
     * If the game is over, this method returns true.
     * 
     * @return 
     */
    public boolean isGameOver() {
        return winner == null;
    }

    /**
     * If the game is not over, returns the results of the battle. If the game is over, this method
     * returns null.
     */
    public void playRound() {
        if (isGameOver()) {
            return;
        }
        currentRoundNumber++;
        Round round = new Round();
        roundHistory.add(round);
        //start round
        while (!round.isOver()) {
            // start round stage
            // end round stage
            // advance stage
        }
        // end round
    }
    
    public Deck buildInitialRoundPot() {
        Deck roundPot = new Deck();
        while (!gamePot.isEmpty()) {
            roundPot.collect(gamePot.draw());
        }
        return roundPot;
    }

}
