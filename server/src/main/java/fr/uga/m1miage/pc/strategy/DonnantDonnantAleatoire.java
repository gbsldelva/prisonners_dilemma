package fr.uga.m1miage.pc.strategy;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;

import java.util.List;

public class DonnantDonnantAleatoire implements Strategy {

    @Override
    public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
        // Vérifie si l'adversaire a fait des coups précédents
        if (UtilFunctions.listContainsMoves(opponentPreviousMoves)) {
            // Avec une probabilité de RANDOM_PROBABILITY, jouer un coup aléatoire
            if (UtilFunctions.eventIsVeryLikelyToHappen()) {
                return UtilFunctions.getRandomMove();
            } else {
                // Sinon, jouer comme le dernier coup de l'adversaire
                return opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
            }
        }

        // Si aucun coup précédent, jouer un coup aléatoire (ou initiale)
        return UtilFunctions.getRandomMove();
    }
}

