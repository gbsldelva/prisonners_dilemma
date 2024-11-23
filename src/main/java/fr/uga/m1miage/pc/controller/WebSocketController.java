package fr.uga.m1miage.pc.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import fr.uga.m1miage.pc.model.Player;

import org.springframework.context.event.EventListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    final Set<String> connectedUsers = new HashSet<>();
    final Map<String, String> userSessionMap = new HashMap<>();
    public static Map<String, Player> connectedPlayers = new HashMap<>();

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

	// Notify clients when a new user connects
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        // Notify all clients about the new user
        updateAvailableUsers();
    }

    // Handle a new user connecting with their username and sessionId
    @MessageMapping("/connectUser")
    public void connectUser(@Payload Player player) {
    if (connectedPlayers.containsKey(player.getUsername())) {
        messagingTemplate.convertAndSendToUser( player.getSessionId(), "/queue/errors", 
            "Le nom d'utilisateur \"" + player.getUsername() + "\" est déjà pris.");
    } else {
        connectedUsers.add(player.toJson());
        connectedPlayers.put(player.getUsername(), player);
        userSessionMap.put(player.getSessionId(), player.getUsername());
        updateAvailableUsers();
    }
    }

    private void updateAvailableUsers() {
        messagingTemplate.convertAndSend("/topic/availablePlayers", connectedUsers);
    }
}
