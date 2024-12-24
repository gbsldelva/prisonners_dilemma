package fr.uga.m1miage.pc.strategy;

public class StrategyFactory {
	
	private StrategyFactory() {}
	
    public static Strategy createStrategy(StrategyType type) {
    	if (type == null)
    		throw new IllegalArgumentException("Unknown strategy type:");

        return switch (type) {
            case DONNANT_DONNANT -> new DonnantDonnant();
            case DONNANT_DONNANT_ALEATOIRE -> new DonnantDonnantAleatoire();
            case DONNANT_DEUX_DONNANT_ALEATOIRE -> new DonnantDeuxDonnantAleatoire();
            case DONNANT_DEUX_DONNANT -> new DonnantDeuxDonnant();
            case SONDEUR_NAIF -> new SondeurNaif();
            case SONDEUR_REPENTANT -> new SondeurRepentant();
            case PACIFICATEUR_NAIF -> new PacificateurNaif();
            case VRAI_PACIFICATEUR -> new VraiPacificateur();
            case ALEATOIRE -> new Aleatoire();
            case TOUJOURS_TRAHIR -> new ToujoursTrahir();
            case TOUJOURS_COOPERER -> new ToujoursCooperer();
            case RANCUNIER -> new Rancunier();
            case PAVLOV -> new Pavlov();
            case PAVLOV_ALEATOIRE -> new PavlovAleatoire();
            case ADAPTATIF -> new Adaptatif();
            case GRADUEL -> new Graduel();
            case DONNANT_DONNANT_SOUPCONNEUX -> new DonnantDonnantSoupconneux();
            case RANCUNIER_DOUX -> new RancunierDoux();
        };
    }
}
