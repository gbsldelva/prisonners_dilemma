package fr.uga.m1miage.pc.domain.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.utils.UtilFunctions;
import org.junit.jupiter.api.Test;

class PavlovAleatoireTest {
    Strategy strategy = new PavlovAleatoire();
    
    @Test
    void testInitialMoveIsRandom() {
        // Premier coup: s'assurer que la stratégie joue au hasard si aucun coup précédent
        assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, null)));
    }
    
    @Test
    void testRepeatsLastMoveOn3or5Points() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);

        // Si le dernier tour a rapporté 3 ou 5 points (coopération mutuelle ici), la stratégie devrait répéter "t"
        assertEquals(Decision.BETRAY, strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testChoosesRandomlyOn3or5Points() {
        List<Decision> myMoves = Arrays.asList(Decision.BETRAY, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);

        // En présence de 3 ou 5 points, la stratégie peut aussi faire un choix aléatoire
        Decision nextMove = strategy.playNextMove(myMoves, opponentMoves);
        assertTrue(UtilFunctions.choices.contains(nextMove));
    }
    
    @Test
    void testRandomMoveOnNo3or5Points() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.BETRAY, Decision.BETRAY);

        // Si le dernier coup n’a pas rapporté 3 ou 5 points, la stratégie devrait jouer un coup aléatoire
        Decision nextMove = strategy.playNextMove(myMoves, opponentMoves);
        assertTrue(UtilFunctions.choices.contains(nextMove));
    }
    
    @Test
    void testRepeatsLastMoveWhenGetting5Points() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY);
        List<Decision> opponentMoves = Arrays.asList(Decision.BETRAY, Decision.COOPERATE);

        // Vérifie que la stratégie répète le dernier coup après avoir obtenu 5 points (trahison unique contre coopération)
        assertEquals(Decision.BETRAY, strategy.playNextMove(myMoves, opponentMoves));
    }
}
