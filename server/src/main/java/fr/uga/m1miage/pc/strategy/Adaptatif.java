package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;

public class Adaptatif implements Strategy {

    static final Decision[] INITIAL_SEQUENCE = {Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE, Decision.BETRAY,Decision.BETRAY, Decision.BETRAY, Decision.BETRAY, Decision.BETRAY};
    int moveIndex = 0;
    int coopScore = 0;
    int betrayScore = 0;
    
    @Override
    public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
        // Si nous n'avons pas encore joué tous les coups de la séquence initiale, jouer le coup suivant de la séquence.
        if (moveIndex < INITIAL_SEQUENCE.length) {
            return INITIAL_SEQUENCE[moveIndex++];
        }

        // Recalculer les scores moyens pour coopérer (c) et trahir (t)
        if (coopScore > betrayScore) {
            return Decision.COOPERATE;
        } else if (betrayScore > coopScore) {
            return Decision.BETRAY;
        } else {
            // Si les scores sont égaux, choisir aléatoirement entre "c" et "t".
            return UtilFunctions.getRandomMove();
        }
    }

    public void updateScores(String myMove, String opponentMove) {
        // Mettre à jour les scores pour coopérer et trahir
        if (myMove.equals("c") && opponentMove.equals("c")) {
            coopScore += 3;  // Coopération contre coopération
        } else if (myMove.equals("t") && opponentMove.equals("c")) {
            betrayScore += 5;  // Trahison contre coopération
        } else if (myMove.equals("t") && opponentMove.equals("t")) {
            betrayScore += 1;  // Trahison contre trahison
        }
    }
}
