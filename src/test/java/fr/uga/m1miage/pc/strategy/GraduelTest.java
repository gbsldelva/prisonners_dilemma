package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class GraduelTest {
    Strategy strategy = new Graduel();
    
    @Test
    void testInitialCooperation() {
        // Premier coup : la stratégie coopère
        assertEquals("c", strategy.playNextMove(null, null));
    }

    @Test
    void testContinuesCooperatingIfNoBetrayal() {
        List<String> myMoves = Arrays.asList("c", "c", "c");
        List<String> opponentMoves = Arrays.asList("c", "c", "c");

        // Coopération continue si l'adversaire n'a pas trahi
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }

    @Test
    void testBetraysEqualToOpponentBetrayals() {
        List<String> myMoves = Arrays.asList("c", "c");
        List<String> opponentMoves = Arrays.asList("c", "t", "c", "t");

        // L'adversaire a trahi deux fois, la stratégie doit trahir deux fois en réponse
        assertEquals("t", strategy.playNextMove(myMoves, opponentMoves));
    }

    @Test
    void testReturnsToCooperationAfterBetraying() {
        List<String> myMoves = Arrays.asList("c", "t", "t");
        List<String> opponentMoves = Arrays.asList("c", "t", "c", "t");

        // Après avoir trahi autant de fois que l'adversaire, la stratégie revient à la coopération
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }

    @Test
    void testCooperatesTwiceAfterRetaliation() {
        List<String> myMoves = Arrays.asList("c", "t", "t", "c");
        List<String> opponentMoves = Arrays.asList("c", "t", "t", "c");

        // Après deux trahisons, la stratégie coopère deux fois consécutives
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testMixedBehaviorWithMultipleBetrayals() {
        List<String> myMoves = Arrays.asList("c", "c", "t", "t", "c", "c");
        List<String> opponentMoves = Arrays.asList("c", "t", "t", "t", "c", "c");

        // Avec plusieurs trahisons de l'adversaire, la stratégie suit le schéma graduel
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }
}
