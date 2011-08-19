package org.jocamo.cardgames.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jocamo.cardgames.core.Card;

/**
 *
 * @author Joe
 */
public class DeckExhaustedException extends RuntimeException {
    
    private final List<? extends Card> drawn;
    
    public DeckExhaustedException() {
        this(new ArrayList<Card>(0));
    }
    /**
     * 
     * @param drawn - the cards that were drawn during the draw that exhausted the deck.
     */
    public DeckExhaustedException(List<? extends Card> drawn) {
        this.drawn = Collections.unmodifiableList(new ArrayList<Card>(drawn));
    }
    
    public List<? extends Card> getDrawn() {
        return drawn;
    }
    
}
