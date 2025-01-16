package fr.uga.m1miage.pc.adapter;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.strats.g8.enums.TypeAction;

public class TypeActionToDecision {
	private TypeActionToDecision() {}
	public static Decision convert(TypeAction typeAction) {
		if (typeAction == TypeAction.TRAHIR)
			return Decision.BETRAY;
		return Decision.COOPERATE;
	}
}
