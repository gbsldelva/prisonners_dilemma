package fr.uga.m1miage.pc.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import fr.uga.m1miage.pc.controller.WebSocketController;
import fr.uga.m1miage.pc.model.GameSession;
import fr.uga.m1miage.pc.model.Invitation;
import fr.uga.m1miage.pc.model.Player;
import fr.uga.m1miage.pc.model.Result;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyInvitation(Invitation invitation) {
        messagingTemplate.convertAndSendToUser(
            invitation.getToUsername(),
            "/queue/invitation",
            invitation.getFromPlayer()
        );
    }
    
    public void sendCompetitionResult(String sessionId, String message) {
        messagingTemplate.convertAndSendToUser(
            sessionId,
            "/queue/competitionResult",
            message
        );
    }

    public void notifyGameStart(String username, String message) {
         Player player = WebSocketController.connectedPlayers.get(username);
              if (player != null && player.getSessionId() != null) {
                  messagingTemplate.convertAndSendToUser(player.getSessionId(), "/queue/gameStartHandler", message);
          }
    }

      public void notifyGameStart(String message) {
        messagingTemplate.convertAndSend("/queue/gameStartHandler", message);
    }

    public void updateScore(GameSession session) {
        Result result = new Result();
        result.setScore(session.getScoreSummary());
        result.setStatus("En cours");
        result.setParti(session.getCurrentIteration() + 1 + "/" + session.getTotalIterations());
        messagingTemplate.convertAndSend("/queue/scoreUpdate", result.toJson());
    }

    public void endGame(GameSession session) {
        Result result = new Result();
        result.setScore(session.getScoreSummary());
        result.setStatus("Termin�");
        result.setParti(session.getTotalIterations() + "/" + session.getTotalIterations());
        messagingTemplate.convertAndSend("/queue/gameEnd", result.toJson());
    }

    public void notifyPlayerReplacement(String username, String message) {
    Player player = WebSocketController.connectedPlayers.get(username);
    if (player != null && player.getSessionId() != null) {
        messagingTemplate.convertAndSendToUser(player.getSessionId(), "/queue/playerReplacement", message);
    }
}
}
