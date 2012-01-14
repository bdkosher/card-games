package org.washcom.cardgames.battleroyale;

import java.util.Objects;
import org.washcom.cardgames.core.Deck;

/**
 *
 * @author Joe
 */
public class Player {
    
    private final String name;
    
    private final Deck hand = new Deck();
    
    private boolean participatingInRound = true;
    
    private boolean participatingInGame = true;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Deck getHand() {
        return hand;
    }

    public boolean isParticipatingInRound() {
        return participatingInRound;
    }

    public void setParticipatingInRound(boolean participatingInRound) {
        this.participatingInRound = participatingInRound;
    }

    public boolean isParticipatingInGame() {
        return participatingInGame;
    }

    public void setParticipatingInGame(boolean participatingInGame) {
        this.participatingInGame = participatingInGame;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
    
}
