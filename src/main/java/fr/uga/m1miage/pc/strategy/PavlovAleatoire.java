package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.utils.UtilFunctions;

public class PavlovAleatoire implements Strategy {

    @Override
    public String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves) {
        if (UtilFunctions.listContainsMoves(myPreviousMoves) && UtilFunctions.listContainsMoves(opponentPreviousMoves)) {
            String lastMyMove = myPreviousMoves.get(myPreviousMoves.size() - 1);
            String lastOpponentMove = opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);

            // Si 5 ou 3 points ont été obtenus au tour précédent
            if (UtilFunctions.get3or5pointsInLastMove(lastMyMove, lastOpponentMove)) {
                // Faire un choix aléatoire avec une faible probabilité
                if (!UtilFunctions.eventIsVeryLikelyToHappen()) {
                    return UtilFunctions.getRandomMove();
                }
                // Sinon, répéter le dernier choix
                return lastMyMove;
            }
        }
        
        // Choix aléatoire par défaut s'il n'y a pas assez d'historique ou pas de points 3 ou 5
        return UtilFunctions.getRandomMove();
    }
}
