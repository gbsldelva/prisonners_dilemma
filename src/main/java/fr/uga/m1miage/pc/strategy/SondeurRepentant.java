package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.utils.UtilFunctions;

public class SondeurRepentant implements Strategy {

    @Override
    public String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves) {
        if (!UtilFunctions.listContainsMoves(opponentPreviousMoves)) {
            return "c";
        }

        String lastOpponentMove = opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
        String lastMyMove = !myPreviousMoves.isEmpty() ? myPreviousMoves.get(myPreviousMoves.size() - 1) : "c";

        // Si l'adversaire a trahi en réponse à une de nos trahisons, se montrer repentant
        if (lastMyMove.equals("t") && lastOpponentMove.equals("t")) {
            return "c";  // Coopérer pour montrer le repentir
        }

        // Décider si on effectue un test de trahison (probabilité d’environ 10 %)
        if (UtilFunctions.eventIsVeryLikelyToHappen()) {
            return lastOpponentMove;  // Imiter le dernier coup de l'adversaire
        } else {
            return "t";  // Trahir pour tester la réaction de l’adversaire
        }
    }
}

