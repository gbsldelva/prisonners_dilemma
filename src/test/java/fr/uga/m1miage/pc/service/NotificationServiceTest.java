package fr.uga.m1miage.pc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import fr.uga.m1miage.pc.controller.WebSocketController;
import fr.uga.m1miage.pc.model.GameSession;
import fr.uga.m1miage.pc.model.Invitation;
import fr.uga.m1miage.pc.model.Player;
import fr.uga.m1miage.pc.model.Result;

class NotificationServiceTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private NotificationService notificationService;

    private Player fromPlayer;
    private Player toPlayer;
    private Invitation invitation;
    private GameSession gameSession;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fromPlayer = new Player("Alice");
        toPlayer = new Player("Bob");
        invitation = new Invitation(fromPlayer.getId(), toPlayer.getId(), 3);
        gameSession = new GameSession(fromPlayer, toPlayer);
        gameSession.setTotalIterations(3);
    }

    @Test
    void testNotifyInvitation() {
        notificationService.notifyInvitation(invitation);
        verify(messagingTemplate).convertAndSendToUser(
            eq(invitation.getToUsername()),
            eq("/queue/invitation"),
            eq(invitation.getFromPlayer())
        );
    }
    @Test
    void testNotifyGameStartWithUsername() {
        fromPlayer.setSessionId("sessionId1");
        WebSocketController.connectedPlayers.put(fromPlayer.getId(), fromPlayer);

        String message = "Game between Alice and the server started";
        notificationService.notifyGameStart(fromPlayer.getId(), message);

        verify(messagingTemplate).convertAndSendToUser(
            eq("sessionId1"),
            eq("/queue/gameStartHandler"),
            eq(message)
        );
    }
    
    @Test
    void testNotifyGameStart() {
        String message = "Game between Alice and Bob started";
        notificationService.notifyGameStart(message);
        verify(messagingTemplate).convertAndSend(eq("/queue/gameStartHandler"), eq(message));
    }

    @Test
    void testUpdateScore() {
        notificationService.updateScore(gameSession);

        Result expectedResult = new Result();
        expectedResult.setScore("Alice(0) - Bob(0)");
        expectedResult.setStatus("En cours");
        expectedResult.setParti("1/3");

        verify(messagingTemplate).convertAndSend(
            eq("/queue/scoreUpdate"),
            eq(expectedResult.toJson())
        );
    }

    @Test
    void testEndGame() {
        notificationService.endGame(gameSession);

        Result expectedResult = new Result();
        expectedResult.setScore("Alice(0) - Bob(0)");
        expectedResult.setStatus("Fini");
        expectedResult.setParti("3/3");

        verify(messagingTemplate).convertAndSend(
            eq("/queue/gameEnd"),
            eq(expectedResult.toJson())
        );
    }
}
