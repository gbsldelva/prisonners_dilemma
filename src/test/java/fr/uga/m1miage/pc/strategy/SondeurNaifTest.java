package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.uga.m1miage.pc.utils.UtilFunctions;

class SondeurNaifTest {
	Strategy strategy = new SondeurNaif();
	
	@Test
	void testPlayNextMoveWhenBetraying() {
		List<String> opponentLastMoves = Arrays.asList("c", "t", "c", "t");
		assertEquals("t", strategy.playNextMove(null, opponentLastMoves));
	}
	
	@Test
	void testPlayNextMoveWhenCooperating() {
		List<String> opponentLastMoves = Arrays.asList("c", "t", "c");
		List<String> results = new ArrayList<String>();
		
		for (int i = 0; i < 100; i++)
			results.add(strategy.playNextMove(null, opponentLastMoves));
		System.out.println(results);
		assertTrue(UtilFunctions.checkOccurenceOfTextInList(results, "c", 50));
		assertTrue(UtilFunctions.checkOccurenceOfTextInList(results, "t", 2));
	}
	
	@Test
	void otherCases() {
		List<String> opponentMoves = Arrays.asList("t");
		assertTrue("c,t".contains(strategy.playNextMove(null, null)));
		assertTrue("c,t".contains(strategy.playNextMove(null, opponentMoves)));
	}
}
