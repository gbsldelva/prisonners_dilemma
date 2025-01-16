package fr.uga.m1miage.pc.domain.strategy;

import java.util.List;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.utils.UtilFunctions;

public class DonnantDeuxDonnantAleatoire implements Strategy {
    @Override
    public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
        // V�rifie si l'adversaire a jou� au moins deux coups pr�c�dents
        if (UtilFunctions.listContainsMoves(opponentPreviousMoves) && opponentPreviousMoves.size() >= 2) {
            // V�rifie si les deux derniers coups de l'adversaire sont identiques
            boolean twoLastMovesAreSame = opponentPreviousMoves.get(opponentPreviousMoves.size() - 1)
                                                              .equals(opponentPreviousMoves.get(opponentPreviousMoves.size() - 2));
            
            // V�rifie si la probabilit� d'�v�nement al�atoire est faible et si les deux derniers coups sont les m�mes
            if (twoLastMovesAreSame && UtilFunctions.eventIsVeryLikelyToHappen()) {
                return UtilFunctions.getRandomMove();  // Si conditions remplies, jouer un coup al�atoire
            }
            return opponentPreviousMoves.get(opponentPreviousMoves.size() - 1); // Sinon, r�p�ter le dernier coup de l'adversaire
        }
        // Si aucun coup pr�c�dent, jouer un coup al�atoire par d�faut
        return UtilFunctions.getRandomMove();
    }
}

