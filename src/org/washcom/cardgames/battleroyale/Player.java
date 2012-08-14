package org.washcom.cardgames.battleroyale;

/**
 *
 * @author Joe
 */
public class Player extends org.washcom.cardgames.core.Player {

    private boolean participatingInBattle = true;
    private int nbrOfBattlesFought = 0;
    private int nbrOfBattlesWon = 0;

    public Player(String name) {
        super(name);
    }

    public boolean isParticipatingInBattle() {
        return participatingInBattle;
    }

    public void setParticipatingInBattle(boolean participatingInBattle) {
        this.participatingInBattle = participatingInBattle;
    }

    public int getNbrOfBattlesFought() {
        return nbrOfBattlesFought;
    }

    public void incrementNbrOfBattlesFought() {
        ++nbrOfBattlesFought;
    }

    public int getNbrOfBattlesWon() {
        return nbrOfBattlesWon;
    }

    public void incrementNbrOfBattlesWon() {
        ++nbrOfBattlesWon;
    }
}
