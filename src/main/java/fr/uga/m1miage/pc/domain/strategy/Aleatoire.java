package fr.uga.m1miage.pc.domain.strategy;

import java.util.List;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.utils.UtilFunctions;

public class Aleatoire implements Strategy {

	@Override
	public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
		return UtilFunctions.getRandomMove();
	}
	
}
