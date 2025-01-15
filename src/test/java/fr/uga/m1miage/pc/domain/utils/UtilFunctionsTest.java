package fr.uga.m1miage.pc.domain.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.uga.m1miage.pc.domain.model.Decision;
import org.junit.jupiter.api.Test;

class UtilFunctionsTest {
	@Test
	void testListContainsMoves() {
		List<Decision> list = new ArrayList<>();
		assertTrue(!UtilFunctions.listContainsMoves(list), "The list is empty.");
		assertTrue(!UtilFunctions.listContainsMoves(null), "The list is null");
		list.add(Decision.BETRAY);
		assertTrue(UtilFunctions.listContainsMoves(list), "The list is not empty");
	}
	
	@Test
    void testGetRandomMove() {
        int numIterations = 100;
        int countC = 0;

        for (int i = 0; i < numIterations; i++) {
            Decision move = UtilFunctions.getRandomMove();
            if (move == Decision.COOPERATE) {
                countC++;
            }
        }

        assertTrue(countC >= 30, "After 100 iterations, we should get at least 30 values for 'c'");
    }
	
	@Test
	void testEventIsVeryLikelyToHappen() {
		List<Boolean> results = new ArrayList<>();
		for (int i = 0; i< 100; i++)
			results.add(UtilFunctions.eventIsVeryLikelyToHappen());
		long numberOfTrueValues = results.stream().filter(b -> b).limit(80).count();
		assertTrue(numberOfTrueValues >= 80);
	}
	
	@Test
	void testPlayerGet3or5pointsInLastMoveWhenCooperating() {
		Decision playerLastMove = Decision.COOPERATE;
		Decision opponentLastMove = Decision.COOPERATE;
		
		assertTrue(UtilFunctions.get3or5pointsInLastMove(playerLastMove, opponentLastMove));
	}
	
	@Test
	void testPlayerGet3or5pointsInLastMoveWhenBetraying() {
		Decision playerLastMove = Decision.BETRAY;
		Decision opponentLastMove = Decision.COOPERATE;
		
		assertTrue(UtilFunctions.get3or5pointsInLastMove(playerLastMove, opponentLastMove));
	}
	
	@Test
	void testPlayerGet3or5pointsInLastMoveFailedCase1() {
		Decision playerLastMove = Decision.BETRAY;
		Decision opponentLastMove = Decision.BETRAY;
		
		assertFalse(UtilFunctions.get3or5pointsInLastMove(playerLastMove, opponentLastMove));
	}
	
	@Test
	void testPlayerGet3or5pointsInLastMoveFailedCase2() {
		Decision playerLastMove = Decision.COOPERATE;
		Decision opponentLastMove = Decision.BETRAY;
		
		assertFalse(UtilFunctions.get3or5pointsInLastMove(playerLastMove, opponentLastMove));
	}
	
	@Test
	void testGetOppositeMoveWhenCooperating() {
		Decision move = Decision.COOPERATE;
		assertEquals(Decision.BETRAY, UtilFunctions.getOppositeMove(move));
	}
	
	@Test
	void testGetOppositeMoveWhenBetraying() {
		Decision move = Decision.BETRAY;
		assertEquals(Decision.COOPERATE, UtilFunctions.getOppositeMove(move));
	}
	
	@Test
	void checkOccurenceOfTextInList() {
		List<Decision> moves = Arrays.asList(Decision.COOPERATE, Decision.BETRAY, Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE, Decision.BETRAY);
		assertTrue(UtilFunctions.checkOccurenceOfTextInList(moves, Decision.COOPERATE, 2));
		assertTrue(UtilFunctions.checkOccurenceOfTextInList(moves, Decision.COOPERATE, 4));
		assertTrue(UtilFunctions.checkOccurenceOfTextInList(moves, Decision.COOPERATE, 0));
		assertTrue(UtilFunctions.checkOccurenceOfTextInList(moves, Decision.BETRAY, 1));
		assertFalse(UtilFunctions.checkOccurenceOfTextInList(new ArrayList<Decision>(), Decision.BETRAY, 1));
	}
}
