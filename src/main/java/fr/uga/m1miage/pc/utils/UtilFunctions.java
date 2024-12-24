package fr.uga.m1miage.pc.utils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import fr.uga.m1miage.pc.model.Decision;

public class UtilFunctions {
	public static final SecureRandom random = new SecureRandom();
	public static List<Decision> choices = Arrays.asList(Decision.COOPERATE, Decision.BETRAY);
	
	private UtilFunctions() {}
	
	public static boolean listContainsMoves(List<Decision> myList) {
	    return myList != null && !myList.isEmpty();
	}
	
	public static int getScore(Decision myDecision, Decision opponentDecision) {
		int score = -1;
		if (myDecision == Decision.COOPERATE && opponentDecision == Decision.BETRAY)
			score = 0;
		else if (myDecision == Decision.COOPERATE && opponentDecision == Decision.COOPERATE)
			score = 3;
		else if (myDecision == Decision.BETRAY && opponentDecision == Decision.BETRAY)
			score = 1;
		else if (myDecision == Decision.BETRAY && opponentDecision == Decision.COOPERATE)
			score = 5;
		return score;
	}
	
	public static Decision getRandomMove() {
		return choices.get(random.nextInt(2));
	}
	
	public static boolean eventIsVeryLikelyToHappen() {
		int randomNumber = random.nextInt(10);
		
		// We return true if the randomNumber is greater than 0
		// Theoretically this event has a 90% chance of likelihood
		return randomNumber > 0;
	}
	
	public static boolean get3or5pointsInLastMove(Decision myMove, Decision opponentMove) {
		boolean bothCooperated = myMove == opponentMove && myMove == Decision.COOPERATE;
		boolean ibetrayedOnly = opponentMove == Decision.COOPERATE && myMove == Decision.BETRAY;
		
		return bothCooperated || ibetrayedOnly;
	}
	
	public static Decision getOppositeMove(Decision decision) {
		if (decision.getValue().equals("c"))
			return Decision.BETRAY;
		return Decision.COOPERATE;
	}
	
	public static boolean checkOccurenceOfTextInList(List<Decision> list, Decision decision, int minOccurence) {
		int occurence = 0;
		for (Decision value : list) {
			if (decision == value) {
				occurence++;
			}
		}
		return occurence >= minOccurence && occurence != 0;
	}
	
}
