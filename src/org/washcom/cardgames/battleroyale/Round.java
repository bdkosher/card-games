package org.washcom.cardgames.battleroyale;

/**
 *
 * @author Joe
 */
public class Round {
    
    private final int number;
    private final BattleRoyaleGame game;

    public Round(int number, BattleRoyaleGame game) {
        if (game == null) {
            throw new NullPointerException("Game arg cannot be null.");
        }
        this.number = number;
        this.game = game;
    }

    public int getNumber() {
        return number;
    }

    public BattleRoyaleGame getGame() {
        return game;
    }
    
}
