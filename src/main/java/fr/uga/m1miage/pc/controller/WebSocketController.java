package fr.uga.m1miage.pc.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import fr.uga.m1miage.pc.model.Player;

import org.springframework.context.event.EventListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final Set<String> connectedUsers = new HashSet<>();
    private final Map<String, String> userSessionMap = new HashMap<>();
    protected static Map<String, Player> connectedPlayers = new HashMap<>();

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

	// Notify clients when a new user connects
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        // Notify all clients about the new user
        updateAvailableUsers();
    }

    // Handle user disconnect
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
            connectedPlayers.remove(username);
            userSessionMap.values().remove(username); // Remove from session map
            updateAvailableUsers(); // Call a method to update available users for other clients
        }
    }

    // Handle a new user connecting with their username and sessionId
    @MessageMapping("/connectUser")
    public void connectUser(@Payload Player player) {
        connectedUsers.add(player.toJson());
        connectedPlayers.put(player.getUsername(), player);
        userSessionMap.put(player.getSessionId(), player.getUsername());
        updateAvailableUsers();
    }

    private void updateAvailableUsers() {
        messagingTemplate.convertAndSend("/topic/availablePlayers", connectedUsers);
    }
}
