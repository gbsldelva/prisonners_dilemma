package fr.uga.m1miage.pc.domain.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.strategy.DonnantDeuxDonnantAleatoire;
import fr.uga.m1miage.pc.domain.strategy.Strategy;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class DonnantDeuxDonnantAleatoireTest {
    Strategy strategy = new DonnantDeuxDonnantAleatoire();

    @Test
    void testPlayLikeOpponentOrRandom() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);

        boolean randomMoveMade = false;
        for (int i = 0; i < 100; i++) {
            Decision move = strategy.playNextMove(myMoves, opponentMoves);
            if (!move.equals(opponentMoves.get(opponentMoves.size() - 1))) {
                randomMoveMade = true;
                break;
            }
        }

        assertTrue(randomMoveMade, "Il devrait y avoir une chance que le coup soit aléatoire");
    }
}
