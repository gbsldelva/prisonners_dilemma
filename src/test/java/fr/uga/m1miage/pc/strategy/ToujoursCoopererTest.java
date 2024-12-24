package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.uga.m1miage.pc.model.Decision;
import org.junit.jupiter.api.Test;

class ToujoursCoopererTest {
	Strategy strategy = new ToujoursCooperer();
	
	@Test
	void mainCase() {
		assertEquals(Decision.COOPERATE, strategy.playNextMove(null, null));
	}
}
