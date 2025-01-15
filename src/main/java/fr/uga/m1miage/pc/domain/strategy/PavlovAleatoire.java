package fr.uga.m1miage.pc.domain.strategy;

import java.util.List;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.utils.UtilFunctions;

public class PavlovAleatoire implements Strategy {

    @Override
    public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
        if (UtilFunctions.listContainsMoves(myPreviousMoves) && UtilFunctions.listContainsMoves(opponentPreviousMoves)) {
            Decision lastMyMove = myPreviousMoves.get(myPreviousMoves.size() - 1);
            Decision lastOpponentMove = opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);

            // Si 5 ou 3 points ont �t� obtenus au tour pr�c�dent
            if (UtilFunctions.get3or5pointsInLastMove(lastMyMove, lastOpponentMove)) {
                // Faire un choix al�atoire avec une faible probabilit�
                if (!UtilFunctions.eventIsVeryLikelyToHappen()) {
                    return UtilFunctions.getRandomMove();
                }
                // Sinon, r�p�ter le dernier choix
                return lastMyMove;
            }
        }
        
        // Choix al�atoire par d�faut s'il n'y a pas assez d'historique ou pas de points 3 ou 5
        return UtilFunctions.getRandomMove();
    }
}
