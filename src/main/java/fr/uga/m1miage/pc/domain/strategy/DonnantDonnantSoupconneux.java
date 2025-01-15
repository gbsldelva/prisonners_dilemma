package fr.uga.m1miage.pc.domain.strategy;

import java.util.List;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.utils.UtilFunctions;

public class DonnantDonnantSoupconneux implements Strategy {

    @Override
    public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
        // Commencer par trahir si aucun coup n'a �t� jou� auparavant
        if (!UtilFunctions.listContainsMoves(opponentPreviousMoves)) {
            return Decision.BETRAY;
        }
        
        // Copier le dernier coup de l'adversaire
        return opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
    }
}
