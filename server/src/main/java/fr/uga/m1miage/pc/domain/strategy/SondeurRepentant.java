package fr.uga.m1miage.pc.domain.strategy;

import java.util.List;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.utils.UtilFunctions;

public class SondeurRepentant implements Strategy {

    @Override
    public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
        if (!UtilFunctions.listContainsMoves(opponentPreviousMoves)) {
            return Decision.COOPERATE;
        }

        Decision lastOpponentMove = opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
        Decision lastMyMove = !myPreviousMoves.isEmpty() ? myPreviousMoves.get(myPreviousMoves.size() - 1) : Decision.COOPERATE;

        // Si l'adversaire a trahi en réponse à une de nos trahisons, se montrer repentant
        if (lastMyMove == Decision.BETRAY && lastOpponentMove == Decision.BETRAY) {
            return Decision.COOPERATE;  // Coopérer pour montrer le repentir
        }

        // Décider si on effectue un test de trahison (probabilité d’environ 10 %)
        if (UtilFunctions.eventIsVeryLikelyToHappen()) {
            return lastOpponentMove;  // Imiter le dernier coup de l'adversaire
        } else {
            return Decision.BETRAY;  // Trahir pour tester la réaction de l’adversaire
        }
    }
}

