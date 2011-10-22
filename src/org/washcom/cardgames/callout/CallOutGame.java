package org.washcom.cardgames.callout;

import java.util.Arrays;
import java.util.Iterator;
import org.washcom.cardgames.core.Deck;
import org.washcom.cardgames.core.Denomination;
import org.washcom.util.LoopingIterator;

/**
 * Game where you call out "Ace, Two, Three..." and if you turn over the card you've called out, you lose.
 * 
 * @author Joe
 */
public class CallOutGame {
   
    private static final Iterator<Denomination> CALL_OUTS = new LoopingIterator<>(Arrays.asList(Denomination.values()));
    
    public enum Result {

        WIN, LOSE
    }
    
    private final Deck deck;

    public CallOutGame(Deck deck) {
        if (deck == null) {
            throw new NullPointerException("Deck arg cannot be null.");
        }
        if (deck.isEmpty()) {
            throw new IllegalArgumentException("Cannot play with an empty deck.");
        }
        this.deck = deck;
    }
    
    public Result playGame() {
        while (!deck.isEmpty()) {
            if (deck.draw().getDenomination() == CALL_OUTS.next()) {
                return Result.LOSE;
            }
        }
        return Result.WIN;
    }
}
