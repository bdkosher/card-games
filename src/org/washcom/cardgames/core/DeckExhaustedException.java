package org.washcom.cardgames.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Indicates when an attempt to draw cards from a deck results in the deck running out of cards.
 * 
 * @author Joe
 */
public class DeckExhaustedException extends RuntimeException {
    
    private final List<Card> drawn;
    
    /**
     * Create an exception when one or more cards are drawn from an empty deck.
     */
    public DeckExhaustedException() {
        this(new ArrayList<Card>(0));
    }
    
    /**
     * Create an exception when more cards are intended to be drawn from a non-empty deck than
     * the deck contains.
     * 
     * @param drawn - the cards that were drawn during the draw that exhausted the deck. The size
     *      of this list is by definition less than the desired amount of cards intended to be drawn.
     */
    public DeckExhaustedException(List<Card> drawn) {
        this.drawn = Collections.unmodifiableList(new ArrayList<Card>(drawn));
    }
    
    /**
     * Returns the drawn cards at the time of deck exhaustion. Never is null.
     * 
     * @return 
     */
    public List<Card> getDrawn() {
        return drawn;
    }
    
}
