package fr.uga.m1miage.pc.model;

public class TitForTatStrategy implements Strategy {

    @Override
    public String decideMove(GameSession session, Player player) {
        // First move: Cooperate
        if (session.getCurrentIteration() == 0) {
            return "c";  // First move is cooperation
        }

        if (player.equals(session.getPlayer1())) {
            // If player1 is using the strategy, mimic player2's last move
            return session.getPlayer2Choices().get(session.getCurrentIteration() - 1);
        } else {
            // If player2 is using the strategy, mimic player1's last move
            return session.getPlayer1Choices().get(session.getCurrentIteration() - 1);
        }
    }
}
