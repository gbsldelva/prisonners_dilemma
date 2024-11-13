package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.utils.UtilFunctions;

public class Graduel implements Strategy {

    @Override
    public String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves) {
        if (!UtilFunctions.listContainsMoves(opponentPreviousMoves)) {
            // Coopérer par défaut en début de partie
            return "c";
        }
        
        // Compter le nombre total de trahisons de l'adversaire
        int opponentBetrayals = (int) opponentPreviousMoves.stream().filter(move -> move.equals("t")).count();
        int myBetrayals = (int) myPreviousMoves.stream().filter(move -> move.equals("t")).count();
        
        // Vérifier si l'adversaire a trahi lors du dernier coup
        String lastOpponentMove = opponentPreviousMoves.get(opponentPreviousMoves.size() - 1);
        
        if (lastOpponentMove.equals("t")) {
            // Trahir autant de fois que l'adversaire a trahi au total
            if (myBetrayals < opponentBetrayals) {
                return "t";
            } else {
                // Après les trahisons, coopérer deux fois
                int recentMovesSize = Math.min(2, myPreviousMoves.size());
                long recentCooperations = myPreviousMoves.subList(myPreviousMoves.size() - recentMovesSize, myPreviousMoves.size())
                                                        .stream().filter(move -> move.equals("c")).count();
                if (recentCooperations < 2) {
                    return "c";
                }
            }
        }
        
        // Coopérer par défaut si aucune condition spéciale n'est remplie
        return "c";
    }
}
