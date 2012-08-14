package org.washcom.cardgames.battleroyale;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joe
 */
public class Battle {

    public static final int MAXIMUM_BATTLE_CONTINUATIONS = 3;

    public enum Status {

        IN_PROGRESS,
        WON,
        UNRESOLVED
    }
    private final int number;
    private final BattleRoyaleGame game;
    
    private final List<BattleCard> battleCards = new ArrayList<>();
    private int continuations = 0;
    private Status status = Status.IN_PROGRESS;

    /**
     * Creates a new battle.
     *
     * @param number
     * @param game
     */
    public Battle(int number, BattleRoyaleGame game) {
        if (game == null) {
            throw new IllegalArgumentException("Game arg cannot be null.");
        }
        if (number < 1) {
            throw new IllegalArgumentException("Round number must be greater than 0");
        }
        this.number = number;
        this.game = game;
    }

    /**
     * Returns the round number.
     *
     * @return
     */
    public int getNumber() {
        return number;
    }

    public BattleRoyaleGame getGame() {
        return game;
    }

    public List<BattleCard> getBattleCards() {
        return battleCards;
    }
    
    public boolean isBeingFought() {
        return status == Status.IN_PROGRESS;
    }

    /**
     * In the event the battle cards are inconclusive, this method continues the battle. The battle cards played are added to the
     * game deck. If the continuations exceed the maximum, the battle will be marked as over and.
     */
    public void continueBattle() {
        if (status != Status.IN_PROGRESS) {
            throw new IllegalStateException("The battle is over.");
        }
        for (BattleCard battleCard : battleCards) {
            game.getGameCards().put(battleCard.getCard());
        }
        battleCards.clear();
        continuations++;
        if (continuations == MAXIMUM_BATTLE_CONTINUATIONS) {
            status = Status.UNRESOLVED;
        }
    }

    public void skirmish(List<Player> battlers) {
        if (status != Status.IN_PROGRESS) {
            throw new IllegalStateException("The battle is over.");
        }
        for (Player battler : battlers) {
            if (battler.handHasAtLeast(1)) {
                battleCards.add(new BattleCard(battler.getHand().draw(), battler));
            }
        }
    }

}
