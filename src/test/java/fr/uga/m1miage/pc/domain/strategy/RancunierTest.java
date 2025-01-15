package fr.uga.m1miage.pc.domain.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.utils.UtilFunctions;
import org.junit.jupiter.api.Test;

class RancunierTest {
	Strategy strategy = new Rancunier();
	
	@Test
	void willCooperate() {
		List<Decision> opponentMoves = Arrays.asList(null, Decision.COOPERATE, Decision.COOPERATE);
		assertEquals(Decision.BETRAY, strategy.playNextMove(opponentMoves, opponentMoves));
	}
	
	@Test
	void willBetray() {
		List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.COOPERATE);
		assertEquals(Decision.BETRAY, strategy.playNextMove(opponentMoves, opponentMoves));
	}

	@Test
	void otherCases() {
		List<Decision> opponentMoves = Arrays.asList(Decision.BETRAY, Decision.COOPERATE);
		assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, null)));
		assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, opponentMoves)));
	}
}
