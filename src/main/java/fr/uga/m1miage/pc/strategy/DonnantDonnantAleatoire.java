package fr.uga.m1miage.pc.strategy;

import fr.uga.m1miage.pc.utils.UtilFunctions;

import java.util.List;

public class DonnantDonnantAleatoire implements Strategy{
	@Override
	public String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves) {
		if (UtilFunctions.listContainsMoves(opponentPreviousMoves) && UtilFunctions.eventIsVeryLikelyToHappen())
			return opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
		
		return UtilFunctions.getRandomMove();
	}

}
