package fr.uga.m1miage.pc.domain.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.uga.m1miage.pc.domain.model.Decision;
import org.junit.jupiter.api.Test;

import fr.uga.m1miage.pc.domain.utils.UtilFunctions;

class SondeurNaifTest {
	Strategy strategy = new SondeurNaif();
	
	@Test
	void testPlayNextMoveWhenBetraying() {
		List<Decision> opponentLastMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.COOPERATE, Decision.BETRAY);
		assertEquals(Decision.BETRAY, strategy.playNextMove(null, opponentLastMoves));
	}
	
	@Test
	void testPlayNextMoveWhenCooperating() {
		List<Decision> opponentLastMoves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.COOPERATE);
		List<Decision> results = new ArrayList<>();
		
		for (int i = 0; i < 100; i++)
			results.add(strategy.playNextMove(null, opponentLastMoves));
		assertTrue(UtilFunctions.checkOccurenceOfTextInList(results, Decision.COOPERATE, 50));
		assertTrue(UtilFunctions.checkOccurenceOfTextInList(results, Decision.BETRAY, 2));
	}

	@Test
	void otherCases() {
		List<Decision> opponentMoves = Arrays.asList(Decision.BETRAY, Decision.COOPERATE);
		assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, null)));
		assertTrue(UtilFunctions.choices.contains(strategy.playNextMove(null, opponentMoves)));
	}
}
