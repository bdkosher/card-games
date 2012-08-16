package org.washcom.cardgames.battleroyale;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joe
 */
public class Battle {

    public static final int MAXIMUM_BATTLE_CONTINUATIONS = 3;

    private final int number;
    private final BattleRoyaleGame game;
    private final List<BattleCard> battleCards = new ArrayList<>();
    private final List<Player> battlers;
    private int continuations = 0;
    
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
        this.battlers = game.getActivePlayers();
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

    /**
     * Does a skirmish: collects a single card from each active player. Returns the winning player or null if there is no winning
     * player.
     *
     * @return
     */
    public void fight(BattleAssessor assessor) {
        for (; continuations < MAXIMUM_BATTLE_CONTINUATIONS; ++continuations) {
            playBattleCards();
            BattleCard winner = assessor.pickWinner(this);
            addBattleCardsToGamePot();
            if (winner != null) {
                spoilsToTheVictor(winner.getPlayedBy());
            } else {
                eliminateCardlessBattlers();
                Map<Player, Integer> fees = assessor.determineFees(this);
                collectBattleFees(fees);
                eliminateCardlessBattlers();
            }
        }
        // TODO unresolved battle, so any inactive player gets the spoils
    }
    
    private void playBattleCards() {
        for (Player battler : battlers) {
            battleCards.add(new BattleCard(battler.getHand().draw(), battler));
        }
    }
    
    private void spoilsToTheVictor(Player victor) {
        victor.getHand().putOnBottom(game.getGameCards().drawAll());
    }
    
    private void addBattleCardsToGamePot() {
        for (BattleCard battleCard : battleCards) {
            game.getGameCards().put(battleCard.getCard());
        }
        battleCards.clear();
    }
    
    private void eliminateCardlessBattlers() {
        for (Iterator<Player> it = battlers.iterator(); it.hasNext(); ) {
            if (it.next().getHand().isEmpty()) {
                it.remove();
            }
        }
    }
    
    /**
     * Collects fees from the battle
     * @return 
     */
    private void collectBattleFees(Map<Player, Integer> fees) {
        for (Map.Entry<Player, Integer> entry : fees.entrySet()) {
            Player battler = entry.getKey();
            int fee = entry.getValue();
            game.getGameCards().put(battler.getHand().drawUpTo(fee));
        }
    }
}
