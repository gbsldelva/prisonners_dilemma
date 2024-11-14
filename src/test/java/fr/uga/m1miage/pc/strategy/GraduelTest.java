package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class GraduelTest {
    Strategy strategy = new Graduel();
    
    @Test
    void testInitialCooperation() {
        // Premier coup : la strat�gie coop�re
        assertEquals("c", strategy.playNextMove(null, null));
    }

    @Test
    void testContinuesCooperatingIfNoBetrayal() {
        List<String> myMoves = Arrays.asList("c", "c", "c");
        List<String> opponentMoves = Arrays.asList("c", "c", "c");

        // Coop�ration continue si l'adversaire n'a pas trahi
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }

    @Test
    void testBetraysEqualToOpponentBetrayals() {
        List<String> myMoves = Arrays.asList("c", "c");
        List<String> opponentMoves = Arrays.asList("c", "t", "c", "t");

        // L'adversaire a trahi deux fois, la strat�gie doit trahir deux fois en r�ponse
        assertEquals("t", strategy.playNextMove(myMoves, opponentMoves));
    }

    @Test
    void testReturnsToCooperationAfterBetraying() {
        List<String> myMoves = Arrays.asList("c", "t", "t");
        List<String> opponentMoves = Arrays.asList("c", "t", "c", "t");

        // Apr�s avoir trahi autant de fois que l'adversaire, la strat�gie revient � la coop�ration
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }

    @Test
    void testCooperatesTwiceAfterRetaliation() {
        List<String> myMoves = Arrays.asList("c", "t", "t", "c");
        List<String> opponentMoves = Arrays.asList("c", "t", "t", "c");

        // Apr�s deux trahisons, la strat�gie coop�re deux fois cons�cutives
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testMixedBehaviorWithMultipleBetrayals() {
        List<String> myMoves = Arrays.asList("c", "c", "t", "t", "c", "c");
        List<String> opponentMoves = Arrays.asList("c", "t", "t", "t", "c", "c");

        // Avec plusieurs trahisons de l'adversaire, la strat�gie suit le sch�ma graduel
        assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
    }
}
