package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;

public class DonnantDeuxDonnantAleatoire implements Strategy {
    @Override
    public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
        // Vérifie si l'adversaire a joué au moins deux coups précédents
        if (UtilFunctions.listContainsMoves(opponentPreviousMoves) && opponentPreviousMoves.size() >= 2) {
            // Vérifie si les deux derniers coups de l'adversaire sont identiques
            boolean twoLastMovesAreSame = opponentPreviousMoves.get(opponentPreviousMoves.size() - 1)
                                                              .equals(opponentPreviousMoves.get(opponentPreviousMoves.size() - 2));
            
            // Vérifie si la probabilité d'événement aléatoire est faible et si les deux derniers coups sont les mêmes
            if (twoLastMovesAreSame && UtilFunctions.eventIsVeryLikelyToHappen()) {
                return UtilFunctions.getRandomMove();  // Si conditions remplies, jouer un coup aléatoire
            }
            return opponentPreviousMoves.get(opponentPreviousMoves.size() - 1); // Sinon, répéter le dernier coup de l'adversaire
        }
        // Si aucun coup précédent, jouer un coup aléatoire par défaut
        return UtilFunctions.getRandomMove();
    }
}

