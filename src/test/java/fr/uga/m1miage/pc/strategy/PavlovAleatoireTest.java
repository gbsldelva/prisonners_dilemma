package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class PavlovAleatoireTest {
    Strategy strategy = new PavlovAleatoire();
    
    @Test
    void testInitialMoveIsRandom() {
        // Premier coup: s'assurer que la stratégie joue au hasard si aucun coup précédent
        assertTrue("c,t".contains(strategy.playNextMove(null, null)));
    }
    
    @Test
    void testRepeatsLastMoveOn3or5Points() {
        List<String> myMoves = Arrays.asList("c", "t");
        List<String> opponentMoves = Arrays.asList("c", "c");

        // Si le dernier tour a rapporté 3 ou 5 points (coopération mutuelle ici), la stratégie devrait répéter "t"
        assertEquals("t", strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testChoosesRandomlyOn3or5Points() {
        List<String> myMoves = Arrays.asList("t", "c");
        List<String> opponentMoves = Arrays.asList("c", "c");

        // En présence de 3 ou 5 points, la stratégie peut aussi faire un choix aléatoire
        String nextMove = strategy.playNextMove(myMoves, opponentMoves);
        assertTrue("c,t".contains(nextMove));
    }
    
    @Test
    void testRandomMoveOnNo3or5Points() {
        List<String> myMoves = Arrays.asList("c");
        List<String> opponentMoves = Arrays.asList("t");

        // Si le dernier coup n’a pas rapporté 3 ou 5 points, la stratégie devrait jouer un coup aléatoire
        String nextMove = strategy.playNextMove(myMoves, opponentMoves);
        assertTrue("c,t".contains(nextMove));
    }
    
    @Test
    void testRepeatsLastMoveWhenGetting5Points() {
        List<String> myMoves = Arrays.asList("c", "t");
        List<String> opponentMoves = Arrays.asList("t", "c");

        // Vérifie que la stratégie répète le dernier coup après avoir obtenu 5 points (trahison unique contre coopération)
        assertEquals("t", strategy.playNextMove(myMoves, opponentMoves));
    }
}
