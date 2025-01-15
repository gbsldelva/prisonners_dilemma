package fr.uga.m1miage.pc.domain.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.utils.UtilFunctions;
import org.junit.jupiter.api.Test;

class DonnantDeuxDonnantTest {
	Strategy strategy = new DonnantDeuxDonnant();
	@Test
	void playNextMoveBetrayal() {
		List<Decision> opponentMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.BETRAY);
		Decision move = strategy.playNextMove(null, opponentMoves);
		assertEquals( Decision.BETRAY, move);
	}
	
	@Test
	void playNextMoveCooperation() {
		List<Decision> opponentMoves = Arrays.asList(Decision.BETRAY, Decision.COOPERATE, Decision.COOPERATE);
		Decision move = strategy.playNextMove(null, opponentMoves);
		assertEquals(Decision.COOPERATE, move);
	}

	@Test
	void otherCases() {
		List<Decision> opponentMoves = Arrays.asList(Decision.BETRAY, Decision.COOPERATE);
		assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, null)));
		assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, opponentMoves)));
	}
}
