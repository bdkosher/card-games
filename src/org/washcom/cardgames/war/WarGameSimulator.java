package org.washcom.cardgames.war;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Joe
 */
public class WarGameSimulator {
    
    private final List<Integer> gameLengths = new ArrayList<>();
    
    public WarGameSimulator(int totalGamesToPlay) {
        for (int i = 0; i < totalGamesToPlay; ++i) {
            System.out.print("Starting game ");
            int length = new WarGame().play();
            System.out.println("\n\tGame completed! " + length + " battles.");
            gameLengths.add(length);
        }
    }
    
    int getLongestGameLength() {
        return Collections.min(gameLengths);
    }
    
    int getShortestGameLength() {
        return Collections.max(gameLengths);
    }
    
    double getAverageGameLength() {
        int total = 0;
        for (Integer gameLength : gameLengths) {
            total += gameLength;
        }
        return (double)total / (double)gameLengths.size();
    }
    
    public static void main(String[] args) {
        WarGameSimulator simulator = new WarGameSimulator(100);
        System.out.println("Shortest game length: " + simulator.getShortestGameLength());
        System.out.println("Longest game length: " + simulator.getLongestGameLength());
        System.out.println("Average game length: " + simulator.getAverageGameLength());
    }
}
