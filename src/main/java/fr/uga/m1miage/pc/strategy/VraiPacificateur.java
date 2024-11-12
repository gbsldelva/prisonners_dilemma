package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.utils.UtilFunctions;

public class VraiPacificateur implements Strategy{

	@Override
	public String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves) {
		if (UtilFunctions.listContainsMoves(opponentPreviousMoves) && opponentPreviousMoves.size() >= 2) {
			boolean canChangeBehavior = UtilFunctions.eventIsVeryLikelyToHappen();
			boolean twoLastMoveAreBetrayal = opponentPreviousMoves.get(opponentPreviousMoves.size() - 1).equals(opponentPreviousMoves.get(opponentPreviousMoves.size() - 2)) && opponentPreviousMoves.get(opponentPreviousMoves.size() - 1).equals("t");
			if (twoLastMoveAreBetrayal) {
				if (canChangeBehavior)
					return "c";
				return "t";
			} else {
				return "c";
			}
		}
		return UtilFunctions.getRandomMove();
	}

}
