package fr.uga.m1miage.pc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import fr.uga.m1miage.pc.model.Player;

class WebSocketControllerTest {
	@Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private WebSocketController webSocketController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messagingTemplate = Mockito.mock(SimpMessagingTemplate.class);
        webSocketController = new WebSocketController(messagingTemplate);
    }

    @Test
    void testHandleWebSocketConnectListener() {
        // Arrange
        SessionConnectedEvent event = Mockito.mock(SessionConnectedEvent.class);
        
        // Act
        webSocketController.handleWebSocketConnectListener(event);

        // Assert
        ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        
        // Verify the method call with explicitly captured arguments
        verify(messagingTemplate, times(1)).convertAndSend(destinationCaptor.capture(), payloadCaptor.capture());

        // Assert that the correct destination is used
        assertEquals("/topic/availablePlayers", destinationCaptor.getValue());
    }
    
    @Test
    void testConnectUser() {
        // Arrange
        Player player = new Player("user1", "session1");
        
        // Act
        webSocketController.connectUser(player);

        // Assert
        assertTrue(webSocketController.connectedUsers.contains(player.toJson()));
        assertTrue(WebSocketController.connectedPlayers.containsKey(player.getUsername()));
        assertEquals(player.getUsername(), webSocketController.userSessionMap.get("session1"));

        // Verify that updateAvailableUsers is called (sends message to /topic/availablePlayers)
        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/availablePlayers"), anySet());
    }
    @Test
    void testUsernamesAlreadyTaken() {
        // Arrange
        Player player1 = new Player("user1", "session1");
        Player player2 = new Player("user1", "session2");
        
        // Act
        webSocketController.connectUser(player1);
        webSocketController.connectUser(player2);

        // Assert
        assertTrue(webSocketController.connectedUsers.contains(player1.toJson()));
        assertTrue(WebSocketController.connectedPlayers.containsKey(player1.getUsername()));
        assertEquals(player1.getUsername(), webSocketController.userSessionMap.get("session1"));

        // Verify player2 is not added to connectedUsers or connectedPlayers
        assertFalse(WebSocketController.connectedPlayers.containsKey(player2.getSessionId()));
        assertNull(webSocketController.userSessionMap.get("session2"));

        // Verify that error message is sent to /user/queue/errors
        verify(messagingTemplate, times(1)).convertAndSendToUser(
          eq("session2"), 
          eq("/queue/errors"),
          eq("Le nom d'utilisateur \"user1\" est déjà pris.")
    );
    }
}
