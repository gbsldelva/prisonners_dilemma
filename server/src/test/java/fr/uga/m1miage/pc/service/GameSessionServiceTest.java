package fr.uga.m1miage.pc.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.uga.m1miage.pc.model.*;
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

class GameSessionServiceTest {

	@Mock
    private NotificationService notificationService;
	
	@InjectMocks
    private GameSessionService gameSessionService;
    
    @InjectMocks
    private WebSocketController webSocketController;

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
        
        gameSessionService = new GameSessionService(notificationService, webSocketController);

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
        GameSession gameSession = gameSessionService.playAgainstServer(player1, 0);
        assertNull(gameSession);
    }

    @Test
    void testCreateSessionInvalidPlayers() {
        GameSession gameSession = gameSessionService.createSession(null, player2, 3);
        assertNull(gameSession);
    }
    
    @Test
    void testDisconectedPlayerShouldPlayNow() {
        // Cr�ez une nouvelle session avec les joueurs player1 et player2
        GameSession gameSession = new GameSession(player1, player2);

        // Ajoutez des choix pour les joueurs
        gameSession.getPlayer1Choices().add(Decision.COOPERATE);
        gameSession.getPlayer2Choices().add(Decision.BETRAY);
        
        // Testez la fonction avec le joueur 1 d�connect�
        boolean result = gameSessionService.disconectedPlayerShouldPlayNow(player1, gameSession);
        assertFalse(result); // Le joueur 2 devrait jouer maintenant

        gameSession.getPlayer2Choices().add(Decision.BETRAY);
        result = gameSessionService.disconectedPlayerShouldPlayNow(player1, gameSession);
        assertTrue(result); // Le joueur 2 devrait jouer maintenant
    }
    
    @Test
    void testGetActivePlayers() {
        // Get active players
        Set<String> activePlayers = gameSessionService.getActivePlayers();

        // Verify that both players are active
        assertEquals(3, activePlayers.size());
        assertTrue(activePlayers.contains(player1.getUsername()));
        assertTrue(activePlayers.contains(player2.getUsername()));
    }
    
}
