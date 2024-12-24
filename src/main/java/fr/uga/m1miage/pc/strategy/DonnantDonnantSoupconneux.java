package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;

public class DonnantDonnantSoupconneux implements Strategy {

    @Override
    public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
        // Commencer par trahir si aucun coup n'a été joué auparavant
        if (!UtilFunctions.listContainsMoves(opponentPreviousMoves)) {
            return Decision.BETRAY;
        }
        
        // Copier le dernier coup de l'adversaire
        return opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
    }
}
