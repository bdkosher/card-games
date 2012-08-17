package org.washcom.cardgames.battleroyale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.washcom.cardgames.core.Denomination;

import static org.washcom.cardgames.core.Denomination.*;

/**
 * A BattleAssessor for when there's exactly two battlers in the battle. If not, the methods will throw
 * IllegalArgumentExceptions.
 * 
 * @author Joe
 */
public class TwoBattlerAssessor implements BattleAssessor {
    
    public static int MAX_VALUE_DIFF_FEE = 3;
    
    public static int MAX_VALUE_DIFF = 2;
    
    public static final Map<Denomination, Integer> battleRoyaleFees = new HashMap<Denomination, Integer>() {
        {
            battleRoyaleFees.put(Denomination.ACE, 4);
            battleRoyaleFees.put(Denomination.KING, 3);
            battleRoyaleFees.put(Denomination.QUEEN, 2);
            battleRoyaleFees.put(Denomination.JACK, 1);
        }
    };

    @Override
    public BattleCard pickWinner(Battle battle) {
        List<BattleCard> cards = battle.getBattleCards();
        checkBattlers(cards);
        BattleCard one = cards.get(0);
        BattleCard two = cards.get(1);
        
        /*
         * In a Battle Royale scenario, no one wins
         */
        if (isBattleRoyale(one, two)) {
            return null;
        }
        /*
         * In an Ace-Two combo, the Two wins.
         */
        if (isAceTwoCombo(one, two)) {
            return one.getCard().getDenomination() == ACE ? two : one;
        }
        
        /*
         * If the value difference is equal or under the max value difference, no winner can be declared. 
         */
        int diff = one.computeValueDifference(two);
        if (Math.abs(diff) <= MAX_VALUE_DIFF) {
            return null;
        }
        
        return diff > 0 ? one : two;
    }
    
    public static boolean isBattleRoyale(BattleCard one, BattleCard two) {
        return one.isBattleRoyaleEligible() && two.isBattleRoyaleEligible();
    }
    
    public static boolean isAceTwoCombo(BattleCard one, BattleCard two) {
        return (one.getCard().getDenomination() == ACE && two.getCard().getDenomination() == TWO)
                || (two.getCard().getDenomination() == ACE && one.getCard().getDenomination() == TWO);
    }

    @Override
    public Map<Player, Integer> determineFees(Battle battle) {
        List<BattleCard> cards = battle.getBattleCards();
        checkBattlers(cards);
        BattleCard one = cards.get(0);
        BattleCard two = cards.get(1);

        Map<Player, Integer> result = new HashMap<>();
        if (isBattleRoyale(one, two)) {
            result.put(one.getPlayedBy(), battleRoyaleFees.get(one.getCard().getDenomination()));
            result.put(two.getPlayedBy(), battleRoyaleFees.get(two.getCard().getDenomination()));
            return result;
        }
        
        int fee = MAX_VALUE_DIFF_FEE - Math.abs(one.computeValueDifference(two));
        result.put(one.getPlayedBy(), fee);
        result.put(two.getPlayedBy(), fee);
        return result;
    }
    
    private static void checkBattlers(List<BattleCard> cards) {
        if (cards.size() != 2) {
            throw new IllegalArgumentException("Must be two players.");
        }
    }
}
