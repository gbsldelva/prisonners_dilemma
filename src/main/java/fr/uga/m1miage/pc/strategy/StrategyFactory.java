package fr.uga.m1miage.pc.strategy;

public class StrategyFactory {
	
	private StrategyFactory() {}
	
    public static Strategy createStrategy(StrategyType type) {
    	if (type == null)
    		throw new IllegalArgumentException("Unknown strategy type: " + type);
    	
        switch (type) {
            case DONNANT_DONNANT:
                return new DonnantDonnant();
            case DONNANT_DONNANT_ALEATOIRE:
                return new DonnantDonnantAleatoire();
            case DONNANT_DEUX_DONNANT_ALEATOIRE:
                return new DonnantDeuxDonnantAleatoire();
            case DONNANT_DEUX_DONNANT:
                return new DonnantDeuxDonnant();
            case SONDEUR_NAIF:
                return new SondeurNaif();
            case SONDEUR_REPENTANT:
                return new SondeurRepentant();
            case PACIFICATEUR_NAIF:
                return new PacificateurNaif();
            case VRAI_PACIFICATEUR:
                return new VraiPacificateur();
            case ALEATOIRE:
                return new Aleatoire();
            case TOUJOURS_TRAHIR:
                return new ToujoursTrahir();
            case TOUJOURS_COOPERER:
                return new ToujoursCooperer();
            case RANCUNIER:
                return new Rancunier();
            case PAVLOV:
                return new Pavlov();
            case PAVLOV_ALEATOIRE:
                return new PavlovAleatoire();
            case ADAPTATIF:
                return new Adaptatif();
            case GRADUEL:
                return new Graduel();
            case DONNANT_DONNANT_SOUPCONNEUX:
                return new DonnantDonnantSoupconneux();
            case RANCUNIER_DOUX:
                return new RancunierDoux();
            default:
                throw new IllegalArgumentException("Unknown strategy type: " + type);
        }
    }
}
