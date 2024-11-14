package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class DonnantDonnantTest {
	Strategy strategy = new DonnantDonnant();
	@Test
	void testPlayNextMoveWithNullValues() {
		assertTrue("c,t".contains(strategy.playNextMove(null, null)), "Should return c or t");
	}
	
	@Test
	void testPlayNextMoveWithEmptyValues() {
		List<String> opponentMoves = new ArrayList<String>();
		assertTrue("c,t".contains(strategy.playNextMove(null, opponentMoves)), "Should return c or t");
	}
	
	@Test
	void testPlayNextMoveWhenCooperating() {
		List<String> opponentMoves = new ArrayList<String>();
		opponentMoves.add("c");
		assertEquals("c", strategy.playNextMove(null, opponentMoves), "Should return c.");
	}
	
	@Test
	void testPlayNextMoveWhenBetraying() {
		List<String> opponentMoves = new ArrayList<String>();
		opponentMoves.add("t");
		assertEquals("t", strategy.playNextMove(null, opponentMoves), "Should return t.");
	}
}
