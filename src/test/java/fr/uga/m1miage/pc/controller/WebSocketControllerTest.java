package fr.uga.m1miage.pc.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WebSocketControllerTest {

    private WebSocketController webSocketController;
    private SimpMessagingTemplate messagingTemplate;

    @BeforeEach
    public void setup() {
        messagingTemplate = Mockito.mock(SimpMessagingTemplate.class);
        webSocketController = new WebSocketController(messagingTemplate);
    }

    @Test
    void testConnectUser() {
        // Arrange
        String username = "User1";

        // Act
        webSocketController.connect(username);

        // Assert
        ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Set<String>> payloadCaptor = ArgumentCaptor.forClass(Set.class);
        verify(messagingTemplate, times(1)).convertAndSend(destinationCaptor.capture(), payloadCaptor.capture());
        
        assertEquals("/topic/availablePlayers", destinationCaptor.getValue());
        assertEquals(Set.of(username), payloadCaptor.getValue());
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
}
