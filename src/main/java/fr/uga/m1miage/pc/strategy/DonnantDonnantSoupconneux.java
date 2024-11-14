package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.utils.UtilFunctions;

public class DonnantDonnantSoupconneux implements Strategy {

    @Override
    public String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves) {
        // Commencer par trahir si aucun coup n'a �t� jou� auparavant
        if (!UtilFunctions.listContainsMoves(opponentPreviousMoves)) {
            return "t";
        }
        
        // Copier le dernier coup de l'adversaire
        return opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
    }
}
