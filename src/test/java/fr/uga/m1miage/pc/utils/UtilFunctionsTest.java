package fr.uga.m1miage.pc.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
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
}
