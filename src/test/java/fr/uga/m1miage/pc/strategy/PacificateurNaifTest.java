package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class PacificateurNaifTest {
    Strategy strategy = new PacificateurNaif();
    
    @Test
    void testFirstMoveIsRandom() {
        // Premier coup: s'assurer que la strat?gie joue au hasard si aucun coup pr?c?dent
        assertTrue("c,t".contains(strategy.playNextMove(null, null)));
    }

    @Test
    void testMimickingOpponentCooperation() {
        List<String> myMoves = Arrays.asList("c");
        List<String> opponentMoves = Arrays.asList("c");
        
        // Si l'adversaire coop?re, le PacificateurNaif devrait coop?rer
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }

    @Test
    void testMimickingOpponentBetrayal() {
        List<String> myMoves = Arrays.asList("c", "c");
        List<String> opponentMoves = Arrays.asList("c", "t");
        
        // Si l'adversaire trahit, le PacificateurNaif devrait g?n?ralement imiter
        assertEquals("t", strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testChangeBehaviorOnBetrayal() {
        List<String> myMoves = Arrays.asList("t", "c");
        List<String> opponentMoves = Arrays.asList("c", "t");

        // Le PacificateurNaif a une faible probabilit? de coop?rer malgr? la trahison de l'adversaire
        String nextMove = strategy.playNextMove(myMoves, opponentMoves);
        assertTrue("c,t".contains(nextMove));
    }

    @Test
    void testAlwaysImitatesIfNoConditionToChangeBehavior() {
        List<String> myMoves = Arrays.asList("c", "t", "c");
        List<String> opponentMoves = Arrays.asList("t", "c", "c");

        // Si l'adversaire coop?re, le PacificateurNaif devrait continuer ? coop?rer sans changer de comportement
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }
}

