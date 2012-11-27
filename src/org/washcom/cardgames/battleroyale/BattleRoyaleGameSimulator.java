package org.washcom.cardgames.battleroyale;

import java.util.logging.Logger;
import org.washcom.cardgames.core.DeckBuilder;

/**
 *
 * @author Joe
 */
public class BattleRoyaleGameSimulator {
    
    private static final Logger log = Logger.getLogger(BattleRoyaleGameSimulator.class.getName());
    
    public static void main(String[] args) {
        BattleRoyaleGame game = new BattleRoyaleGame();
        game.play(DeckBuilder.buildShuffled52CardDeck(), new Player("Joe"), new Player("Patrick"), new Player("Nolan"));
        Player winner = game.getWinner();
        if (winner != null) {
            log.info(winner + " won the game after " + game.getRoundsPlayed() + " rounds played.");
        } else {
            log.info("Nobody won after " + game.getRoundsPlayed() + " rounds?!");
        }
    }
    
}
