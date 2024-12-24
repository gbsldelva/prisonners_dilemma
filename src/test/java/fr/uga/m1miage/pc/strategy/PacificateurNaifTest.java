package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;
import org.junit.jupiter.api.Test;

class PacificateurNaifTest {
    Strategy strategy = new PacificateurNaif();
    
    @Test
    void testFirstMoveIsRandom() {
        // Premier coup: s'assurer que la strat?gie joue au hasard si aucun coup pr?c?dent
        assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, null)));
    }

    @Test
    void testMimickingOpponentCooperation() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);
        
        // Si l'adversaire coopérer, le PacificateurNaif devrait coop?rer
        assertEquals(Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
    }

    @Test
    void testMimickingOpponentBetrayal() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY);
        
        // Si l'adversaire trahit, le PacificateurNaif devrait g?n?ralement imiter
        assertEquals(Decision.BETRAY, strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testChangeBehaviorOnBetrayal() {
        List<Decision> myMoves = Arrays.asList(Decision.BETRAY, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY);

        // Le PacificateurNaif a une faible probabilit? de coop?rer malgr? la trahison de l'adversaire
        Decision nextMove = strategy.playNextMove(myMoves, opponentMoves);
        assertTrue(UtilFunctions.choices.contains(nextMove));
    }

    @Test
    void testAlwaysImitatesIfNoConditionToChangeBehavior() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.BETRAY, Decision.COOPERATE, Decision.COOPERATE);

        // Si l'adversaire coop?re, le PacificateurNaif devrait continuer ? coop?rer sans changer de comportement
        assertEquals(Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
    }
}

