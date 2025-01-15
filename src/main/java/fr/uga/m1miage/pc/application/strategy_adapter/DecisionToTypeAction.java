package fr.uga.m1miage.pc.application.strategy_adapter;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.strats.g8.enums.TypeAction;

public class DecisionToTypeAction {
	private DecisionToTypeAction () {}
	public static TypeAction convert(Decision decision) {
		if (decision == Decision.COOPERATE)
			return TypeAction.COOPERER;
		return TypeAction.TRAHIR;
	}
}
