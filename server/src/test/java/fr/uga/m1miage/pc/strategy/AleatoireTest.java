package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import fr.uga.m1miage.pc.model.Decision;
import org.junit.jupiter.api.Test;

import fr.uga.m1miage.pc.utils.UtilFunctions;

class AleatoireTest {
	Strategy strategy = new Aleatoire();
	
	private List<Decision> generateRandomMoves(int number) {
		List<Decision> moves = new ArrayList<>();
		for (int i = 0; i < number; i++)
			moves.add(strategy.playNextMove(null, null));
		return moves;
	}
	
	@Test
	void testPlayNextMoveCooperation() {
		List<Decision> moves = generateRandomMoves(100);
		boolean atLeast30Cooperation = UtilFunctions.checkOccurenceOfTextInList(moves, Decision.COOPERATE, 30);
		assertTrue(atLeast30Cooperation);
	}
	
	@Test
	void testPlayNextMoveBetrayal() {
		List<Decision> moves = generateRandomMoves(100);
		boolean atLeast30Cooperation = UtilFunctions.checkOccurenceOfTextInList(moves, Decision.BETRAY, 30);
		assertTrue(atLeast30Cooperation);
	}
	
}
