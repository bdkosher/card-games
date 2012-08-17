package org.washcom.cardgames.battleroyale;

import java.util.List;
import java.util.Map;

import org.washcom.cardgames.core.Denomination;


public class DefaultBattleAccessor implements BattleAssessor {
    

    @Override
    public BattleCard pickWinner(Battle battle) {
        for (int i = 0; i < battle.getBattleCards().size(); i++) {
            for (int j = i + 1; j < battle.getBattleCards().size(); j++) {
                int val1 = battle.getBattleCards().get(i).getCard().getDenomination().getValue();
                int val2 = battle.getBattleCards().get(j).getCard().getDenomination().getValue();
                if (Math.abs(val1 - val2) < 3) {
                    return null;
                }
            }
        }
        
        if (hasMultipleRoyalty(battle.getBattleCards())) {
            return null;
        }
        
        if (hasJackEight(battle.getBattleCards())) {
            return null;
        }
        
        throw new RuntimeException("Not implemented");
    }
    
    private boolean hasJackEight(List<BattleCard> cards) {
        boolean foundJack = false;
        boolean foundEight = false;
        for (BattleCard card : cards) {
            if (card.getCard().getDenomination() == Denomination.EIGHT) {
                foundEight = true;
            } else if (card.getCard().getDenomination() == Denomination.JACK) {
                foundJack = true;
            }
        }

        return foundEight && foundJack;
    }
    
    private boolean hasMultipleRoyalty(List<BattleCard> cards) {
        int royaltyCount = 0;
        for (BattleCard card : cards) {
            if (card.getCard().getDenomination().isRoyalty() || card.getCard().getDenomination() == Denomination.ACE) {
                royaltyCount++;
            }
            if (royaltyCount > 1) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Map<Player, Integer> determineFees(Battle battle) {
        // TODO Auto-generated method stub
        return null;
    }

}
