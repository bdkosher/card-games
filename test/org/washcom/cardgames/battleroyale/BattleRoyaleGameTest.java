package org.washcom.cardgames.battleroyale;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.washcom.cardgames.core.DeckBuilder;

/**
 *
 * @author Joe
 */
public class BattleRoyaleGameTest {

    private final BattleAssessor noFeesPlayer1Wins = new BattleAssessor() {

        @Override
        public BattleCard pickWinner(Battle battle) {
            return battle.getBattleCards().get(0);
        }

        @Override
        public Map<Player, Integer> determineFees(Battle battle) {
            return new HashMap<Player, Integer>() {

                {
                    put(new Player("1"), 0);
                    put(new Player("2"), 0);
                    put(new Player("3"), 0);
                }
            };
        }
    };

    @Test
    public void testHookup() {
        assertTrue(true);
    }

    @Test
    public void testGame() {
        BattleRoyaleGame game = new BattleRoyaleGame(noFeesPlayer1Wins);
        game.play(DeckBuilder.buildShuffled52CardDeck(), new Player("1"), new Player("2"), new Player("3"));
        assertEquals(new Player("1"), game.getWinner());
    }
}
