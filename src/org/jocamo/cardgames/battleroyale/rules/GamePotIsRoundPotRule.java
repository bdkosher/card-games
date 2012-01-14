package org.jocamo.cardgames.battleroyale.rules;

import org.jocamo.cardgames.battleroyale.BattleRoyaleGame;

/**
 * In this rule, any cards in the game pot are immediately put into the round pot at the beginning of the round.
 * 
 * @author Joe
 */
public class GamePotIsRoundPotRule {

    public void onRoundStart(BattleRoyaleGame game) {
        game.getCurrentRound().getPot().collect(game.getGamePot().drawAll());
    }
}
