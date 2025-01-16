package fr.uga.m1miage.pc.domain.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.utils.UtilFunctions;
import org.junit.jupiter.api.Test;

class PavlovTest {
	Strategy strategy = new Pavlov();
	
	@Test
	void testRepeatingMoveCase1() {
		List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);
		List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);
		
		assertEquals(Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
	}
	
	@Test
	void testRepeatingMoveCase2() {
		List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY);
		List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);
		
		assertEquals(Decision.BETRAY, strategy.playNextMove(myMoves, opponentMoves));
	}
	
	@Test
	void testChangeMoveCase1() {
		List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY);
		List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY);
		
		assertEquals(Decision.COOPERATE, strategy.playNextMove(myMoves, opponentMoves));
	}
	
	@Test
	void testChangeMoveCase2() {
		List<Decision> myMoves = Arrays.asList(Decision.COOPERATE, Decision.COOPERATE);
		List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY);
		
		assertEquals(Decision.BETRAY, strategy.playNextMove(myMoves, opponentMoves));
	}

	@Test
	void otherCases() {
		List<Decision> opponentMoves = Arrays.asList(Decision.BETRAY, Decision.COOPERATE);
		assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, null)));
		assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, opponentMoves)));
	}
	
}
