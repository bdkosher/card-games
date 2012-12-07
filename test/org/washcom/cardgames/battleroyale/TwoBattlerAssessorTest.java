package org.washcom.cardgames.battleroyale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    Denomination pickWinner(Denomination one, Denomination two) {
        BattleCard battleCard = assessor.pickWinner(battle(one, two));
        return battleCard == null ? null : battleCard.getCard().getDenomination();
    }

    int[] determineFees(Denomination one, Denomination two) {
        Map<Player, Integer> fees = assessor.determineFees(battle(one, two));
        int fee1 = fees.get(player(1));
        int fee2 = fees.get(player(2));
        return new int[]{fee1, fee2};
    }
    
    int[] expectedFees(int one, int two) {
        return new int[]{one, two};
    }

    Map<Player, Integer> determineFees(Battle battle) {
        return assessor.determineFees(battle);
    }

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
        assertEquals(TWO, pickWinner(TWO, ACE));
    }

    @Test
    public void testAceBeatsAllButRoyaltyAndTwo() {
        assertEquals(ACE, pickWinner(THREE, ACE));
        assertEquals(ACE, pickWinner(FOUR, ACE));
        assertEquals(ACE, pickWinner(FIVE, ACE));
        assertEquals(ACE, pickWinner(SIX, ACE));
        assertEquals(ACE, pickWinner(SEVEN, ACE));
        assertEquals(ACE, pickWinner(EIGHT, ACE));
        assertEquals(ACE, pickWinner(NINE, ACE));
        assertEquals(ACE, pickWinner(TEN, ACE));
        assertNull(pickWinner(JACK, ACE));
        assertNull(pickWinner(QUEEN, ACE));
        assertNull(pickWinner(KING, ACE));
    }

    @Test
    public void testTwoBeatsAce() {
        assertEquals(TWO, pickWinner(TWO, ACE));
        assertEquals(TWO, pickWinner(ACE, TWO));
    }

    @Test
    public void testBattleRoyaleCombosResultInNoWinner() {
        assertNull(pickWinner(JACK, JACK));
        assertNull(pickWinner(QUEEN, JACK));
        assertNull(pickWinner(KING, JACK));
        assertNull(pickWinner(ACE, JACK));
        assertNull(pickWinner(JACK, QUEEN));
        assertNull(pickWinner(QUEEN, QUEEN));
        assertNull(pickWinner(KING, QUEEN));
        assertNull(pickWinner(ACE, QUEEN));
        assertNull(pickWinner(JACK, KING));
        assertNull(pickWinner(QUEEN, KING));
        assertNull(pickWinner(KING, KING));
        assertNull(pickWinner(ACE, KING));
        assertNull(pickWinner(JACK, ACE));
        assertNull(pickWinner(QUEEN, ACE));
        assertNull(pickWinner(KING, ACE));
        assertNull(pickWinner(ACE, ACE));
    }
    
    @Test
    public void testBattleRoyaleFees() {
        assertArrayEquals(expectedFees(1, 1), determineFees(JACK, JACK));
        assertArrayEquals(expectedFees(1, 2), determineFees(JACK, QUEEN));
        assertArrayEquals(expectedFees(1, 3), determineFees(JACK, KING));
        assertArrayEquals(expectedFees(1, 4), determineFees(JACK, ACE));
        assertArrayEquals(expectedFees(2, 1), determineFees(QUEEN, JACK));
        assertArrayEquals(expectedFees(2, 2), determineFees(QUEEN, QUEEN));
        assertArrayEquals(expectedFees(2, 3), determineFees(QUEEN, KING));
        assertArrayEquals(expectedFees(2, 4), determineFees(QUEEN, ACE));
        assertArrayEquals(expectedFees(3, 1), determineFees(KING, JACK));
        assertArrayEquals(expectedFees(3, 2), determineFees(KING, QUEEN));
        assertArrayEquals(expectedFees(3, 3), determineFees(KING, KING));
        assertArrayEquals(expectedFees(3, 4), determineFees(KING, ACE));
        assertArrayEquals(expectedFees(4, 1), determineFees(ACE, JACK));
        assertArrayEquals(expectedFees(4, 2), determineFees(ACE, QUEEN));
        assertArrayEquals(expectedFees(4, 3), determineFees(ACE, KING));
        assertArrayEquals(expectedFees(4, 4), determineFees(ACE, ACE));
    }

    @Test
    public void testIdenticalCardsResultInNoWinner() {
        for (Denomination denom : Denomination.values()) {
            assertNull(pickWinner(denom, denom));
        }
    }

    @Test
    public void testIdenticalNonBattleRoyaleCardsHaveFeesOfThree() {
        int[] threeEach = {3, 3};
        for (Denomination denom : new Denomination[] { TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN }) {
            assertArrayEquals("Two " + denom + "s = 3 fee cards per player.", 
                    threeEach, determineFees(denom, denom));
        }
    }

    @Test
    public void testOneOffCardsResultInNoWinner() {
        assertNull(pickWinner(TWO, THREE));
        assertNull(pickWinner(THREE, FOUR));
        assertNull(pickWinner(FOUR, FIVE));
        assertNull(pickWinner(FIVE, SIX));
        assertNull(pickWinner(SIX, SEVEN));
        assertNull(pickWinner(SEVEN, EIGHT));
        assertNull(pickWinner(EIGHT, NINE));
        assertNull(pickWinner(NINE, TEN));
        assertNull(pickWinner(TEN, JACK));

        assertNull(pickWinner(THREE, TWO));
        assertNull(pickWinner(FOUR, THREE));
        assertNull(pickWinner(FIVE, FOUR));
        assertNull(pickWinner(SIX, FIVE));
        assertNull(pickWinner(SEVEN, SIX));
        assertNull(pickWinner(EIGHT, SEVEN));
        assertNull(pickWinner(NINE, EIGHT));
        assertNull(pickWinner(TEN, NINE));
        assertNull(pickWinner(JACK, TEN));
    }
    
    @Test
    public void testOneOffCardsHaveFeesOfTwo() {
        assertArrayEquals(expectedFees(2, 2), determineFees(TWO, THREE));
        assertArrayEquals(expectedFees(2, 2), determineFees(THREE, FOUR));
        assertArrayEquals(expectedFees(2, 2), determineFees(FOUR, FIVE));
        assertArrayEquals(expectedFees(2, 2), determineFees(FIVE, SIX));
        assertArrayEquals(expectedFees(2, 2), determineFees(SIX, SEVEN));
        assertArrayEquals(expectedFees(2, 2), determineFees(SEVEN, EIGHT));
        assertArrayEquals(expectedFees(2, 2), determineFees(EIGHT, NINE));
        assertArrayEquals(expectedFees(2, 2), determineFees(NINE, TEN));
        assertArrayEquals(expectedFees(2, 2), determineFees(TEN, JACK));

        assertArrayEquals(expectedFees(2, 2), determineFees(THREE, TWO));
        assertArrayEquals(expectedFees(2, 2), determineFees(FOUR, THREE));
        assertArrayEquals(expectedFees(2, 2), determineFees(FIVE, FOUR));
        assertArrayEquals(expectedFees(2, 2), determineFees(SIX, FIVE));
        assertArrayEquals(expectedFees(2, 2), determineFees(SEVEN, SIX));
        assertArrayEquals(expectedFees(2, 2), determineFees(EIGHT, SEVEN));
        assertArrayEquals(expectedFees(2, 2), determineFees(NINE, EIGHT));
        assertArrayEquals(expectedFees(2, 2), determineFees(TEN, NINE));
        assertArrayEquals(expectedFees(2, 2), determineFees(JACK, TEN));
    }

    @Test
    public void testTwoOffCardsResultInNoWinner() {
        assertNull(pickWinner(TWO, FOUR));
        assertNull(pickWinner(THREE, FIVE));
        assertNull(pickWinner(FOUR, SIX));
        assertNull(pickWinner(FIVE, SEVEN));
        assertNull(pickWinner(SIX, EIGHT));
        assertNull(pickWinner(SEVEN, NINE));
        assertNull(pickWinner(EIGHT, TEN));
        assertNull(pickWinner(NINE, JACK));
        assertNull(pickWinner(TEN, QUEEN));

        assertNull(pickWinner(FOUR, TWO));
        assertNull(pickWinner(FIVE, THREE));
        assertNull(pickWinner(SIX, FOUR));
        assertNull(pickWinner(SEVEN, FIVE));
        assertNull(pickWinner(EIGHT, SIX));
        assertNull(pickWinner(NINE, SEVEN));
        assertNull(pickWinner(TEN, EIGHT));
        assertNull(pickWinner(JACK, NINE));
        assertNull(pickWinner(QUEEN, TEN));
    }
    
    @Test
    public void testTwoOffCardsHaveFeesOfOne() {
        assertArrayEquals(expectedFees(1, 1), determineFees(TWO, FOUR));
        assertArrayEquals(expectedFees(1, 1), determineFees(THREE, FIVE));
        assertArrayEquals(expectedFees(1, 1), determineFees(FOUR, SIX));
        assertArrayEquals(expectedFees(1, 1), determineFees(FIVE, SEVEN));
        assertArrayEquals(expectedFees(1, 1), determineFees(SIX, EIGHT));
        assertArrayEquals(expectedFees(1, 1), determineFees(SEVEN, NINE));
        assertArrayEquals(expectedFees(1, 1), determineFees(EIGHT, TEN));
        assertArrayEquals(expectedFees(1, 1), determineFees(NINE, JACK));
        assertArrayEquals(expectedFees(1, 1), determineFees(TEN, QUEEN));

        assertArrayEquals(expectedFees(1, 1), determineFees(FOUR, TWO));
        assertArrayEquals(expectedFees(1, 1), determineFees(FIVE, THREE));
        assertArrayEquals(expectedFees(1, 1), determineFees(SIX, FOUR));
        assertArrayEquals(expectedFees(1, 1), determineFees(SEVEN, FIVE));
        assertArrayEquals(expectedFees(1, 1), determineFees(EIGHT, SIX));
        assertArrayEquals(expectedFees(1, 1), determineFees(NINE, SEVEN));
        assertArrayEquals(expectedFees(1, 1), determineFees(TEN, EIGHT));
        assertArrayEquals(expectedFees(1, 1), determineFees(JACK, NINE));
        assertArrayEquals(expectedFees(1, 1), determineFees(QUEEN, TEN));
    }

    @Test
    public void testNoWrappingAroundForDiffsOfTwo() {
        assertEquals(KING, pickWinner(KING, TWO));
        assertEquals(KING, pickWinner(TWO, KING));
        assertEquals(ACE, pickWinner(ACE, THREE));
        assertEquals(ACE, pickWinner(THREE, ACE));
    }

    @Test
    public void testMoreThanTwoDiffMeansAWinner() {
        assertEquals(FIVE, pickWinner(FIVE, TWO));
        assertEquals(SIX, pickWinner(SIX, THREE));
        assertEquals(SIX, pickWinner(SIX, TWO));
        assertEquals(SEVEN, pickWinner(SEVEN, FOUR));
        assertEquals(SEVEN, pickWinner(SEVEN, THREE));
        assertEquals(SEVEN, pickWinner(SEVEN, TWO));
        assertEquals(EIGHT, pickWinner(EIGHT, FIVE));
        assertEquals(EIGHT, pickWinner(EIGHT, FOUR));
        assertEquals(EIGHT, pickWinner(EIGHT, THREE));
        assertEquals(EIGHT, pickWinner(EIGHT, TWO));
        assertEquals(NINE, pickWinner(NINE, SIX));
        assertEquals(NINE, pickWinner(NINE, FIVE));
        assertEquals(NINE, pickWinner(NINE, FOUR));
        assertEquals(NINE, pickWinner(NINE, THREE));
        assertEquals(NINE, pickWinner(NINE, TWO));
        assertEquals(TEN, pickWinner(TEN, SEVEN));
        assertEquals(TEN, pickWinner(TEN, SIX));
        assertEquals(TEN, pickWinner(TEN, FIVE));
        assertEquals(TEN, pickWinner(TEN, FOUR));
        assertEquals(TEN, pickWinner(TEN, THREE));
        assertEquals(TEN, pickWinner(TEN, TWO));
        assertEquals(JACK, pickWinner(JACK, EIGHT));
        assertEquals(JACK, pickWinner(JACK, SEVEN));
        assertEquals(JACK, pickWinner(JACK, SIX));
        assertEquals(JACK, pickWinner(JACK, FIVE));
        assertEquals(JACK, pickWinner(JACK, FOUR));
        assertEquals(JACK, pickWinner(JACK, THREE));
        assertEquals(JACK, pickWinner(JACK, TWO));
        assertEquals(QUEEN, pickWinner(QUEEN, NINE));
        assertEquals(QUEEN, pickWinner(QUEEN, EIGHT));
        assertEquals(QUEEN, pickWinner(QUEEN, SEVEN));
        assertEquals(QUEEN, pickWinner(QUEEN, SIX));
        assertEquals(QUEEN, pickWinner(QUEEN, FIVE));
        assertEquals(QUEEN, pickWinner(QUEEN, FOUR));
        assertEquals(QUEEN, pickWinner(QUEEN, THREE));
        assertEquals(QUEEN, pickWinner(QUEEN, TWO));
        assertEquals(KING, pickWinner(KING, TEN));
        assertEquals(KING, pickWinner(KING, NINE));
        assertEquals(KING, pickWinner(KING, EIGHT));
        assertEquals(KING, pickWinner(KING, SEVEN));
        assertEquals(KING, pickWinner(KING, SIX));
        assertEquals(KING, pickWinner(KING, FIVE));
        assertEquals(KING, pickWinner(KING, FOUR));
        assertEquals(KING, pickWinner(KING, THREE));
        assertEquals(KING, pickWinner(KING, TWO));
    }
    
    @Test
    public void testDefinitiveWinnerDoesNotBlowUpFeesMethod() {
        determineFees(KING, SIX);
        determineFees(THREE, SEVEN);
    }
}
