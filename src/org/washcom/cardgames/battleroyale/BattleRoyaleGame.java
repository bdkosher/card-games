package org.washcom.cardgames.battleroyale;

/**
 *
 * @author Joe
 */
public class BattleRoyaleGame {
    
    private int currentRoundNumber;
    
    private Round currentRound;
    
    public Round getCurrentRound() {
        return currentRound;
    }
    
    public void nextRound() {
        if (currentRound != null) {
            //currentRound.end();
        }
        currentRound = new Round(++currentRoundNumber, this);
    }

    public int getCurrentRoundNumber() {
        return currentRoundNumber;
    }
    
}
