package fr.uga.m1miage.pc.domain.strategy;

import java.util.List;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.utils.UtilFunctions;

public class SondeurNaif implements Strategy{

	@Override
	public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
		if (UtilFunctions.listContainsMoves(opponentPreviousMoves)) {
			// Change behavior a few time when it's time to cooperate
			if (opponentPreviousMoves.get(opponentPreviousMoves.size() - 1) == Decision.COOPERATE && !UtilFunctions.eventIsVeryLikelyToHappen())
				return Decision.BETRAY;
			
			return opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
		}
		return UtilFunctions.getRandomMove();	
	}
	
}
