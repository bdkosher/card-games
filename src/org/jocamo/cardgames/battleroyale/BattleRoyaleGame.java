package org.jocamo.cardgames.battleroyale;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jocamo.cardgames.core.Deck;
import org.jocamo.cardgames.util.LoopingIterator;

/**
 *
 * @author Joe
 */
public class BattleRoyaleGame {
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 4;
    private final List<Player> players;
    private int round = 0;
    private Player winner = null;
    /**
     * Represents the cards that were played from the players' hands this round 
     */
    private Deck battleHand = new Deck();

    public BattleRoyaleGame(List<Player> players, Deck deck) {
        if (players == null) {
            throw new NullPointerException("Players arg cannot be null.");
        }
        if (players.size() < MIN_PLAYERS || players.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException("Number of players must be between " + MIN_PLAYERS + " and " + MAX_PLAYERS);
        }
        if (deck == null) {
            throw new NullPointerException("Deck arg cannot be null.");
        }
        this.players = players;
        distributeDeck(deck);
    }

    /**
     * Distributes the deck to the players' hands. Remainder cards are included in the battle hand for 
     * the first round of play.
     * 
     * @param deck 
     */
    private void distributeDeck(Deck deck) {
        battleHand.collect(deck.draw(deck.size() % players.size()));
        Iterator<Player> playerIterator = new LoopingIterator(players);
        while (!deck.isEmpty()) {
            playerIterator.next().getHand().collect(deck.draw());
        }
    }

    public List<Player> getPlayers() {
        return players;
    }
    
    public int getRound() {
        return round;
    }

    /**
     * If null, it means the game has not concluded yet.
     * 
     * @return 
     */
    public Player getWinner() {
        return winner;
    }    

    /**
     * Returns the players who can actively play.
     * 
     * @return 
     */
    public List<Player> getActivePlayers() {
        List<Player> active = new ArrayList<Player>(players.size());
        for (Player player : players) {
            if (player.isActive()) {
                active.add(player);
            }
        }
        return active;
    }
    
    public boolean isGameOver() {
        return winner == null;
    }

    public BattleResult playRound() {
        if (isGameOver()) {
            return null;
        }
        round++;
        
        BattleResult battle = new BattleResult();
        return battle;
    }
}
