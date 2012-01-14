package org.jocamo.cardgames.battleroyale.rules;

import java.util.List;
import org.jocamo.cardgames.battleroyale.BattleRoyaleGame;
import org.jocamo.cardgames.battleroyale.Player;

/**
 * This rule declares the winner provided there is exactly one active player.
 * 
 * @author Joe
 */
public class LastActivePlayerIsWinnerRule {
    
    public void onRoundEnd(BattleRoyaleGame game) {
        List<Player> active = game.getActivePlayers();
        if (active.size() == 1) {
            game.setWinner(active.iterator().next());
        }
    }
    
}
