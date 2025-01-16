package fr.uga.m1miage.pc.domain.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.utils.UtilFunctions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class DonnantDonnantAleatoireTest {
    Strategy strategy = new DonnantDonnantAleatoire();

    @Test
    void testPlayLikeOpponentOrRandom() {
        // Tester qu'il y a une chance de choisir al�atoirement
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);

        boolean randomMoveMade = false;
        for (int i = 0; i < 100; i++) {
            Decision move = strategy.playNextMove(myMoves, opponentMoves);
            if (!move.equals(opponentMoves.get(opponentMoves.size() - 1))) {
                randomMoveMade = true;
                break;
            }
        }

        assertTrue(randomMoveMade, "Il devrait y avoir une chance que le coup soit al�atoire");
    }

    @Test
    void otherCases() {
        List<Decision> opponentMoves = Arrays.asList(Decision.BETRAY, Decision.COOPERATE);
        assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, null)));
        assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, opponentMoves)));
    }
}
