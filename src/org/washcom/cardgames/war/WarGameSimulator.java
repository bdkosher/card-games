package org.washcom.cardgames.war;

import java.util.*;

/**
 *
 * @author Joe
 */
public class WarGameSimulator {

    private final List<Integer> gameLengths = new ArrayList<>();
    private final Map<Integer, List<Integer>> warCounts = new HashMap<>();

    public WarGameSimulator(int totalGamesToPlay) {
        for (int i = 0; i < totalGamesToPlay; ++i) {
//            System.out.print("Starting game...");
            WarGame.Result result = new WarGame().play();
//            System.out.println("completed! " + result.totalBattles + " battles.");
            gameLengths.add(result.totalBattles);
            for (Map.Entry<Integer, Integer> entry : result.totalWars.entrySet()) {
                List<Integer> targetWarCounts = warCounts.get(entry.getKey());
                if (targetWarCounts == null) {
                    targetWarCounts = new ArrayList<>();
                    warCounts.put(entry.getKey(), targetWarCounts);
                }
                targetWarCounts.add(entry.getValue());
            }
        }
    }
    
    int getTotalGamesPlayed() {
        return gameLengths.size();
    }

    int getLongestGameLength() {
        return Collections.max(gameLengths);
    }

    int getShortestGameLength() {
        return Collections.min(gameLengths);
    }

    double getAverageGameLength() {
        int total = 0;
        for (Integer gameLength : gameLengths) {
            total += gameLength;
        }
        return (double) total / (double) gameLengths.size();
    }

    int getMostWarsInGame(int nbrOfWarsPerBattle) {
        if (nbrOfWarsPerBattle < 0) {
            throw new IllegalArgumentException("Expecting a value of 0 or more.");
        }
        List<Integer> counts = warCounts.get(nbrOfWarsPerBattle);
        return counts == null ? 0 : Collections.max(counts);
    }

    double getAverageWarsPerGame(int nbrOfWarsPerBattle) {
        if (nbrOfWarsPerBattle < 0) {
            throw new IllegalArgumentException("Expecting a value of 0 or more.");
        }
        List<Integer> targetWarCounts = warCounts.get(nbrOfWarsPerBattle);
        if (targetWarCounts == null) {
            return 0d;
        }
        int total = 0;
        for (Integer warCount : targetWarCounts) {
            total += warCount;
        }
        return (double) total / (double) gameLengths.size();
    }

    public static void main(String[] args) {
        WarGameSimulator simulator = new WarGameSimulator(10000);
        System.out.println("Total games played: " + simulator.getTotalGamesPlayed());
        System.out.println("Shortest game length: " + simulator.getShortestGameLength());
        System.out.println("Longest game length: " + simulator.getLongestGameLength());
        System.out.println("Average game length: " + simulator.getAverageGameLength());
        for (int i = 0; i < 7; ++i) {
            System.out.println("Most " + describeWar(i) + " in a game: " + simulator.getMostWarsInGame(i));
            System.out.println("Average " + describeWar(i) + " per game: " + simulator.getAverageWarsPerGame(i));
        }
    }

    static String describeWar(int warLength) {
        switch (warLength) {
            case 0:
                return "warless battles";
            case 1:
                return "wars";
            case 2:
                return "double wars";
            case 3:
                return "triple wars";
            case 4:
                return "quadruple wars";
            case 5:
                return "quintuple wars";
            case 6:
                return "sextuple wars";
            case 7:
                return "septuple wars";
            case 8:
                return "octuple wars";
            default:
                return "x" + warLength + " wars";
        }
    }
}
