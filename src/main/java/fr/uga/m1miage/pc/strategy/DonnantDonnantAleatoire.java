package fr.uga.m1miage.pc.strategy;

import fr.uga.m1miage.pc.utils.UtilFunctions;

import java.util.List;

public class DonnantDonnantAleatoire implements Strategy {

    @Override
    public String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves) {
        // V�rifie si l'adversaire a fait des coups pr�c�dents
        if (UtilFunctions.listContainsMoves(opponentPreviousMoves)) {
            // Avec une probabilit� de RANDOM_PROBABILITY, jouer un coup al�atoire
            if (UtilFunctions.eventIsVeryLikelyToHappen()) {
                return UtilFunctions.getRandomMove();
            } else {
                // Sinon, jouer comme le dernier coup de l'adversaire
                return opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
            }
        }

        // Si aucun coup pr�c�dent, jouer un coup al�atoire (ou initiale)
        return UtilFunctions.getRandomMove();
    }
}

