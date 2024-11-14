package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.utils.UtilFunctions;

public class VraiPacificateur implements Strategy {

    @Override
    public String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves) {
        if (opponentPreviousMoves == null || opponentPreviousMoves.size() < 2) {
            // Coop�rer par d�faut si pas assez de coups pr�c�dents pour �valuer la s�quence
            return "c";
        }
        
        String lastOpponentMove = opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
        String secondLastOpponentMove = opponentPreviousMoves.get(opponentPreviousMoves.size() - 2);
        
        // V�rifier si l'adversaire a trahi deux fois de suite
        if (lastOpponentMove.equals("t") && secondLastOpponentMove.equals("t")) {
            // Tenter de faire la paix en coop�rant avec une faible probabilit�
            if (!UtilFunctions.eventIsVeryLikelyToHappen()) {
                return "c";  // Coop�rer pour apaiser
            }
            return "t";  // Trahir par d�faut apr�s une double trahison de l'adversaire
        }

        // Coop�rer si l'adversaire n'a pas trahi deux fois de suite
        return "c";
    }
}
