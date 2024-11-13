package fr.uga.m1miage.pc.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import fr.uga.m1miage.pc.model.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

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
}
