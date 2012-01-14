package org.jocamo.cardgames.battleroyale;

import org.jocamo.cardgames.core.Deck;

/**
 *
 * @author Joe
 */
public class Player {

    private final int id;
    private Deck hand;
    private int roundsParticipatedIn = 0;
    private int roundsWon = 0;
    private int roundsInactive = 0;
    private boolean active;

    public Player(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Deck getHand() {
        return hand;
    }

    public void setHand(Deck hand) {
        this.hand = hand;
    }
    
    public void incrementRoundsParticipatedIn() {
        ++roundsParticipatedIn;
    }

    public int getRoundsParticipatedIn() {
        return roundsParticipatedIn;
    }
    
    public void incrementRoundsWon() {
        ++roundsWon;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    /**
     * Returns the number of rounds that this player was inactive.
     * 
     * @return 
     */
    public int getRoundsInactive() {
        return roundsInactive;
    }
    
    public void incrementRoundsInactive() {
        ++roundsInactive;
    }
    
    /**
     * Exchanges hands with the other player.
     * 
     * @param other 
     */
    public void exchangeHands(Player other) {
        Deck temp = getHand();
        setHand(other.getHand());
        other.setHand(temp);
    }
    
    /**
     * Returns true if the player is active.
     * 
     * @return 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets whether or not a player is active.
     * 
     * @param active 
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString() {
        return "Player " + id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.id;
        return hash;
    }
    
}
