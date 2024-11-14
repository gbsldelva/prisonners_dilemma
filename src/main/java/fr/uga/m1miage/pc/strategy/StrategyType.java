package fr.uga.m1miage.pc.strategy;

public enum StrategyType {
    DONNANT_DONNANT,
    DONNANT_DONNANT_ALEATOIRE,
    DONNANT_DEUX_DONNANT_ALEATOIRE,
    DONNANT_DEUX_DONNANT,
    SONDEUR_NAIF,
    SONDEUR_REPENTANT,
    PACIFICATEUR_NAIF,
    VRAI_PACIFICATEUR,
    ALEATOIRE,
    TOUJOURS_TRAHIR,
    TOUJOURS_COOPERER,
    RANCUNIER,
    PAVLOV,
    PAVLOV_ALEATOIRE,
    ADAPTATIF,
    GRADUEL,
    DONNANT_DONNANT_SOUPCONNEUX,
    RANCUNIER_DOUX;

    public static StrategyType fromString(String strategyName) {
        switch (strategyName) {
            case "DonnantDonnant":
                return DONNANT_DONNANT;
            case "DonnantDonnantAleatoire":
                return DONNANT_DONNANT_ALEATOIRE;
            case "DonnantDeuxDonnantAleatoire":
                return DONNANT_DEUX_DONNANT_ALEATOIRE;
            case "DonnantDeuxDonnant":
                return DONNANT_DEUX_DONNANT;
            case "SondeurNaif":
                return SONDEUR_NAIF;
            case "SondeurRepentant":
                return SONDEUR_REPENTANT;
            case "PacificateurNaif":
                return PACIFICATEUR_NAIF;
            case "VraiPacificateur":
                return VRAI_PACIFICATEUR;
            case "Aleatoire":
                return ALEATOIRE;
            case "ToujoursTrahir":
                return TOUJOURS_TRAHIR;
            case "ToujoursCooperer":
                return TOUJOURS_COOPERER;
            case "Rancunier":
                return RANCUNIER;
            case "Pavlov":
                return PAVLOV;
            case "PavlovAleatoire":
                return PAVLOV_ALEATOIRE;
            case "Adaptatif":
                return ADAPTATIF;
            case "Graduel":
                return GRADUEL;
            case "DonnantDonnantSoupconneux":
                return DONNANT_DONNANT_SOUPCONNEUX;
            case "RancunierDoux":
                return RANCUNIER_DOUX;
            default:
                throw new IllegalArgumentException("Unknown strategy type: " + strategyName);
        }
    }
}
