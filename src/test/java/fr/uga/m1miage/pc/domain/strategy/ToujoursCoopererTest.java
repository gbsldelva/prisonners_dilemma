package fr.uga.m1miage.pc.domain.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.strategy.Strategy;
import fr.uga.m1miage.pc.domain.strategy.ToujoursCooperer;
import org.junit.jupiter.api.Test;

class ToujoursCoopererTest {
	Strategy strategy = new ToujoursCooperer();
	
	@Test
	void mainCase() {
		assertEquals(Decision.COOPERATE, strategy.playNextMove(null, null));
	}
}
