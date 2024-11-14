package fr.uga.m1miage.pc.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class StrategyFactoryTest {

    @Test
    void testCreateStrategyAllCases() {
        assertTrue(StrategyFactory.createStrategy(StrategyType.DONNANT_DONNANT) instanceof DonnantDonnant);
        assertTrue(StrategyFactory.createStrategy(StrategyType.DONNANT_DONNANT_ALEATOIRE) instanceof DonnantDonnantAleatoire);
        assertTrue(StrategyFactory.createStrategy(StrategyType.DONNANT_DEUX_DONNANT_ALEATOIRE) instanceof DonnantDeuxDonnantAleatoire);
        assertTrue(StrategyFactory.createStrategy(StrategyType.DONNANT_DEUX_DONNANT) instanceof DonnantDeuxDonnant);
        assertTrue(StrategyFactory.createStrategy(StrategyType.SONDEUR_NAIF) instanceof SondeurNaif);
        assertTrue(StrategyFactory.createStrategy(StrategyType.SONDEUR_REPENTANT) instanceof SondeurRepentant);
        assertTrue(StrategyFactory.createStrategy(StrategyType.PACIFICATEUR_NAIF) instanceof PacificateurNaif);
        assertTrue(StrategyFactory.createStrategy(StrategyType.VRAI_PACIFICATEUR) instanceof VraiPacificateur);
        assertTrue(StrategyFactory.createStrategy(StrategyType.ALEATOIRE) instanceof Aleatoire);
        assertTrue(StrategyFactory.createStrategy(StrategyType.TOUJOURS_TRAHIR) instanceof ToujoursTrahir);
        assertTrue(StrategyFactory.createStrategy(StrategyType.TOUJOURS_COOPERER) instanceof ToujoursCooperer);
        assertTrue(StrategyFactory.createStrategy(StrategyType.RANCUNIER) instanceof Rancunier);
        assertTrue(StrategyFactory.createStrategy(StrategyType.PAVLOV) instanceof Pavlov);
        assertTrue(StrategyFactory.createStrategy(StrategyType.PAVLOV_ALEATOIRE) instanceof PavlovAleatoire);
        assertTrue(StrategyFactory.createStrategy(StrategyType.ADAPTATIF) instanceof Adaptatif);
        assertTrue(StrategyFactory.createStrategy(StrategyType.GRADUEL) instanceof Graduel);
        assertTrue(StrategyFactory.createStrategy(StrategyType.DONNANT_DONNANT_SOUPCONNEUX) instanceof DonnantDonnantSoupconneux);
        assertTrue(StrategyFactory.createStrategy(StrategyType.RANCUNIER_DOUX) instanceof RancunierDoux);
    }

    @Test
    void shouldThrowExceptionForUnknownStrategy() {
        assertThrows(IllegalArgumentException.class, () -> StrategyFactory.createStrategy(null));
    }
}
