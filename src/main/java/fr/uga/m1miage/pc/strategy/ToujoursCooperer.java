package fr.uga.m1miage.pc.strategy;

import java.util.List;

public class ToujoursCooperer implements Strategy{

	@Override
	public String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves) {
		return "c";
	}

}
