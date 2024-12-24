package fr.uga.m1miage.pc.strategy;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;
import java.util.List;

public class DonnantDonnant implements Strategy {

	@Override
	public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
		if (UtilFunctions.listContainsMoves(opponentPreviousMoves))
			return opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
		return UtilFunctions.getRandomMove();	
	}
}
