package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class DonnantDeuxDonnantTest {
	Strategy strategy = new DonnantDeuxDonnant();
	@Test
	void playNextMoveBetrayal() {
		List<String> opponentMoves = Arrays.asList("c", "t", "t");
		String move = strategy.playNextMove(null, opponentMoves);
		assertEquals("t", move);
	}
	
	@Test
	void playNextMoveCooperation() {
		List<String> opponentMoves = Arrays.asList("t", "c", "c");
		String move = strategy.playNextMove(null, opponentMoves);
		assertEquals("c", move);
	}
	
	@Test
	void otherCases() {
		List<String> opponentMoves = Arrays.asList("t");
		assertTrue("c,t".contains(strategy.playNextMove(null, null)));
		assertTrue("c,t".contains(strategy.playNextMove(null, opponentMoves)));
	}
}
