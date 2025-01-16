package fr.uga.m1miage.pc.adapter;

import java.security.SecureRandom;

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
import fr.uga.strats.g8.strategie.RancunierDouxStrategie;
import fr.uga.strats.g8.strategie.RancunierStrategie;
import fr.uga.strats.g8.strategie.SondeurNaifStrategie;
import fr.uga.strats.g8.strategie.SondeurRepentantStrategie;
import fr.uga.strats.g8.strategie.Strategie;
import fr.uga.strats.g8.strategie.ToujoursCoopererStrategie;
import fr.uga.strats.g8.strategie.ToujoursTrahirStrategie;
import fr.uga.strats.g8.strategie.VraiPacificateurStrategie;

public class Groupe2StrategyFactory {
	static SecureRandom secureRandom = new SecureRandom();
	private Groupe2StrategyFactory() {}
	public static Strategie createStrategy(StrategyType type) {
		if (type == null)
			return null;
        switch (type) {
            case DONNANT_DONNANT:
                return new DonnantDonnantStrategie();
            case DONNANT_DONNANT_ALEATOIRE:
                return new DonnantDonnantAleatoireStrategie(secureRandom);
            case DONNANT_DEUX_DONNANT_ALEATOIRE:
                return new DonnantPourDeuxDonnantsEtAleatoireStrategie(secureRandom, secureRandom);
            case DONNANT_DEUX_DONNANT:
                return new DonnantDonnantStrategie();
            case SONDEUR_NAIF:
                return new SondeurNaifStrategie(secureRandom);
            case SONDEUR_REPENTANT:
                return new SondeurRepentantStrategie(secureRandom);
            case PACIFICATEUR_NAIF:
                return new PacificateurNaifStrategie(secureRandom);
            case VRAI_PACIFICATEUR:
                return new VraiPacificateurStrategie(secureRandom);
            case ALEATOIRE:
                return new AleatoireStrategie(secureRandom);
            case TOUJOURS_TRAHIR:
                return new ToujoursTrahirStrategie();
            case TOUJOURS_COOPERER:
                return new ToujoursCoopererStrategie();
            case RANCUNIER:
                return new RancunierStrategie();
            case PAVLOV:
                return new PavlovStrategie();
            case PAVLOV_ALEATOIRE:
                return new PavlovAleatoireStrategie(secureRandom);
            case ADAPTATIF:
                return new AdaptatifStrategie();
            case GRADUEL:
                return new GraduelStrategie();
            case DONNANT_DONNANT_SOUPCONNEUX:
                return new DonnantDonnantSoupconneuxStrategie();
            case RANCUNIER_DOUX:
                return new RancunierDouxStrategie();
        }
		return null;
    }
}
