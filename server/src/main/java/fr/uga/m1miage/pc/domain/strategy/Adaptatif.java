package fr.uga.m1miage.pc.domain.strategy;

import java.util.List;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.utils.UtilFunctions;

public class Adaptatif implements Strategy {

    static final Decision[] INITIAL_SEQUENCE = {Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE, Decision.COOPERATE, Decision.BETRAY,Decision.BETRAY, Decision.BETRAY, Decision.BETRAY, Decision.BETRAY};
    int moveIndex = 0;
    int coopScore = 0;
    int betrayScore = 0;
    
    @Override
    public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
        // Si nous n'avons pas encore jou� tous les coups de la s�quence initiale, jouer le coup suivant de la s�quence.
        if (moveIndex < INITIAL_SEQUENCE.length) {
            return INITIAL_SEQUENCE[moveIndex++];
        }

        // Recalculer les scores moyens pour coop�rer (c) et trahir (t)
        if (coopScore > betrayScore) {
            return Decision.COOPERATE;
        } else if (betrayScore > coopScore) {
            return Decision.BETRAY;
        } else {
            // Si les scores sont �gaux, choisir al�atoirement entre "c" et "t".
            return UtilFunctions.getRandomMove();
        }
    }

    public void updateScores(String myMove, String opponentMove) {
        // Mettre � jour les scores pour coop�rer et trahir
        if (myMove.equals("c") && opponentMove.equals("c")) {
            coopScore += 3;  // Coop�ration contre coop�ration
        } else if (myMove.equals("t") && opponentMove.equals("c")) {
            betrayScore += 5;  // Trahison contre coop�ration
        } else if (myMove.equals("t") && opponentMove.equals("t")) {
            betrayScore += 1;  // Trahison contre trahison
        }
    }
}
