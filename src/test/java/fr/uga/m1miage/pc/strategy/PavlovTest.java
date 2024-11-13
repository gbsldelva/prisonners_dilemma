package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class PavlovTest {
	Strategy strategy = new Pavlov();
	
	@Test
	void testRepeatingMoveCase1() {
		List<String> myMoves = Arrays.asList("c", "c");
		List<String> opponentMoves = Arrays.asList("c", "c");
		
		assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
	}
	
	@Test
	void testRepeatingMoveCase2() {
		List<String> myMoves = Arrays.asList("c", "t");
		List<String> opponentMoves = Arrays.asList("c", "c");
		
		assertEquals("t", strategy.playNextMove(myMoves, opponentMoves));
	}
	
	@Test
	void testChangeMoveCase1() {
		List<String> myMoves = Arrays.asList("c", "t");
		List<String> opponentMoves = Arrays.asList("c", "t");
		
		assertEquals("c", strategy.playNextMove(myMoves, opponentMoves));
	}
	
	@Test
	void testChangeMoveCase2() {
		List<String> myMoves = Arrays.asList("c", "c");
		List<String> opponentMoves = Arrays.asList("c", "t");
		
		assertEquals("t", strategy.playNextMove(myMoves, opponentMoves));
	}
	
	@Test
	void otherCases() {
		List<String> opponentMoves = Arrays.asList("t");
		assertTrue("c,t".contains(strategy.playNextMove(null, null)));
		assertTrue("c,t".contains(strategy.playNextMove(null, opponentMoves)));
	}
	
}
