package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;

public class Rancunier implements Strategy{

	@Override
	public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
		if (UtilFunctions.listContainsMoves(opponentPreviousMoves)) {
			if (opponentPreviousMoves.contains(Decision.COOPERATE))
				return Decision.BETRAY;
			return Decision.COOPERATE;
		}
		return UtilFunctions.getRandomMove();
	}

}
