package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;
import org.junit.jupiter.api.Test;

class VraiPacificateurTest {
    Strategy strategy = new VraiPacificateur();
    
    @Test
    void testCooperateInitially() {
        assertEquals(Decision.COOPERATE, strategy.playNextMove(null, null));
    }
    
    @Test
    void testCooperateIfNoConsecutiveBetrayals() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.COOPERATE);

        // Si l'adversaire n'a pas trahi deux fois de suite, le VraiPacificateur devrait coop�rer
        assertEquals(Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testAttemptToMakePeaceAfterDoubleBetrayal() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.BETRAY, Decision.BETRAY);

        // Le VraiPacificateur peut parfois coop�rer m�me apr�s une double trahison pour essayer d'apaiser
        Decision nextMove = strategy.playNextMove(myMoves, opponentMoves);
        assertTrue(UtilFunctions.choices.contains(nextMove));
    }

    @Test
    void testAlwaysCooperateIfOpponentNeverBetraysTwiceInRow() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.COOPERATE);

        // Si l'adversaire n'a jamais trahi deux fois de suite, le VraiPacificateur devrait continuer � coop�rer
        assertEquals(Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
    }

    @Test
    void otherCases() {
        List<Decision> opponentMoves = Arrays.asList(Decision.BETRAY, Decision.COOPERATE);
        assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, null)));
        assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, opponentMoves)));
    }
}