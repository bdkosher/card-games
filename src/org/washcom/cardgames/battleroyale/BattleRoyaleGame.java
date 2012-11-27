package org.washcom.cardgames.battleroyale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.washcom.cardgames.core.Deck;
import org.washcom.cardgames.core.DeckDealer;

/**
 * The game class maintains the state of the game. - the players - the current round - game cards (not belonging to any particular
 * player)
 *
 * The game class also contains methods to move state, namely, to start a new round.
 *
 * @author Joe
 */
public class BattleRoyaleGame {

    private static final int MINIMUM_NUMBER_OF_PLAYERS = 2;
    private static final boolean LEFTOVER_CARDS_FROM_DEAL_GO_INTO_GAMEPOT = true;
    private final BattleAssessor assessor;
        
    private List<Player> players;
    private Deck gameCards;
    private int currentRoundNumber = 0;
    private Battle currentBattle;
    private Player winner = null;

    /**
     * Initializes a new game. The game is not started until {@code start} is called. The provided listener is responsible for
     * driving the state of the game.
     */
    public BattleRoyaleGame() {
        this(new DefaultBattleAccessor());
    }
    
    /**
     * Initializes a new game. The game is not started until {@code start} is called. The provided listener is responsible for
     * driving the state of the game.
     */
    public BattleRoyaleGame(BattleAssessor assessor) {
        if (assessor == null) {
            throw new IllegalArgumentException("BattleAssessor arg cannot be null.");
        }
        this.assessor = assessor;
    }

    private void initializeGame(Deck deck, Player... players) {
        if (deck == null) {
            throw new IllegalArgumentException("Deck cannot be null.");
        }
        if (players == null || players.length < MINIMUM_NUMBER_OF_PLAYERS) { // TODO: set 2 in config.
            throw new IllegalArgumentException("Must have at least two players.");
        }
        if (deck.size() < MINIMUM_NUMBER_OF_PLAYERS) {
            throw new IllegalArgumentException("Must have at least as many cards as minimum players.");
        }
        this.players = Arrays.asList(players);
        this.gameCards = deck;
        if (LEFTOVER_CARDS_FROM_DEAL_GO_INTO_GAMEPOT) {
            DeckDealer.dealFairly(this.gameCards, players);
        } else {
            DeckDealer.dealEntirely(this.gameCards, players);
        }
    }

    /**
     * Starts a new game of Battle Royale.
     *
     * @param deck - the cards to be used in the game
     * @param players - the players who will be playing the game
     */
    public void play(Deck deck, Player... players) {
        initializeGame(deck, players);
        /*
         * Battle as long as there are two or more players in the game.
         */
        while (getActivePlayers().size() > 1) {
            battle();
        }

        /*
         * If there's one player left standing, declare him the winner. It is possible given few enough cards and high enough max
         * battle continuations that all active players are engaged in a battle that never concludes, thus ending the game without
         * a declared winner.
         */
        if (getActivePlayers().size() == 1) {
            winner = getActivePlayers().get(0);
        }
    }

    void battle() {
        currentBattle = new Battle(++currentRoundNumber, this);
        currentBattle.fight(assessor);
    }

    /**
     * Returns the current battle.
     */
    public Battle getCurrentBattle() {
        return currentBattle;
    }

    /**
     * Returns the players playing this game.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the cards which do not belong to any player in particular.
     */
    public Deck getGameCards() {
        return gameCards;
    }

    /**
     * Returns all players who are participating in this game.
     */
    public List<Player> getActivePlayers() {
        List<Player> active = new ArrayList<>(players.size());
        for (Player player : players) {
            if (!player.getHand().isEmpty()) {
                active.add(player);
            }
        }
        return active;
    }

    /**
     * Returns the winner of the game. If the game hasn't finished, this method will return null.
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Returns the number of rounds played so far.
     */
    public int getRoundsPlayed() {
        return currentRoundNumber;
    }
}
