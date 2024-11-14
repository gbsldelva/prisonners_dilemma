package fr.uga.m1miage.pc.strategy;

import java.util.List;

public interface Strategy {
	String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves);
}