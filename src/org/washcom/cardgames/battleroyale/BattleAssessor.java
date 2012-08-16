package org.washcom.cardgames.battleroyale;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Joe
 */
public interface BattleAssessor {

    /**
     * Picks the winning battle 
     * @param battleCards
     * @return 
     */
    BattleCard pickWinner(Battle battle);

    /**
     * Returns a mapping of Players to the number of cards they must pay as fees in order to continue the battle. If a player is
     * absent from the map, that means the player is not eligible to continue battle.
     */
    Map<Player, Integer> determineFees(Battle battle);
}
