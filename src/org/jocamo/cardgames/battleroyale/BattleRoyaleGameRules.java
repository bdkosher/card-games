package org.jocamo.cardgames.battleroyale;

/**
 *
 * @author Katie
 */
public interface BattleRoyaleGameRules {
    
    void onGameStart(BattleRoyaleGame game);
    
    void onGameEnd(BattleRoyaleGame game);
    
    void onRoundStart(BattleRoyaleGame game);
    
    void onRoundEnd(BattleRoyaleGame game);
    
    void doBattle(BattleRoyaleGame game);
    
    void onCardPlayed(BattleRoyaleGame game);
    
}
