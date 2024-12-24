package fr.uga.m1miage.pc.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class StrategyFactoryTest {

    @Test
    void testCreateStrategyAllCases() {
        assertInstanceOf(DonnantDonnant.class, StrategyFactory.createStrategy(StrategyType.DONNANT_DONNANT));
        assertInstanceOf(DonnantDonnantAleatoire.class, StrategyFactory.createStrategy(StrategyType.DONNANT_DONNANT_ALEATOIRE));
        assertInstanceOf(DonnantDeuxDonnantAleatoire.class, StrategyFactory.createStrategy(StrategyType.DONNANT_DEUX_DONNANT_ALEATOIRE));
        assertInstanceOf(DonnantDeuxDonnant.class, StrategyFactory.createStrategy(StrategyType.DONNANT_DEUX_DONNANT));
        assertInstanceOf(SondeurNaif.class, StrategyFactory.createStrategy(StrategyType.SONDEUR_NAIF));
        assertInstanceOf(SondeurRepentant.class, StrategyFactory.createStrategy(StrategyType.SONDEUR_REPENTANT));
        assertInstanceOf(PacificateurNaif.class, StrategyFactory.createStrategy(StrategyType.PACIFICATEUR_NAIF));
        assertInstanceOf(VraiPacificateur.class, StrategyFactory.createStrategy(StrategyType.VRAI_PACIFICATEUR));
        assertInstanceOf(Aleatoire.class, StrategyFactory.createStrategy(StrategyType.ALEATOIRE));
        assertInstanceOf(ToujoursTrahir.class, StrategyFactory.createStrategy(StrategyType.TOUJOURS_TRAHIR));
        assertInstanceOf(ToujoursCooperer.class, StrategyFactory.createStrategy(StrategyType.TOUJOURS_COOPERER));
        assertInstanceOf(Rancunier.class, StrategyFactory.createStrategy(StrategyType.RANCUNIER));
        assertInstanceOf(Pavlov.class, StrategyFactory.createStrategy(StrategyType.PAVLOV));
        assertInstanceOf(PavlovAleatoire.class, StrategyFactory.createStrategy(StrategyType.PAVLOV_ALEATOIRE));
        assertInstanceOf(Adaptatif.class, StrategyFactory.createStrategy(StrategyType.ADAPTATIF));
        assertInstanceOf(Graduel.class, StrategyFactory.createStrategy(StrategyType.GRADUEL));
        assertInstanceOf(DonnantDonnantSoupconneux.class, StrategyFactory.createStrategy(StrategyType.DONNANT_DONNANT_SOUPCONNEUX));
        assertInstanceOf(RancunierDoux.class, StrategyFactory.createStrategy(StrategyType.RANCUNIER_DOUX));
    }

    @Test
    void shouldThrowExceptionForUnknownStrategy() {
        assertThrows(IllegalArgumentException.class, () -> StrategyFactory.createStrategy(null));
    }
}
