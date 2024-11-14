package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class VraiPacificateurTest {
    Strategy strategy = new VraiPacificateur();
    
    @Test
    void testCooperateInitially() {
        // Premier coup: s'assurer que la stratégie coopère par défaut
        assertEquals("c", strategy.playNextMove(null, null));
    }
    
    @Test
    void testCooperateIfNoConsecutiveBetrayals() {
        List<String> myMoves = Arrays.asList("c", "c");
        List<String> opponentMoves = Arrays.asList("c", "t", "c");

        // Si l'adversaire n'a pas trahi deux fois de suite, le VraiPacificateur devrait coopérer
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testAttemptToMakePeaceAfterDoubleBetrayal() {
        List<String> myMoves = Arrays.asList("c", "t", "c");
        List<String> opponentMoves = Arrays.asList("t", "t");

        // Le VraiPacificateur peut parfois coopérer même après une double trahison pour essayer d'apaiser
        String nextMove = strategy.playNextMove(myMoves, opponentMoves);
        assertTrue("c,t".contains(nextMove));
    }

    @Test
    void testAlwaysCooperateIfOpponentNeverBetraysTwiceInRow() {
        List<String> myMoves = Arrays.asList("c", "c", "c");
        List<String> opponentMoves = Arrays.asList("c", "t", "c");

        // Si l'adversaire n'a jamais trahi deux fois de suite, le VraiPacificateur devrait continuer à coopérer
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }
}