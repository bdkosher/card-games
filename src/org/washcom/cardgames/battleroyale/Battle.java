package org.washcom.cardgames.battleroyale;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.washcom.cardgames.core.Card;

/**
 *
 * @author Joe
 */
public class Battle {

    private static final Logger log = Logger.getLogger(Battle.class.toString());
    public static final int MAXIMUM_BATTLE_CONTINUATIONS = 3;
    private final int number;
    private final BattleRoyaleGame game;
    private final List<BattleCard> battleCards = new ArrayList<>();
    private List<Player> battlers;
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
    }

    public List<Player> getBattlers() {
        return battlers;
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
     * Returns the number of times the battle has continued beyond the initial
     * playing of battle cards.
     *
     * @return
     */
    public int getContinuations() {
        return continuations;
    }

    /**
     * Does a skirmish: collects a single card from each active player. Returns
     * the winning player or null if there is no winning player.
     *
     * @return
     */
    public void fight(BattleAssessor assessor) {
        this.battlers = game.getActivePlayers();
        Player nonBattler = null;
        for (; continuations <= MAXIMUM_BATTLE_CONTINUATIONS; ++continuations) {
            if (continuations > 0) {
                log.info("Executing continuation " + continuations + " of battle " + number);
            } else {
                log.info("Fighting battle " + number);
            }
            nonBattler = findNonBattler();
            playBattleCards();
            BattleCard winner = assessor.pickWinner(this);
            Map<Player, Integer> fees = assessor.determineFees(this);
            
            addBattleCardsToGamePot();
            if (winner != null) {
                spoilsToTheVictor(winner.getPlayedBy());
                return;
            }

            eliminateCardlessBattlers();
            if (battlers.size() == 1) {
                spoilsToTheVictor(battlers.get(0));
                return;
            }

            addFeeCardsToGamePot(fees);

            eliminateCardlessBattlers();
            if (battlers.size() == 1) {
                spoilsToTheVictor(battlers.get(0));
                return;
            }
        }
        // unresolved battle, so inactive player gets the spoils
        if (nonBattler == null) {
            throw new IllegalStateException("Man...unresolved three-way-battle");
        }
        log.info("Unresolved battle, spoils go to the non-battling battler, " + nonBattler);
        spoilsToTheVictor(nonBattler);
    }

    /**
     * The non-battler is eligible to receive all the spoils in the event of an
     * unresolvable battle. If all players are battling, or if there is more
     * than one non-battler, this method returns null.
     *
     * @return
     */
    private Player findNonBattler() {
        List<Player> allPlayers = new ArrayList<>(game.getPlayers());
        allPlayers.removeAll(battlers);
        return allPlayers.size() == 1 ? allPlayers.get(0) : null;
    }

    private void playBattleCards() {
        log.info("Playing battle cards...");
        for (Player battler : battlers) {
            Card card = battler.getHand().draw();
            battleCards.add(new BattleCard(card, battler));
            log.info("\t" + battler + " played a " + card);
        }
    }

    private void spoilsToTheVictor(Player victor) {
        log.info("Giving spoils of " + game.getGameCards().size() + " cards to " + victor);
        /* shuffling necessary in war to prevent endless fighting, perhaps necessary here, too? */
        game.getGameCards().shuffle();
        victor.getHand().putOnBottom(game.getGameCards().drawAll());
    }

    private void addBattleCardsToGamePot() {
        log.info("Adding " + battleCards.size() + " battle cards to game pot.");
        for (BattleCard battleCard : battleCards) {
            game.getGameCards().put(battleCard.getCard());
        }
        battleCards.clear();
    }

    private void eliminateCardlessBattlers() {
        for (Iterator<Player> it = battlers.iterator(); it.hasNext();) {
            Player player = it.next();
            if (player.getHand().isEmpty()) {
                it.remove();
                log.info(player + " has run out of cards.");
            }
        }
    }

    /**
     * Collects fees from the battle
     */
    private void addFeeCardsToGamePot(Map<Player, Integer> fees) {
        log.info("Adding fee cards to battle pot...");
        battlers.retainAll(fees.keySet());
        for (Map.Entry<Player, Integer> entry : fees.entrySet()) {
            Player battler = entry.getKey();
            int fee = entry.getValue();
            log.info("\t" + battler + " pays fee of " + fee);
            game.getGameCards().put(battler.getHand().drawUpTo(fee));
        }
    }
}
