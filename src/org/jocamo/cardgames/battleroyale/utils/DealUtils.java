package org.jocamo.cardgames.battleroyale.utils;

import java.util.Iterator;
import org.jocamo.cardgames.battleroyale.Player;
import org.jocamo.cardgames.core.Deck;
import org.jocamo.cardgames.util.LoopingIterator;

/**
 *
 * @author Katie
 */
public class DealUtils {
    /**
     * Deals all cards in the given deck to the given iteraton of players; each successive player is given
     * a successive card in the deck, just like in a real life deal.
     * 
     * @param deck - the deck of cards to deal out, cannot be null
     * @param players - the iterable unit of players to deal cards to; cannot be null
     */
    public static void dealAll(Deck deck, Iterable<Player> players) {
        if (deck == null) {
            throw new NullPointerException("Deck arg cannot be null.");
        }
        if (players == null) {
            throw new NullPointerException("Players arg cannot be null.");
        }
        Iterator<Player> playerIterator = new LoopingIterator(players);
        while (!deck.isEmpty()) {
            playerIterator.next().getHand().collect(deck.draw());
        }
    }
    
    
}
