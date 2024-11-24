package fr.uga.m1miage.pc.service;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

import fr.uga.m1miage.pc.controller.WebSocketController;
import fr.uga.m1miage.pc.model.ChoiceMessage;
import fr.uga.m1miage.pc.model.GameSession;
import fr.uga.m1miage.pc.model.Invitation;
import fr.uga.m1miage.pc.model.InvitationAnswer;
import fr.uga.m1miage.pc.model.Player;

class GameSessionServiceTest {

	@Mock
    private NotificationService notificationService;

    @InjectMocks
    private GameSessionService gameSessionService;

    private Player player1;
    private Player player2;
    private GameSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        player1 = new Player("player1");
        player2 = new Player("player2");
        session = new GameSession(player1, player2);

        // Mock static method in WebSocketController for connected players
        Map<String, Player> connectedPlayers = new HashMap<>();
        connectedPlayers.put(player1.getUsername(), player1);
        connectedPlayers.put(player2.getUsername(), player2);

        WebSocketController.connectedPlayers = connectedPlayers;
    }

    @Test
    void testHandleInvitation() {
        Invitation invitation = new Invitation(player1.getUsername(), player2.getUsername(), 5);
        
        gameSessionService.handleInvitation(invitation);
        
        assertTrue(gameSessionService.invitationIterationMap.containsKey(player1.getUsername() + "&" + player2.getUsername()));
        verify(notificationService, times(1)).notifyInvitation(invitation);
    }

    @Test
    void testHandleInvitationAnswerConfirmed() {
        InvitationAnswer answer = new InvitationAnswer("player1", "player2", "confirmed");

        gameSessionService.handleInvitationAnswer(answer);

        verify(notificationService, times(1)).notifyGameStart(answer.getMessage());
        assertNotNull(gameSessionService.findGameSession("player1"));
    }

    @Test
    void testHandleInvitationAnswerRejected() {
        InvitationAnswer answer = new InvitationAnswer("player1", "player2", "rejected");

        gameSessionService.handleInvitationAnswer(answer);

        verify(notificationService, times(1)).notifyGameStart(answer.getMessage());
    }

    @Test
    void testPairPlayers() {
        Invitation invitation = new Invitation(player1.getUsername(), player2.getUsername(), 3);
        gameSessionService.handleInvitation(invitation);

        gameSessionService.pairPlayers("player1", "player2");

        GameSession createdSession = gameSessionService.findGameSession("player1");
        assertNotNull(createdSession);
        assertEquals(3, createdSession.getTotalIterations());
    }

    @Test
    void testHandleChoiceRoundComplete() {
        // Setup session with players
        gameSessionService.createSession(player1, player2, 3);

        ChoiceMessage choice1 = new ChoiceMessage("player1", "c");
        ChoiceMessage choice2 = new ChoiceMessage("player2", "t");

        gameSessionService.handleChoice(choice1);
        gameSessionService.handleChoice(choice2);

        session = gameSessionService.findGameSession("player1");
        assertNotNull(session);
        assertEquals(1, session.getCurrentIteration());
        verify(notificationService, times(1)).updateScore(session);
    }

    @Test
    void testHandleChoiceGameOver() {
        
        gameSessionService.clearActiveGames();
        gameSessionService.createSession(player1, player2, 1);

        ChoiceMessage choice1 = new ChoiceMessage("player1", "c");
        ChoiceMessage choice2 = new ChoiceMessage("player2", "c");

        gameSessionService.handleChoice(choice1);
        gameSessionService.handleChoice(choice2);

        session = gameSessionService.findGameSession("player1");
        assertNull(session);
    }

    @Test
    void testEndGame() {
        gameSessionService.createSession(player1, player2, 1);
        
        gameSessionService.endGame(session);
        
        assertNull(gameSessionService.findGameSession("player1"));
        verify(notificationService, times(1)).endGame(session);
    }
    
    @Test
    void testPairingPlayers() {
        session = gameSessionService.createSession(player1, player2, 5);
        assertNotNull(session);
        assertEquals(5, session.getTotalIterations());
        assertTrue(session.containsPlayer(player1.getUsername()));
        assertTrue(session.containsPlayer(player2.getUsername()));
    }

    @Test
    void testFindGameSession() {
        session = gameSessionService.createSession(player1, player2, 5);
        GameSession foundSession = gameSessionService.findGameSession(player1.getUsername());
        assertEquals(session, foundSession);
    }
    
    @Test
    void testPlayAgainstServer() {
        int iterations = 5;

        GameSession createdSession = gameSessionService.playAgainstServer(player1, iterations);

        assertNotNull(createdSession);
        assertEquals(iterations, createdSession.getTotalIterations());
        assertTrue(createdSession.containsPlayer("player1"));
        assertEquals("Ordinateur", createdSession.getPlayer2().getUsername());
        assertTrue(createdSession.getPlayer2().isServer());

        verify(notificationService, times(1)).notifyGameStart(eq("player1"), anyString());
    }

    @Test
    void testPlayAgainstServerInvalidIterations() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> gameSessionService.playAgainstServer(player1, 0)
        );
        assertEquals("Le nombre d'itérations doit être positif.", exception.getMessage());
    }

    @Test
    void testCreateSessionInvalidPlayers() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> gameSessionService.createSession(null, player2, 3)
        );
        assertEquals("Les joueurs ne peuvent pas être nuls.", exception.getMessage());
    }
    
}
