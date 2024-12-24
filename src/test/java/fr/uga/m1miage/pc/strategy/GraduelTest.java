package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;
import org.junit.jupiter.api.Test;

class GraduelTest {
    Strategy strategy = new Graduel();
    
    @Test
    void testInitialCooperation() {
        // Premier coup : la strat�gie coop�re
        assertEquals(Decision.COOPERATE, strategy.playNextMove(null, null));
    }

    @Test
    void testContinuesCooperatingIfNoBetrayal() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE);

        // Coopération continue si l'adversaire n'a pas trahi
        assertEquals(Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
    }

    @Test
    void testBetraysEqualToOpponentBetrayals() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.COOPERATE, Decision.BETRAY);

        // L'adversaire a trahi deux fois, la strat�gie doit trahir deux fois en r�ponse
        assertEquals(Decision.BETRAY, strategy.playNextMove(myMoves, opponentMoves));
    }

    @Test
    void testReturnsToCooperationAfterBetraying() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.BETRAY);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.COOPERATE, Decision.BETRAY);

        // Apr�s avoir trahi autant de fois que l'adversaire, la strat�gie revient � la coop�ration
        assertEquals(Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
    }

    @Test
    void testCooperatesTwiceAfterRetaliation() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.BETRAY, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.BETRAY, Decision.COOPERATE);

        // Apr�s deux trahisons, la strat�gie coop�re deux fois cons�cutives
        assertEquals(Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testMixedBehaviorWithMultipleBetrayals() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE, Decision.BETRAY, Decision.BETRAY, Decision.COOPERATE, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.BETRAY, Decision.BETRAY, Decision.COOPERATE, Decision.COOPERATE);

        // Avec plusieurs trahisons de l'adversaire, la strat�gie suit le sch�ma graduel
        assertEquals(Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
    }
}
