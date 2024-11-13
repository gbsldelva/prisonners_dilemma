package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.uga.m1miage.pc.utils.UtilFunctions;

class AleatoireTest {
	Strategy strategy = new Aleatoire();
	
	private List<String> generateRandomMoves(int number) {
		List<String> moves = new ArrayList<>();
		for (int i = 0; i < number; i++)
			moves.add(strategy.playNextMove(null, null));
		return moves;
	}
	
	@Test
	void testPlayNextMoveCooperation() {
		List<String> moves = generateRandomMoves(100);
		boolean atLeast30Cooperation = UtilFunctions.checkOccurenceOfTextInList(moves, "c", 30);
		assertTrue(atLeast30Cooperation);
	}
	
	@Test
	void testPlayNextMoveBetrayal() {
		List<String> moves = generateRandomMoves(100);
		boolean atLeast30Cooperation = UtilFunctions.checkOccurenceOfTextInList(moves, "t", 30);
		assertTrue(atLeast30Cooperation);
	}
	
}
