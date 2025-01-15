package fr.uga.m1miage.pc.domain.port.output;

import fr.uga.m1miage.pc.domain.model.GameSession;
import fr.uga.m1miage.pc.domain.model.Invitation;

public interface NotificationPort {
    void notifyInvitation(Invitation invitation);
    void sendCompetitionResult(String sessionId, String message);
    void notifyGameStart(String username, String message);
    void updateScore(GameSession session);
    void endGame(GameSession session);
    void notifyPlayerReplacement(String username, String message);
}
