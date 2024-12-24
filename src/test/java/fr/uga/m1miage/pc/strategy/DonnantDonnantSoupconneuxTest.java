package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;
import org.junit.jupiter.api.Test;

class DonnantDonnantSoupconneuxTest {
    Strategy strategy = new DonnantDonnantSoupconneux();
    
    @Test
    void testInitialMoveIsBetrayal() {
        // Premier coup : la strat�gie commence par trahir
        assertEquals(Decision.BETRAY, strategy.playNextMove(null, null));
    }
    
    @Test
    void testFollowsOpponentMoveAfterFirstTurn() {
        List<Decision> myMoves = Arrays.asList(Decision.BETRAY, Decision.BETRAY);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);
        
        // Suivre le dernier coup de l'adversaire, ici coopérer après qu'il a coopéré
        assertEquals(Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testRepeatsOpponentBetrayal() {
        List<Decision> myMoves = Arrays.asList(Decision.BETRAY, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY);
        
        // R�p�ter la trahison si l'adversaire a trahi au dernier coup
        assertEquals(Decision.BETRAY, strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testContinuesFollowingOpponentMoves() {
        List<Decision> myMoves = Arrays.asList(Decision.BETRAY, Decision.COOPERATE, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.COOPERATE);

        // Continuer de copier le dernier coup de l'adversaire
        assertEquals( Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
    }

    @Test
    void otherCases() {
        List<Decision> opponentMoves = Arrays.asList(Decision.BETRAY, Decision.COOPERATE);
        assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, null)));
        assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, opponentMoves)));
    }

}
