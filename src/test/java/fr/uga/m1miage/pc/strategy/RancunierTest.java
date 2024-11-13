package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class RancunierTest {
	Strategy strategy = new Rancunier();
	
	@Test
	void willCooperate() {
		List<String> opponentMoves = Arrays.asList("c", "c", "c");
		assertEquals("c", strategy.playNextMove(null, opponentMoves));
	}
	
	@Test
	void willBetray() {
		List<String> opponentMoves = Arrays.asList("c", "t", "c");
		assertEquals("t", strategy.playNextMove(null, opponentMoves));
	}
	
	@Test
	void otherCases() {
		List<String> opponentMoves = Arrays.asList("t");
		assertTrue("c,t".contains(strategy.playNextMove(null, null)));
		assertTrue("c,t".contains(strategy.playNextMove(null, opponentMoves)));
	}
}
