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
public class ThreeBattlerAssessorTest {

    ThreeBattlerAssessor assessor = new ThreeBattlerAssessor();

    Denomination pickWinner(Denomination one, Denomination two, Denomination three) {
        BattleCard battleCard = assessor.pickWinner(battle(one, two, three));
        return battleCard == null ? null : battleCard.getCard().getDenomination();
    }

    Map<Player, Integer> determineFees(Battle battle) {
        return assessor.determineFees(battle);
    }

    Integer[] determineFees(Denomination one, Denomination two, Denomination three) {
        Map<Player, Integer> fees = assessor.determineFees(battle(one, two, three));
        Integer fee1 = fees.get(player(1));
        Integer fee2 = fees.get(player(2));
        Integer fee3 = fees.get(player(3));
        return new Integer[]{fee1, fee2, fee3};
    }

    Integer[] expectedFees(Integer one, Integer two, Integer three) {
        return new Integer[]{one, two, three};
    }

    Player player(int playerId) {
        return new Player("Player " + playerId);
    }

    BattleCard bc(Denomination denom, int playerId) {
        return new BattleCard(new Card(denom, Suit.values()[new Random().nextInt(Suit.values().length)]),
                player(playerId));
    }

    Battle battle(final Denomination denom1, final Denomination denom2, final Denomination denom3) {
        return new Battle(1, new BattleRoyaleGame(assessor)) {
            @Override
            public List<BattleCard> getBattleCards() {
                List<BattleCard> cards = new ArrayList<>();
                cards.add(bc(denom1, 1));
                cards.add(bc(denom2, 2));
                cards.add(bc(denom3, 3));
                return cards;
            }

            @Override
            public List<Player> getBattlers() {
                return Arrays.<Player>asList(player(1), player(2), player(3));
            }
        };
    }

    @Test
    public void testTwoWinsCleanlyOverAce() {
        assertEquals(TWO, pickWinner(TWO, ACE, FIVE));
        assertEquals(TWO, pickWinner(TWO, ACE, SIX));
        assertEquals(TWO, pickWinner(TWO, ACE, SEVEN));
        assertEquals(TWO, pickWinner(TWO, ACE, EIGHT));
        assertEquals(TWO, pickWinner(TWO, ACE, NINE));
        assertEquals(TWO, pickWinner(TWO, ACE, TEN));
    }

    @Test
    public void testBattleSituationsWithTwoAndAce() {
        assertNull("Two and two continue battle.", pickWinner(TWO, ACE, TWO));
        assertNull("Two and three continue battle (1-off).", pickWinner(TWO, ACE, THREE));
        assertNull("Two and four continue battle (2-off).", pickWinner(TWO, ACE, FOUR));
        assertNull("Ace and king battle royale.", pickWinner(TWO, ACE, KING));
        assertArrayEquals(determineFees(TWO, ACE, TWO), expectedFees(3, null, 3));
        assertArrayEquals(determineFees(TWO, ACE, THREE), expectedFees(2, null, 2));
        assertArrayEquals(determineFees(TWO, ACE, FOUR), expectedFees(1, null, 1));
    }

    @Test
    public void testTwoPlayerBattleRoyaleSituations() {
        for (Denomination denom : new Denomination[]{TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN}) {
            assertNull(pickWinner(JACK, JACK, denom));
            assertNull(pickWinner(QUEEN, JACK, denom));
            assertNull(pickWinner(KING, JACK, denom));
            assertNull(pickWinner(ACE, JACK, denom));
            assertNull(pickWinner(QUEEN, QUEEN, denom));
            assertNull(pickWinner(KING, QUEEN, denom));
            assertNull(pickWinner(ACE, QUEEN, denom));
            assertNull(pickWinner(KING, KING, denom));
            assertNull(pickWinner(ACE, KING, denom));
            assertNull(pickWinner(ACE, ACE, denom));

            assertArrayEquals(determineFees(JACK, JACK, denom), expectedFees(1, 1, null));
            assertArrayEquals(determineFees(QUEEN, JACK, denom), expectedFees(2, 1, null));
            assertArrayEquals(determineFees(KING, JACK, denom), expectedFees(3, 1, null));
            assertArrayEquals(determineFees(ACE, JACK, denom), expectedFees(4, 1, null));
            assertArrayEquals(determineFees(QUEEN, QUEEN, denom), expectedFees(2, 2, null));
            assertArrayEquals(determineFees(KING, QUEEN, denom), expectedFees(3, 2, null));
            assertArrayEquals(determineFees(ACE, QUEEN, denom), expectedFees(4, 2, null));
            assertArrayEquals(determineFees(KING, KING, denom), expectedFees(3, 3, null));
            assertArrayEquals(determineFees(ACE, KING, denom), expectedFees(4, 3, null));
            assertArrayEquals(determineFees(ACE, ACE, denom), expectedFees(4, 4, null));
        }
    }

    @Test
    public void testThreePlayerBattleRoyaleSituations() {
    }
}
