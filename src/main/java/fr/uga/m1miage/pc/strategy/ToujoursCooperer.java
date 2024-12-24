package fr.uga.m1miage.pc.strategy;

import fr.uga.m1miage.pc.model.Decision;

import java.util.List;

public class ToujoursCooperer implements Strategy{

	@Override
	public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
		return Decision.COOPERATE;
	}

}
