package org.jocamo.cardgames.battleroyale;

import org.jocamo.cardgames.core.Card;

/**
 *
 * @author Joe
 */
public interface HighCardChooser {
    
    /**
     * Given an array of cards, picks the high cards and returns the index of the highest card. If multiple cards
     * exist of equal value, the array of all high cards is returned.
     * 
     * @param cards
     * @return an array of indexes no longer than the length of the input card array.
     */
    int[] choose(Card[] cards);
}
