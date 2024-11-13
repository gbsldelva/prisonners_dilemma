package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.utils.UtilFunctions;

public class VraiPacificateur implements Strategy {

    @Override
    public String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves) {
        if (opponentPreviousMoves == null || opponentPreviousMoves.size() < 2) {
            // Coopérer par défaut si pas assez de coups précédents pour évaluer la séquence
            return "c";
        }
        
        String lastOpponentMove = opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
        String secondLastOpponentMove = opponentPreviousMoves.get(opponentPreviousMoves.size() - 2);
        
        // Vérifier si l'adversaire a trahi deux fois de suite
        if (lastOpponentMove.equals("t") && secondLastOpponentMove.equals("t")) {
            // Tenter de faire la paix en coopérant avec une faible probabilité
            if (!UtilFunctions.eventIsVeryLikelyToHappen()) {
                return "c";  // Coopérer pour apaiser
            }
            return "t";  // Trahir par défaut après une double trahison de l'adversaire
        }

        // Coopérer si l'adversaire n'a pas trahi deux fois de suite
        return "c";
    }
}
