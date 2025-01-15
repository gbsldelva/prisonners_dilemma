package fr.uga.m1miage.pc.infrastructure.service;

import fr.uga.m1miage.pc.domain.port.output.NotificationPort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import fr.uga.m1miage.pc.infrastructure.controller.WebSocketController;
import fr.uga.m1miage.pc.domain.model.GameSession;
import fr.uga.m1miage.pc.domain.model.Invitation;
import fr.uga.m1miage.pc.domain.model.Player;
import fr.uga.m1miage.pc.domain.model.Result;

@Service
public class NotificationService implements NotificationPort {

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
                  messagingTemplate.convertAndSendToUser(player.getUsername(), "/queue/gameStartHandler", message);
          }
    }

    public void updateScore(GameSession session) {
        Result result = new Result();
        result.setScore(session.getScoreSummary());
        result.setStatus("En cours");
        result.setParti(session.getCurrentIteration() + 1 + "/" + session.getTotalIterations());
        messagingTemplate.convertAndSendToUser(session.getPlayer1().getUsername(),"/queue/scoreUpdate", result.toJson());
        messagingTemplate.convertAndSendToUser(session.getPlayer2().getUsername(),"/queue/scoreUpdate", result.toJson());
    }

    public void endGame(GameSession session) {
        Result result = new Result();
        result.setScore(session.getScoreSummary());
        result.setStatus("Fini");
        result.setParti(session.getTotalIterations() + "/" + session.getTotalIterations());
        messagingTemplate.convertAndSendToUser(session.getPlayer1().getUsername(),"/queue/gameEnd", result.toJson());
        messagingTemplate.convertAndSendToUser(session.getPlayer2().getUsername(),"/queue/gameEnd", result.toJson());
    }

    public void notifyPlayerReplacement(String username, String message) {
    Player player = WebSocketController.connectedPlayers.get(username);
    if (player != null && player.getSessionId() != null) {
        messagingTemplate.convertAndSendToUser(player.getUsername(), "/queue/playerReplacement", message);
    }
}
}
