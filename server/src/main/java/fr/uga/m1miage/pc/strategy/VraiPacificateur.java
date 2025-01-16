package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;

public class VraiPacificateur implements Strategy {

    @Override
    public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
        if (opponentPreviousMoves == null || opponentPreviousMoves.size() < 2) {
            // Coopérer par défaut si pas assez de coups précédents pour évaluer la séquence
            return Decision.COOPERATE;
        }
        
        Decision lastOpponentMove = opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
        Decision secondLastOpponentMove = opponentPreviousMoves.get(opponentPreviousMoves.size() - 2);
        
        // Vérifier si l'adversaire a trahi deux fois de suite
        if (lastOpponentMove == Decision.BETRAY && secondLastOpponentMove == Decision.BETRAY) {
            // Tenter de faire la paix en coopérant avec une faible probabilité
            if (!UtilFunctions.eventIsVeryLikelyToHappen()) {
                return Decision.COOPERATE;  // Coopérer pour apaiser
            }
            return Decision.BETRAY;  // Trahir par défaut après une double trahison de l'adversaire
        }

        // Coopérer si l'adversaire n'a pas trahi deux fois de suite
        return Decision.COOPERATE;
    }
}
