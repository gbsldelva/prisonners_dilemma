package fr.uga.m1miage.pc.strategy;

import java.util.List;

import fr.uga.m1miage.pc.utils.UtilFunctions;

public class RancunierDoux implements Strategy {

    private int punishmentIndex = -1;  // Indique si nous sommes dans une s�quence punitive (et la position dans celle-ci)
    private static final String[] PUNISHMENT_SEQUENCE = {"t", "t", "t", "t", "c", "c"};

    @Override
    public String playNextMove(List<String> myPreviousMoves, List<String> opponentPreviousMoves) {
        if (punishmentIndex >= 0) {
            // Si nous sommes dans une s�quence punitive, continuer cette s�quence
            String nextMove = PUNISHMENT_SEQUENCE[punishmentIndex];
            punishmentIndex++;
            if (punishmentIndex >= PUNISHMENT_SEQUENCE.length) {
                // R�initialiser l'index de punition apr�s avoir termin� la s�quence
                punishmentIndex = -1;
            }
            return nextMove;
        }
        
        // Si l'adversaire a trahi au dernier coup, d�clencher la s�quence punitive
        if (UtilFunctions.listContainsMoves(opponentPreviousMoves) &&
            opponentPreviousMoves.get(opponentPreviousMoves.size() - 1).equals("t")) {
            punishmentIndex = 0;
            return PUNISHMENT_SEQUENCE[punishmentIndex++];
        }

        // Par d�faut, coop�rer
        return "c";
    }
}
