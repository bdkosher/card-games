package org.washcom.cardgames.battleroyale;

import org.washcom.cardgames.core.Deck;

/**
 *
 * @author Joe
 */
public class Player {
    
    private final String name;
    
    private final Deck hand = new Deck();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
