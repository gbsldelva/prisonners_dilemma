package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class AdaptatifTest {
    Strategy strategy = new Adaptatif();
    
    @Test
    void testInitialSequence() {
        // Tester que les premiers coups suivent la séquence initiale : c,c,c,c,c,c,t,t,t,t,t
        List<String> myMoves = Arrays.asList();
        List<String> opponentMoves = Arrays.asList();

        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
        assertEquals("t", strategy.playNextMove(myMoves, opponentMoves));
        assertEquals("t", strategy.playNextMove(myMoves, opponentMoves));
        assertEquals("t", strategy.playNextMove(myMoves, opponentMoves));
        assertEquals("t", strategy.playNextMove(myMoves, opponentMoves));
        assertEquals("t", strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testTieInScores() {
        List<String> myMoves = Arrays.asList("c", "c", "t", "t");
        List<String> opponentMoves = Arrays.asList("c", "t", "t", "c");

        // La stratégie devrait choisir aléatoirement si les scores sont égaux
        Adaptatif adaptatifStrategy = (Adaptatif) strategy;
        for (int i = 0; i < 4; i++) {
            adaptatifStrategy.updateScores(myMoves.get(i), opponentMoves.get(i));
        }

        // Teste un choix aléatoire entre c et t si les scores sont égaux
        String nextMove = strategy.playNextMove(myMoves, opponentMoves);
        assertTrue(nextMove.equals("c") || nextMove.equals("t"));
    }
}
