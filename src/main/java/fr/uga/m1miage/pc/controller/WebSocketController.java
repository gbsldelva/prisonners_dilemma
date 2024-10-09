package fr.uga.m1miage.pc.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.context.event.EventListener;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final Set<String> connectedUsers = new HashSet<>();

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        // Notify all clients about the new user
        updateAvailableUsers();
    }

 // This method is called when a WebSocket session is disconnected
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // Retrieve the username from the session
        String username = null;
        Map<String, Object> attributes = headerAccessor.getSessionAttributes();
        if (attributes != null)
        	username = (String) attributes.get("username");

        if (username != null) {
            connectedUsers.remove(username); // Remove user from connected users
            updateAvailableUsers(); // Call a method to update available users for other clients
        }
    }

    @MessageMapping("/connectUser")
    public void connect(String username) {
        connectedUsers.add(username);
        updateAvailableUsers();
    }

    private void updateAvailableUsers() {
        messagingTemplate.convertAndSend("/topic/availablePlayers", connectedUsers);
    }
}
