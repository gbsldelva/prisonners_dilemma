package fr.uga.m1miage.pc.strategy;

import java.util.List;

public class ToujoursTrahir implements Strategy {

	@Override
	public String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves) {
		return "t";
	}

}
