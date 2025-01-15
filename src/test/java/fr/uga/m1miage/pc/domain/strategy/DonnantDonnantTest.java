package fr.uga.m1miage.pc.domain.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.utils.UtilFunctions;
import org.junit.jupiter.api.Test;

class DonnantDonnantTest {
	Strategy strategy = new DonnantDonnant();
	@Test
	void testPlayNextMoveWithNullValues() {
		assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, null)), "Should return c or t");
	}
	
	@Test
	void testPlayNextMoveWithEmptyValues() {
		List<Decision> opponentMoves = new ArrayList<>();
		assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, opponentMoves)), "Should return c or t");
	}
	
	@Test
	void testPlayNextMoveWhenCooperating() {
		List<Decision> opponentMoves = new ArrayList<>();
		opponentMoves.add(Decision.COOPERATE);
		assertEquals(Decision.COOPERATE, strategy.playNextMove(null, opponentMoves), "Should return c.");
	}
	
	@Test
	void testPlayNextMoveWhenBetraying() {
		List<Decision> opponentMoves = new ArrayList<>();
		opponentMoves.add(Decision.BETRAY);
		assertEquals(Decision.BETRAY, strategy.playNextMove(null, opponentMoves), "Should return t.");
	}
}
