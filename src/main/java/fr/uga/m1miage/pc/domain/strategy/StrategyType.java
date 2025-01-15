package fr.uga.m1miage.pc.domain.strategy;

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
        return switch (strategyName) {
            case "DonnantDonnant" -> DONNANT_DONNANT;
            case "DonnantDonnantAleatoire" -> DONNANT_DONNANT_ALEATOIRE;
            case "DonnantDeuxDonnantAleatoire" -> DONNANT_DEUX_DONNANT_ALEATOIRE;
            case "DonnantDeuxDonnant" -> DONNANT_DEUX_DONNANT;
            case "SondeurNaif" -> SONDEUR_NAIF;
            case "SondeurRepentant" -> SONDEUR_REPENTANT;
            case "PacificateurNaif" -> PACIFICATEUR_NAIF;
            case "VraiPacificateur" -> VRAI_PACIFICATEUR;
            case "Aleatoire" -> ALEATOIRE;
            case "ToujoursTrahir" -> TOUJOURS_TRAHIR;
            case "ToujoursCooperer" -> TOUJOURS_COOPERER;
            case "Rancunier" -> RANCUNIER;
            case "Pavlov" -> PAVLOV;
            case "PavlovAleatoire" -> PAVLOV_ALEATOIRE;
            case "Adaptatif" -> ADAPTATIF;
            case "Graduel" -> GRADUEL;
            case "DonnantDonnantSoupconneux" -> DONNANT_DONNANT_SOUPCONNEUX;
            case "RancunierDoux" -> RANCUNIER_DOUX;
            default -> throw new IllegalArgumentException("Unknown strategy type: " + strategyName);
        };
    }
}
