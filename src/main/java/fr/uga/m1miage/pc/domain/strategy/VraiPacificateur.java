package fr.uga.m1miage.pc.domain.strategy;

import java.util.List;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.utils.UtilFunctions;

public class VraiPacificateur implements Strategy {

    @Override
    public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
        if (opponentPreviousMoves == null || opponentPreviousMoves.size() < 2) {
            // Coop�rer par d�faut si pas assez de coups pr�c�dents pour �valuer la s�quence
            return Decision.COOPERATE;
        }
        
        Decision lastOpponentMove = opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
        Decision secondLastOpponentMove = opponentPreviousMoves.get(opponentPreviousMoves.size() - 2);
        
        // V�rifier si l'adversaire a trahi deux fois de suite
        if (lastOpponentMove == Decision.BETRAY && secondLastOpponentMove == Decision.BETRAY) {
            // Tenter de faire la paix en coop�rant avec une faible probabilit�
            if (!UtilFunctions.eventIsVeryLikelyToHappen()) {
                return Decision.COOPERATE;  // Coop�rer pour apaiser
            }
            return Decision.BETRAY;  // Trahir par d�faut apr�s une double trahison de l'adversaire
        }

        // Coop�rer si l'adversaire n'a pas trahi deux fois de suite
        return Decision.COOPERATE;
    }
}
