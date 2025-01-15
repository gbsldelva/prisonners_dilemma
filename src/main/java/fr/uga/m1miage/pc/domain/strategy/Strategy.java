package fr.uga.m1miage.pc.domain.strategy;

import fr.uga.m1miage.pc.domain.model.Decision;

import java.util.List;

public interface Strategy {
	Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves);
}