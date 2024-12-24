package fr.uga.m1miage.pc.strategy;

import fr.uga.m1miage.pc.model.Decision;

import java.util.List;

public interface Strategy {
	Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves);
}