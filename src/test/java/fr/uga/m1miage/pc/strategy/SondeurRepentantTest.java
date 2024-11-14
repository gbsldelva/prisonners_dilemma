package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class SondeurRepentantTest {
    Strategy strategy = new SondeurRepentant();
    
    @Test
    void testInitialCooperation() {
        List<String> myMoves = Arrays.asList();
        List<String> opponentMoves = Arrays.asList();
        
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testImitatingOpponentMove() {
        List<String> myMoves = Arrays.asList("c", "t");
        List<String> opponentMoves = Arrays.asList("c", "c");
        
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testRepentanceAfterOpponentRetaliation() {
        List<String> myMoves = Arrays.asList("c", "t");
        List<String> opponentMoves = Arrays.asList("c", "t");
        
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testRandomBetrayal() {
        // This test checks that either "c" or "t" can be chosen in cases of random betrayal
        List<String> myMoves = Arrays.asList("c", "c");
        List<String> opponentMoves = Arrays.asList("c", "c");
        
        assertTrue("c,t".contains(strategy.playNextMove(myMoves, opponentMoves)));
    }
}
