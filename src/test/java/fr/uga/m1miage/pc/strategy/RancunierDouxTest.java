package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class RancunierDouxTest {
    Strategy strategy = new RancunierDoux();
    
    @Test
    void testInitialCooperation() {
        // Premier coup : la stratégie coopère
        assertEquals("c", strategy.playNextMove(null, null));
    }
    
    @Test
    void testCooperatesIfOpponentCooperates() {
        List<String> myMoves = Arrays.asList("c", "c", "c");
        List<String> opponentMoves = Arrays.asList("c", "c", "c");

        // Coopération continue tant que l'adversaire coopère
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testStartsPunishmentAfterBetrayal() {
        List<String> myMoves = Arrays.asList("c", "c", "c");
        List<String> opponentMoves = Arrays.asList("c", "c", "t");

        // La stratégie commence la séquence punitive
        assertEquals("t", strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testResumesCooperationAfterPunishment() {
        List<String> myMoves = Arrays.asList("c", "t", "t", "t", "t", "c", "c");
        List<String> opponentMoves = Arrays.asList("c", "c", "t", "t", "t", "t", "c");

        // Après la séquence punitive, la stratégie coopère de nouveau
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testRestartsPunishmentIfBetrayedAgain() {
        List<String> myMoves = Arrays.asList("c", "t", "t", "t", "t", "c", "c", "c");
        List<String> opponentMoves = Arrays.asList("c", "c", "t", "t", "t", "t", "c", "t");

        // Si l'adversaire trahit à nouveau, la stratégie recommence la séquence punitive
        assertEquals("t", strategy.playNextMove(myMoves, opponentMoves));
    }
}
