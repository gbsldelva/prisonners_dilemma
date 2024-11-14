package fr.uga.m1miage.pc.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class UtilFunctionsTest {
	@Test
	void testListContainsMoves() {
		List<String> list = new ArrayList<String>();
		assertTrue(!UtilFunctions.listContainsMoves(list), "The list is empty.");
		assertTrue(!UtilFunctions.listContainsMoves(null), "The list is null");
		list.add("Test");
		assertTrue(UtilFunctions.listContainsMoves(list), "The list is not empty");
	}
	
	@Test
    void testGetRandomMove() {
        int numIterations = 100;
        int countC = 0;

        for (int i = 0; i < numIterations; i++) {
            String move = UtilFunctions.getRandomMove();
            if (move.equals("c")) {
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
		String playerLastMove = "c";
		String opponentLastMove = "c";
		
		assertTrue(UtilFunctions.get3or5pointsInLastMove(playerLastMove, opponentLastMove));
	}
	
	@Test
	void testPlayerGet3or5pointsInLastMoveWhenBetraying() {
		String playerLastMove = "t";
		String opponentLastMove = "c";
		
		assertTrue(UtilFunctions.get3or5pointsInLastMove(playerLastMove, opponentLastMove));
	}
	
	@Test
	void testPlayerGet3or5pointsInLastMoveFailedCase1() {
		String playerLastMove = "t";
		String opponentLastMove = "t";
		
		assertTrue(!UtilFunctions.get3or5pointsInLastMove(playerLastMove, opponentLastMove));
	}
	
	@Test
	void testPlayerGet3or5pointsInLastMoveFailedCase2() {
		String playerLastMove = "c";
		String opponentLastMove = "t";
		
		assertTrue(!UtilFunctions.get3or5pointsInLastMove(playerLastMove, opponentLastMove));
	}
	
	@Test
	void testGetOppositeMoveWhenCooperating() {
		String move = "c";
		assertEquals("t", UtilFunctions.getOppositeMove(move));
	}
	
	@Test
	void testGetOppositeMoveWhenBetraying() {
		String move = "t";
		assertEquals("c", UtilFunctions.getOppositeMove(move));
	}
	
	@Test
	void checkOccurenceOfTextInList() {
		List<String> moves = Arrays.asList("c", "t", "c", "c", "c", "t");
		assertTrue(UtilFunctions.checkOccurenceOfTextInList(moves, "c", 2));
		assertTrue(UtilFunctions.checkOccurenceOfTextInList(moves, "c", 4));
		assertTrue(UtilFunctions.checkOccurenceOfTextInList(moves, "c", 0));
		assertFalse(UtilFunctions.checkOccurenceOfTextInList(moves, "d", 1));
		assertFalse(UtilFunctions.checkOccurenceOfTextInList(new ArrayList<String>(), "d", 1));
	}
}
