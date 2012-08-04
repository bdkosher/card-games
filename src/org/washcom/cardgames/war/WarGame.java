package org.washcom.cardgames.war;

import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.washcom.cardgames.core.*;

/**
 *
 * @author Joe
 */
public class WarGame {

    /**
     * The number of cards which each player plays in the event their played cards match.
     */
    public static final int WAR_FEE = 3;
    
    private final Player player1;
    private final Player player2;
    private final Comparator<Card> chooser = new AcesHighCardComparator();
    
    private boolean gameOver = false;

    public WarGame() {
        this(DeckBuilder.buildShuffled52CardDeck());
    }

    public WarGame(Deck deck) {
        this(new Player("1"), new Player("2"), deck);
    }

    public WarGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public WarGame(Player player1, Player player2, Deck deckToDistribute) {
        this.player1 = player1;
        this.player2 = player2;
        DeckDealer.dealEntirely(deckToDistribute, player1, player2);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    void battle(Deck pot, int fee) {
        int cardsNeededToBattle = fee + 1;
        if (!player1.handHasAtLeast(cardsNeededToBattle)) {
            pot.put(player1.getHand().drawAll());
            gameOver = true;
        }
        if (!player2.handHasAtLeast(cardsNeededToBattle)) {
            pot.put(player2.getHand().drawAll());
            gameOver = true;
        }
        
        if (gameOver) return;

        pot.put(player1.getHand().draw(fee));
        pot.put(player2.getHand().draw(fee));

        Card card1 = player1.getHand().draw();
        Card card2 = player2.getHand().draw();
        pot.put(card1);
        pot.put(card2);
        int result = chooser.compare(card1, card2);
        if (result < 0) {
            player2.getHand().putOnBottom(pot.drawAll());
        } else if (result > 0) {
            player1.getHand().putOnBottom(pot.drawAll());
        } else {
            battle(pot, WAR_FEE);
        }
    }

    public int play() {
        int totalNbrOfBattles = 0;
        while (!isGameOver()) {
            ++totalNbrOfBattles;
            battle(new Deck(), 0);
            if (totalNbrOfBattles % 500 == 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
                System.out.println("\n\t\t1: " + player1.getHand().size() + " 2: " + player2.getHand().size());
                System.out.flush();
            }
        }
        return totalNbrOfBattles;
    }
}
