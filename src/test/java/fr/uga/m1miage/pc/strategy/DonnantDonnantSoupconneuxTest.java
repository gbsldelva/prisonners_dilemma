package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class DonnantDonnantSoupconneuxTest {
    Strategy strategy = new DonnantDonnantSoupconneux();
    
    @Test
    void testInitialMoveIsBetrayal() {
        // Premier coup : la stratégie commence par trahir
        assertEquals("t", strategy.playNextMove(null, null));
    }
    
    @Test
    void testFollowsOpponentMoveAfterFirstTurn() {
        List<String> myMoves = Arrays.asList("t");
        List<String> opponentMoves = Arrays.asList("c");
        
        // Suivre le dernier coup de l'adversaire, ici coopérer après qu'il a coopéré
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testRepeatsOpponentBetrayal() {
        List<String> myMoves = Arrays.asList("t", "c");
        List<String> opponentMoves = Arrays.asList("c", "t");
        
        // Répéter la trahison si l'adversaire a trahi au dernier coup
        assertEquals("t", strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testContinuesFollowingOpponentMoves() {
        List<String> myMoves = Arrays.asList("t", "c", "t");
        List<String> opponentMoves = Arrays.asList("c", "t", "c");

        // Continuer de copier le dernier coup de l'adversaire
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }
}
