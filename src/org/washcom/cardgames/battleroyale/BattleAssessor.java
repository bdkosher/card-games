package org.washcom.cardgames.battleroyale;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Joe
 */
public interface BattleAssessor {
    
    Player pickWinner(List<BattleCard> battleCards);
    
    Map<Player, Integer> determineFees(Battle battle);
}
