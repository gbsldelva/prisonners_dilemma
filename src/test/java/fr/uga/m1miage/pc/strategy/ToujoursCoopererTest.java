package fr.uga.m1miage.pc.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ToujoursCoopererTest {
	Strategy strategy = new ToujoursCooperer();
	
	@Test
	void mainCase() {
		assertEquals("c", strategy.playNextMove(null, null));
	}
}
