package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.utils.UtilFunctions;

public class Rancunier implements Strategy{

	@Override
	public String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves) {
		if (UtilFunctions.listContainsMoves(opponentPreviousMoves)) {
			if (opponentPreviousMoves.contains("t"))
				return "t";
			return "c";
		}
		return UtilFunctions.getRandomMove();
	}

}
