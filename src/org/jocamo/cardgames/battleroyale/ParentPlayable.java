package org.jocamo.cardgames.battleroyale;

/**
 *
 * @author Joe
 */
public interface ParentPlayable<P extends Playable> extends Playable {
    
    /**
     * Returns the current child playable.
     * 
     * @return 
     */
    P getCurrentChild();
    
    /**
     * Returns the number of the current child playable.
     * @return 
     */
    int getCurrentChildNumber();
    
    /**
     * Returns the child of this playable corresponding to the given number
     * 
     * @param number - one-indexed; must be between one and the current child number, inclusive
     * @return 
     */
    P getChild(int number);
    
}
