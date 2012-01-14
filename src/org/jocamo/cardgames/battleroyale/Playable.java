package org.jocamo.cardgames.battleroyale;

import java.util.List;
import org.jocamo.cardgames.core.Deck;

/**
 * A playable is something that can be played: a game, a round of a game, a phase of a round, a series of games, etc.
 * 
 * @author Joe
 */
public interface Playable {
    
    /**
     * Enumerates the state of the playable.
     * TODO permit a playable status type to be parameterized so that additional states can be used if needed.
     */
    public enum Status {
        /** Indicates that the play method has not been executed yet. */
        NOT_STARTED, 
        /** Indicates that the play has been called but has not finished executing. */
        IN_PROGRESS, 
        /** Indicates that the play method has been called and has completed. */
        COMPLETED
    }
    
    /**
     * Return all of the players participating in this playable. Such players may be either eligible or
     * ineligible to play.
     * 
     * @return 
     */
    List<Player> getPlayers();
    
    /**
     * Returns all of the players eligible to play this playable.
     * 
     * TODO consider assigning some state to players and having a method to get players of a specific state.
     * Consider the possibilities of a player having multiple states at one time
     * 
     * @return 
     */
    List<Player> getEligiblePlayers();
    
    /**
     * Returns the status of the playable.
     * 
     * @return 
     */
    Status getStatus();
    
    /**
     * Starts the playable and returns when the playable has been completed.
     */
    void play();
    
    /**
     * This represents the cards which are awarded to the winner of this playable
     * 
     * @return 
     */
    Deck getPot();
    
}
