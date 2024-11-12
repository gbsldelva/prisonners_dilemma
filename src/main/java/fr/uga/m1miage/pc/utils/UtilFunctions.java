package fr.uga.m1miage.pc.utils;

import java.security.SecureRandom;
import java.util.List;

public class UtilFunctions {
	public static final SecureRandom random = new SecureRandom();
	static String[] choices = {"c", "t"};
	
	private UtilFunctions() {}
	
	public static boolean listContainsMoves(List<String> myList) {
	    return myList != null && !myList.isEmpty();
	}
	
	public static String getRandomMove() {
		return choices[random.nextInt(2)];
	}
	
	public static boolean eventIsVeryLikelyToHappen() {
		int randomNumber = UtilFunctions.random.nextInt(5);
		
		// We return true if the randomNumber is greater than 0
		// Theoretically this event has a 80% chance of likelihood
		return randomNumber > 0;
	}
	
	public static boolean get3or5pointsInLastMove(String myMove, String opponentMove) {
		boolean bothCooperated = myMove.equals(opponentMove) && myMove.equals("c");
		boolean ibetrayedOnly = opponentMove.equals("c") && myMove.equals("t");
		
		return bothCooperated || ibetrayedOnly;
	}
	
	public static String getOppositeMove(String move) {
		if (move.equals("c"))
			return "t";
		return "c";
	}
	
}
