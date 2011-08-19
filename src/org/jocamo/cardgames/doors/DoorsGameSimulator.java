package org.jocamo.cardgames.doors;

import org.jocamo.cardgames.core.DeckBuilder;
import org.jocamo.cardgames.core.Deck;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import org.jocamo.cardgames.doors.DoorsGame.Result;

/**
 * 1,000,000 games using the DenominationValuePolicy = 14.2% success rate, inv avg = 0.142857
 * 1,000,000 games using the FixedRoyaltyPolicy(10) = 15.2% success rate, inv avg = 0.153
 * 1,000,000 games using the FixedRoyaltyPolicy(1) = 22.2% success rate, inv avg = 0.224138
 * 1,000,000 games using the ColorValuePolicy(2,1) = 66.6% success rate, inv avg = 0.666667
 * 1,000,000 games using the ColorValuePolicy(5,3) = 26.2% success rate
 * @author Joe
 */
public class DoorsGameSimulator {

    private int gamesPlayed;
    private int gamesWon;

    public DoorsGameSimulator(int totalGamesToPlay, CardValuePolicy policy) {
        for (int i = 0; i < totalGamesToPlay; ++i) {
            Deck deck = DeckBuilder.build52CardDeck();
            deck.shuffle();
            DoorsGame game = new DoorsGame(deck, policy);
            while (game.playRound()) {
//                System.out.println("ROUND " + game.getRound() + ":" + game.getTopCard() + "; "
//                        + game.getTopCardValue() + " to be drawn from remaining " + game.getDeck().size() + " cards.");
            }
//            System.out.println("GAME OVER: " + game.getResult());
            gamesWon = gamesWon + (game.getResult() == Result.WIN ? 1 : 0);
            ++gamesPlayed;
        }
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public static void main(String[] args) {
        int games = 1000000;
        CardValuePolicy policy = new ColorValuePolicy(7, 11);
        DoorsGameSimulator sim = new DoorsGameSimulator(games, policy);
        System.out.println("Won " + sim.getGamesWon() + " out of " + sim.getGamesPlayed() + " games.");
        System.out.println("Empirical win ratio: " + new BigDecimal(sim.getGamesWon()).divide(new BigDecimal(sim.getGamesPlayed())).floatValue());
        System.out.println("Actual win ratio?: " + getInverseOfAvgCardValue(policy));
    }

    static float getInverseOfAvgCardValue(CardValuePolicy policy) {
        Deck deck = DeckBuilder.build52CardDeck();
        int size = deck.size();
        int totalValue = 0;
        for (int i = 0; i < size; ++i) {
            totalValue += policy.getValue(deck.draw());
        }
        return BigDecimal.ONE.divide(new BigDecimal(totalValue).divide(new BigDecimal(size), new MathContext(8, RoundingMode.HALF_UP)), new MathContext(6, RoundingMode.HALF_UP)).floatValue();
    }
}
