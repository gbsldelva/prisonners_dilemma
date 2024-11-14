package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.utils.UtilFunctions;

public class Pavlov implements Strategy{

	@Override
	public String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves) {
		if (UtilFunctions.listContainsMoves(opponentPreviousMoves) && UtilFunctions.listContainsMoves(myPreviousMoves)) {
			if (UtilFunctions.get3or5pointsInLastMove(myPreviousMoves.get(myPreviousMoves.size() - 1), opponentPreviousMoves.get(opponentPreviousMoves.size() - 1)))
				return myPreviousMoves.get(myPreviousMoves.size() - 1);
			return UtilFunctions.getOppositeMove(myPreviousMoves.get(myPreviousMoves.size() - 1));
		}
		return UtilFunctions.getRandomMove();
	}
	
}
