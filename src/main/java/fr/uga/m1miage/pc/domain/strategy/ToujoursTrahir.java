package fr.uga.m1miage.pc.domain.strategy;

import fr.uga.m1miage.pc.domain.model.Decision;

import java.util.List;

public class ToujoursTrahir implements Strategy {

	@Override
	public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
		return Decision.BETRAY;
	}

}
