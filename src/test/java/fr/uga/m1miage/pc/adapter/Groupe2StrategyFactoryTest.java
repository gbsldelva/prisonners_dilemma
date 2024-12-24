package fr.uga.m1miage.pc.adapter;

import org.junit.jupiter.api.Test;

import fr.uga.m1miage.pc.strategy.StrategyType;
import fr.uga.strats.g8.strategie.AdaptatifStrategie;
import fr.uga.strats.g8.strategie.AleatoireStrategie;
import fr.uga.strats.g8.strategie.DonnantDonnantAleatoireStrategie;
import fr.uga.strats.g8.strategie.DonnantDonnantSoupconneuxStrategie;
import fr.uga.strats.g8.strategie.DonnantDonnantStrategie;
import fr.uga.strats.g8.strategie.DonnantPourDeuxDonnantsEtAleatoireStrategie;
import fr.uga.strats.g8.strategie.GraduelStrategie;
import fr.uga.strats.g8.strategie.PacificateurNaifStrategie;
import fr.uga.strats.g8.strategie.PavlovAleatoireStrategie;
import fr.uga.strats.g8.strategie.PavlovStrategie;
import fr.uga.strats.g8.strategie.RancunierStrategie;
import fr.uga.strats.g8.strategie.SondeurNaifStrategie;
import fr.uga.strats.g8.strategie.SondeurRepentantStrategie;
import fr.uga.strats.g8.strategie.Strategie;
import fr.uga.strats.g8.strategie.ToujoursCoopererStrategie;
import fr.uga.strats.g8.strategie.ToujoursTrahirStrategie;
import fr.uga.strats.g8.strategie.VraiPacificateurStrategie;

import static org.junit.jupiter.api.Assertions.*;

class Groupe2StrategyFactoryTest {

    @Test
    void testCreateStrategyDonnantDonnant() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.DONNANT_DONNANT);
        assertTrue(strategie instanceof DonnantDonnantStrategie);
    }

    @Test
    void testCreateStrategyDonnantDonnantAleatoire() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.DONNANT_DONNANT_ALEATOIRE);
        assertTrue(strategie instanceof DonnantDonnantAleatoireStrategie);
    }

    @Test
    void testCreateStrategyDonnantDeuxDonnantsAleatoire() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.DONNANT_DEUX_DONNANT_ALEATOIRE);
        assertTrue(strategie instanceof DonnantPourDeuxDonnantsEtAleatoireStrategie);
    }

    @Test
    void testCreateStrategyDonnantDeuxDonnants() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.DONNANT_DEUX_DONNANT);
        assertTrue(strategie instanceof DonnantDonnantStrategie);
    }

    @Test
    void testCreateStrategySondeurNaif() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.SONDEUR_NAIF);
        assertTrue(strategie instanceof SondeurNaifStrategie);
    }

    @Test
    void testCreateStrategySondeurRepentant() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.SONDEUR_REPENTANT);
        assertTrue(strategie instanceof SondeurRepentantStrategie);
    }

    @Test
    void testCreateStrategyPacificateurNaif() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.PACIFICATEUR_NAIF);
        assertTrue(strategie instanceof PacificateurNaifStrategie);
    }

    @Test
    void testCreateStrategyVraiPacificateur() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.VRAI_PACIFICATEUR);
        assertTrue(strategie instanceof VraiPacificateurStrategie);
    }

    @Test
    void testCreateStrategyAleatoire() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.ALEATOIRE);
        assertTrue(strategie instanceof AleatoireStrategie);
    }

    @Test
    void testCreateStrategyToujoursTrahir() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.TOUJOURS_TRAHIR);
        assertTrue(strategie instanceof ToujoursTrahirStrategie);
    }

    @Test
    void testCreateStrategyToujoursCooperer() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.TOUJOURS_COOPERER);
        assertTrue(strategie instanceof ToujoursCoopererStrategie);
    }

    @Test
    void testCreateStrategyRancunier() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.RANCUNIER);
        assertTrue(strategie instanceof RancunierStrategie);
    }

    @Test
    void testCreateStrategyPavlov() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.PAVLOV);
        assertTrue(strategie instanceof PavlovStrategie);
    }

    @Test
    void testCreateStrategyPavlovAleatoire() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.PAVLOV_ALEATOIRE);
        assertTrue(strategie instanceof PavlovAleatoireStrategie);
    }

    @Test
    void testCreateStrategyAdaptatif() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.ADAPTATIF);
        assertTrue(strategie instanceof AdaptatifStrategie);
    }

    @Test
    void testCreateStrategyGraduel() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.GRADUEL);
        assertTrue(strategie instanceof GraduelStrategie);
    }

    @Test
    void testCreateStrategyDonnantDonnantSoupconneux() {
        Strategie strategie = Groupe2StrategyFactory.createStrategy(StrategyType.DONNANT_DONNANT_SOUPCONNEUX);
        assertTrue(strategie instanceof DonnantDonnantSoupconneuxStrategie);
    }

}