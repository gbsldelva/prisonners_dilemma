package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;

public class Graduel implements Strategy {

    @Override
    public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
        if (!UtilFunctions.listContainsMoves(opponentPreviousMoves)) {
            // Coopérer par défaut en début de partie
            return Decision.COOPERATE;
        }
        
        // Compter le nombre total de trahisons de l'adversaire
        int opponentBetrayals = (int) opponentPreviousMoves.stream().filter(move -> move == Decision.BETRAY).count();
        int myBetrayals = (int) myPreviousMoves.stream().filter(move ->  move == Decision.BETRAY).count();
        
        // Vérifier si l'adversaire a trahi lors du dernier coup
        Decision lastOpponentMove = opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
        
        if (lastOpponentMove == Decision.BETRAY) {
            // Trahir autant de fois que l'adversaire a trahi au total
            if (myBetrayals < opponentBetrayals) {
                return Decision.BETRAY;
            } else {
                // Après les trahisons, coopérer deux fois
                int recentMovesSize = Math.min(2, myPreviousMoves.size());
                long recentCooperations = myPreviousMoves.subList(myPreviousMoves.size() - recentMovesSize, myPreviousMoves.size())
                                                        .stream().filter(move -> move == Decision.BETRAY).count();
                if (recentCooperations < 2) {
                    return Decision.COOPERATE;
                }
            }
        }

        // Coopérer par défaut si aucune condition spéciale n'est remplie
        return Decision.COOPERATE;
    }
}
