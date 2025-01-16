package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.uga.m1miage.pc.model.Decision;
import org.junit.jupiter.api.Test;

class ToujoursTrahirTest {
	Strategy strategy = new ToujoursTrahir();
	
	@Test
	void testMainCase() {
		assertEquals(Decision.BETRAY, strategy.playNextMove(null, null));
	}
}
