package fr.uga.m1miage.pc.domain.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.strategy.Strategy;
import fr.uga.m1miage.pc.domain.strategy.ToujoursTrahir;
import org.junit.jupiter.api.Test;

class ToujoursTrahirTest {
	Strategy strategy = new ToujoursTrahir();
	
	@Test
	void testMainCase() {
		assertEquals(Decision.BETRAY, strategy.playNextMove(null, null));
	}
}
