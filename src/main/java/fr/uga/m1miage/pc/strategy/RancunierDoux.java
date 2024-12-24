package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;

public class RancunierDoux implements Strategy {

    private int punishmentIndex = -1;  // Indique si nous sommes dans une séquence punitive (et la position dans celle-ci)
    private static final Decision[] PUNISHMENT_SEQUENCE = {Decision.BETRAY, Decision.BETRAY, Decision.BETRAY, Decision.BETRAY, Decision.COOPERATE, Decision.COOPERATE};

    @Override
    public Decision playNextMove(List<Decision> myPreviousMoves, List<Decision> opponentPreviousMoves) {
        if (punishmentIndex >= 0) {
            // Si nous sommes dans une séquence punitive, continuer cette séquence
            Decision nextMove = PUNISHMENT_SEQUENCE[punishmentIndex];
            punishmentIndex++;
            if (punishmentIndex >= PUNISHMENT_SEQUENCE.length) {
                // Réinitialiser l'index de punition après avoir terminé la séquence
                punishmentIndex = -1;
            }
            return nextMove;
        }
        
        // Si l'adversaire a trahi au dernier coup, déclencher la séquence punitive
        if (UtilFunctions.listContainsMoves(opponentPreviousMoves) &&
            opponentPreviousMoves.get(opponentPreviousMoves.size() - 1) == Decision.BETRAY) {
            punishmentIndex = 0;
            return PUNISHMENT_SEQUENCE[punishmentIndex++];
        }

        // Par défaut, coopérer
        return Decision.COOPERATE;
    }
}
