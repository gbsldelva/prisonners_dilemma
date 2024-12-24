package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import fr.uga.m1miage.pc.model.Decision;
import org.junit.jupiter.api.Test;

class RancunierDouxTest {
    Strategy strategy = new RancunierDoux();
    
    @Test
    void testInitialCooperation() {
        // Premier coup : la stratégie coopère
        assertEquals(Decision.COOPERATE, strategy.playNextMove(null, null));
    }
    
    @Test
    void testCooperatesIfOpponentCooperates() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE);

        // Coop�ration continue tant que l'adversaire coop�re
        assertEquals(Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testStartsPunishmentAfterBetrayal() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE, Decision.BETRAY);

        // La stratégie commence la séquence punitive
        assertEquals(Decision.BETRAY, strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testResumesCooperationAfterPunishment() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.BETRAY, Decision.BETRAY, Decision.BETRAY, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE, Decision.BETRAY, Decision.BETRAY, Decision.BETRAY, Decision.BETRAY, Decision.COOPERATE);

        // Apr�s la s�quence punitive, la strat�gie coop�re de nouveau
        assertEquals(Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
    }
    
    @Test
    void testRestartsPunishmentIfBetrayedAgain() {
        List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.BETRAY, Decision.BETRAY, Decision.BETRAY, Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE);
        List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE, Decision.BETRAY, Decision.BETRAY, Decision.BETRAY, Decision.BETRAY, Decision.COOPERATE, Decision.BETRAY);

        // Si l'adversaire trahit � nouveau, la strat�gie recommence la s�quence punitive
        assertEquals(Decision.BETRAY, strategy.playNextMove(myMoves, opponentMoves));
    }
}
