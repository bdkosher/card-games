package org.washcom.cardgames.core;

import java.util.Objects;

/**
 *
 * @author Joe
 */
public class Player {

    private final String name;
    private final Deck hand = new Deck();

    public Player(String name) {
        if (name == null) {
            throw new NullPointerException("Player name cannot be null.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Deck getHand() {
        return hand;
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
