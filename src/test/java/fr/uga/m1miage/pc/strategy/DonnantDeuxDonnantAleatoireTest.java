package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class DonnantDeuxDonnantAleatoireTest {
    Strategy strategy = new DonnantDeuxDonnantAleatoire();

    @Test
    void testPlayLikeOpponentOrRandom() {
        List<String> myMoves = Arrays.asList();
        List<String> opponentMoves = Arrays.asList("c", "c");

        // Tester que la stratégie suit le dernier coup de l'adversaire
        String move = strategy.playNextMove(myMoves, opponentMoves);
        assertTrue("ct".contains(move));  // Doit être "c" ou "t"

        // Tester que la stratégie joue parfois un coup aléatoire
        myMoves = Arrays.asList("c");
        opponentMoves = Arrays.asList("c", "c");

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
