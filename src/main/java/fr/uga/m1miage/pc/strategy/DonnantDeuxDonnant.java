package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;

public class DonnantDeuxDonnant implements Strategy{

	@Override
	public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
		if (UtilFunctions.listContainsMoves(opponentPreviousMoves) && opponentPreviousMoves.size() >= 2) {
			boolean twoLastMoveAreSame = opponentPreviousMoves.get(opponentPreviousMoves.size() - 1).equals(opponentPreviousMoves.get(opponentPreviousMoves.size() - 2));
			if (twoLastMoveAreSame)
				return opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
		}
		return UtilFunctions.getRandomMove();
	}

}
