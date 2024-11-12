package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.utils.UtilFunctions;

public class SondeurNaif implements Strategy{

	@Override
	public String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves) {
		if (UtilFunctions.listContainsMoves(opponentPreviousMoves)) {
			// Change behavior a few time when it's time to cooperate
			if (opponentPreviousMoves.get(opponentPreviousMoves.size() - 1).equals("c")) {
				boolean shouldChangeBehavior = !UtilFunctions.eventIsVeryLikelyToHappen();
				if (shouldChangeBehavior)
					return "t";
			}
			return opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
		}
		return UtilFunctions.getRandomMove();	
	}
	
}
