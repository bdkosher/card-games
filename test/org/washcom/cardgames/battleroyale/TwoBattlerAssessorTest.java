package org.washcom.cardgames.battleroyale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.*;
import org.junit.Test;
import org.washcom.cardgames.core.Card;
import org.washcom.cardgames.core.Denomination;
import static org.washcom.cardgames.core.Denomination.*;
import org.washcom.cardgames.core.Suit;

/**
 *
 * @author Joe
 */
public class TwoBattlerAssessorTest {
    
    TwoBattlerAssessor assessor = new TwoBattlerAssessor();
    
    Player player(int playerId) {
        return new Player("Player " + playerId);
    }
    
    BattleCard bc(Denomination denom, int playerId) {
        return new BattleCard(new Card(denom, Suit.values()[new Random().nextInt(Suit.values().length)]), 
                player(playerId));
    }
    
    Battle battle(final Denomination denom1, final Denomination denom2) {
        return new Battle(1, new BattleRoyaleGame(assessor)) {

            @Override
            public List<BattleCard> getBattleCards() {
                List<BattleCard> cards = new ArrayList<>();
                cards.add(bc(denom1, 1));
                cards.add(bc(denom2, 2));
                return cards;
            }

            @Override
            public List<Player> getBattlers() {
                return Arrays.<Player>asList(player(1), player(2));
            }
            
        };
    }
    
    @Test
    public void testTwoWinsOverAce() {
        assertEquals(TWO, assessor.pickWinner(battle(TWO, ACE)).getCard().getDenomination());
    }
    
    @Test
    public void testAceBeatsAllButRoyaltyAndTwo() {
        assertEquals(ACE, assessor.pickWinner(battle(THREE, ACE)).getCard().getDenomination());
        assertEquals(ACE, assessor.pickWinner(battle(FOUR, ACE)).getCard().getDenomination());
        assertEquals(ACE, assessor.pickWinner(battle(FIVE, ACE)).getCard().getDenomination());
        assertEquals(ACE, assessor.pickWinner(battle(SIX, ACE)).getCard().getDenomination());
        assertEquals(ACE, assessor.pickWinner(battle(SEVEN, ACE)).getCard().getDenomination());
        assertEquals(ACE, assessor.pickWinner(battle(EIGHT, ACE)).getCard().getDenomination());
        assertEquals(ACE, assessor.pickWinner(battle(NINE, ACE)).getCard().getDenomination());
        assertEquals(ACE, assessor.pickWinner(battle(TEN, ACE)).getCard().getDenomination());
        assertNull(assessor.pickWinner(battle(JACK, ACE)));
        assertNull(assessor.pickWinner(battle(QUEEN, ACE)));
        assertNull(assessor.pickWinner(battle(KING, ACE)));
    }
    
    
    @Test
    public void testPlayerOneWinsWithTwoOverAce() {
        assertEquals(player(1), assessor.pickWinner(battle(TWO, ACE)).getPlayedBy());
        assertEquals(player(2), assessor.pickWinner(battle(ACE, TWO)).getPlayedBy());
    }
    
    @Test
    public void testBattleRoyaleCombosResultInNoWinner() {
        assertNull(assessor.pickWinner(battle(JACK, JACK)));
        assertNull(assessor.pickWinner(battle(QUEEN, JACK)));
        assertNull(assessor.pickWinner(battle(KING, JACK)));
        assertNull(assessor.pickWinner(battle(JACK, QUEEN)));
        assertNull(assessor.pickWinner(battle(QUEEN, QUEEN)));
        assertNull(assessor.pickWinner(battle(KING, QUEEN)));
        assertNull(assessor.pickWinner(battle(JACK, KING)));
        assertNull(assessor.pickWinner(battle(QUEEN, KING)));
        assertNull(assessor.pickWinner(battle(KING, KING)));
    }
    
}
