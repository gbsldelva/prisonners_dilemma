package fr.uga.m1miage.pc.infrastructure.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import fr.uga.m1miage.pc.infrastructure.controller.WebSocketController;
import fr.uga.m1miage.pc.domain.model.GameSession;
import fr.uga.m1miage.pc.domain.model.Invitation;
import fr.uga.m1miage.pc.domain.model.Player;
import fr.uga.m1miage.pc.domain.model.Result;

class NotificationServiceAdapterTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private NotificationServiceAdapter notificationServiceAdapter;

    private Player fromPlayer;
    private Player toPlayer;
    private Invitation invitation;
    private GameSession gameSession;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fromPlayer = new Player();
        fromPlayer.setUsername("Alice");
        toPlayer = new Player();
        toPlayer.setUsername("Bob");
        invitation = new Invitation(fromPlayer.getUsername(), toPlayer.getUsername(), 3);
        gameSession = new GameSession(fromPlayer, toPlayer);
        gameSession.setTotalIterations(3);
    }

    @Test
    void testNotifyInvitation() {
        notificationServiceAdapter.notifyInvitation(invitation);
        verify(messagingTemplate).convertAndSendToUser(
            eq(invitation.getToUsername()),
            eq("/queue/invitation"),
            eq(invitation.getFromPlayer())
        );
    }
    @Test
    void testNotifyGameStartWithUsername() {
        fromPlayer.setSessionId("sessionId1");
        WebSocketController.connectedPlayers.put(fromPlayer.getUsername(), fromPlayer);

        String message = "Game between Alice and the server started";
        notificationServiceAdapter.notifyGameStart(fromPlayer.getUsername(), message);

        verify(messagingTemplate).convertAndSendToUser(
            eq("Alice"),
            eq("/queue/gameStartHandler"),
            eq(message)
        );
    }

    @Test
    void testUpdateScore() {
        notificationServiceAdapter.updateScore(gameSession);

        Result expectedResult = new Result();
        expectedResult.setScore("Alice(0) - Bob(0)");
        expectedResult.setStatus("En cours");
        expectedResult.setParti("1/3");

        verify(messagingTemplate).convertAndSendToUser(
            eq(fromPlayer.getUsername()),
            eq("/queue/scoreUpdate"),
            eq(expectedResult.toJson())
        );

        verify(messagingTemplate).convertAndSendToUser(
                eq(toPlayer.getUsername()),
                eq("/queue/scoreUpdate"),
                eq(expectedResult.toJson())
        );
    }

    @Test
    void testEndGame() {
        notificationServiceAdapter.endGame(gameSession);

        Result expectedResult = new Result();
        expectedResult.setScore("Alice(0) - Bob(0)");
        expectedResult.setStatus("Fini");
        expectedResult.setParti("3/3");

        verify(messagingTemplate).convertAndSendToUser(
            eq(fromPlayer.getUsername()),
            eq("/queue/gameEnd"),
            eq(expectedResult.toJson())
        );
        verify(messagingTemplate).convertAndSendToUser(
                eq(toPlayer.getUsername()),
                eq("/queue/gameEnd"),
                eq(expectedResult.toJson())
        );
    }
}
