package fr.uga.m1miage.pc.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StrategyTypeTest {

    @Test
    void testFromStringAllCases() {
        assertEquals(StrategyType.DONNANT_DONNANT, StrategyType.fromString("DonnantDonnant"));
        assertEquals(StrategyType.DONNANT_DONNANT_ALEATOIRE, StrategyType.fromString("DonnantDonnantAleatoire"));
        assertEquals(StrategyType.DONNANT_DEUX_DONNANT_ALEATOIRE, StrategyType.fromString("DonnantDeuxDonnantAleatoire"));
        assertEquals(StrategyType.DONNANT_DEUX_DONNANT, StrategyType.fromString("DonnantDeuxDonnant"));
        assertEquals(StrategyType.SONDEUR_NAIF, StrategyType.fromString("SondeurNaif"));
        assertEquals(StrategyType.SONDEUR_REPENTANT, StrategyType.fromString("SondeurRepentant"));
        assertEquals(StrategyType.PACIFICATEUR_NAIF, StrategyType.fromString("PacificateurNaif"));
        assertEquals(StrategyType.VRAI_PACIFICATEUR, StrategyType.fromString("VraiPacificateur"));
        assertEquals(StrategyType.ALEATOIRE, StrategyType.fromString("Aleatoire"));
        assertEquals(StrategyType.TOUJOURS_TRAHIR, StrategyType.fromString("ToujoursTrahir"));
        assertEquals(StrategyType.TOUJOURS_COOPERER, StrategyType.fromString("ToujoursCooperer"));
        assertEquals(StrategyType.RANCUNIER, StrategyType.fromString("Rancunier"));
        assertEquals(StrategyType.PAVLOV, StrategyType.fromString("Pavlov"));
        assertEquals(StrategyType.PAVLOV_ALEATOIRE, StrategyType.fromString("PavlovAleatoire"));
        assertEquals(StrategyType.ADAPTATIF, StrategyType.fromString("Adaptatif"));
        assertEquals(StrategyType.GRADUEL, StrategyType.fromString("Graduel"));
        assertEquals(StrategyType.DONNANT_DONNANT_SOUPCONNEUX, StrategyType.fromString("DonnantDonnantSoupconneux"));
        assertEquals(StrategyType.RANCUNIER_DOUX, StrategyType.fromString("RancunierDoux"));
    }

    @Test
    void testFromStringInvalidCase() {
        assertThrows(IllegalArgumentException.class, () -> StrategyType.fromString("UnknownStrategy"));
        assertThrows(IllegalArgumentException.class, () -> StrategyType.fromString(""));
    }
}
