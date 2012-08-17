package org.washcom.cardgames.battleroyale;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.washcom.cardgames.core.DeckBuilder;

/**
 *
 * @author Joe
 */
public class BattleRoyaleGameTest {

    @Test
    public void testHookup() {
        assertTrue(true);
    }

    @Test
    public void testFirstPlayerAlwaysWins() {
        BattleRoyaleGame game = new BattleRoyaleGame(new BattleAssessor() {

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
        });
        game.play(DeckBuilder.buildShuffled52CardDeck(), new Player("1"), new Player("2"), new Player("3"));
        assertEquals(new Player("1"), game.getWinner());
    }

    @Test
    public void testFirstPlayerWinsOnFirstContinuationNoFees() {
        BattleRoyaleGame game = new BattleRoyaleGame(new BattleAssessor() {

            @Override
            public BattleCard pickWinner(Battle battle) {
                if (battle.getContinuations() == 0) {
                    return null;
                }
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
        });
        game.play(DeckBuilder.buildShuffled52CardDeck(), new Player("1"), new Player("2"), new Player("3"));
        assertEquals(new Player("1"), game.getWinner());
    }
    
    @Test
    public void testFirstPlayerWinsOnFirstContinuationFeeOf1() {
        BattleRoyaleGame game = new BattleRoyaleGame(new BattleAssessor() {

            @Override
            public BattleCard pickWinner(Battle battle) {
                if (battle.getContinuations() == 0) {
                    return null;
                }
                return battle.getBattleCards().get(0);
            }

            @Override
            public Map<Player, Integer> determineFees(Battle battle) {
                return new HashMap<Player, Integer>() {

                    {
                        put(new Player("1"), 1);
                        put(new Player("2"), 1);
                        put(new Player("3"), 1);
                    }
                };
            }
        });
        game.play(DeckBuilder.buildShuffled52CardDeck(), new Player("1"), new Player("2"), new Player("3"));
        assertEquals(new Player("1"), game.getWinner());
    }
}
