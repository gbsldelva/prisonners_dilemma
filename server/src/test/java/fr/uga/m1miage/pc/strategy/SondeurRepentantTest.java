package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;
import org.junit.jupiter.api.Test;

class SondeurRepentantTest {
    Strategy strategy = new SondeurRepentant();
    
    @Test
    void testInitialCooperation() {
        assertEquals(Decision.COOPERATE, strategy.playNextMove(null, null));
    }
    
    @Test
    void testImitatingOpponentMove() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);
        
        assertEquals(Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testRepentanceAfterOpponentRetaliation() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY);
        
        assertEquals(Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testRandomBetrayal() {
        // This test checks that either "c" or "t" can be chosen in cases of random betrayal
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);
        
        assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(myMoves, opponentMoves)));
    }
}
