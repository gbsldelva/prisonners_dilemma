package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class DonnantDonnantAleatoireTest {
    Strategy strategy = new DonnantDonnantAleatoire();

    @Test
    void testPlayLikeOpponentOrRandom() {
        List<String> myMoves = Arrays.asList();
        List<String> opponentMoves = Arrays.asList("c", "t", "c");

        // Tester que la stratégie suit le dernier coup de l'adversaire ou choisit aléatoirement
        String move = strategy.playNextMove(myMoves, opponentMoves);
        assertTrue("ct".contains(move));  // Doit être "c" ou "t"

        // Tester qu'il y a une chance de choisir aléatoirement
        myMoves = Arrays.asList("c");
        opponentMoves = Arrays.asList("c");

        boolean randomMoveMade = false;
        for (int i = 0; i < 100; i++) {
            move = strategy.playNextMove(myMoves, opponentMoves);
            if (!move.equals(opponentMoves.get(opponentMoves.size() - 1))) {
                randomMoveMade = true;
                break;
            }
        }

        assertTrue(randomMoveMade, "Il devrait y avoir une chance que le coup soit aléatoire");
    }
}
