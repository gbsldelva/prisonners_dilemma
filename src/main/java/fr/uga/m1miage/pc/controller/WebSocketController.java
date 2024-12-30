package fr.uga.m1miage.pc.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.uga.m1miage.pc.model.Player;
import fr.uga.m1miage.pc.service.GameSessionService;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GameSessionService gameSessionService; 
    final Set<String> connectedUsers = new HashSet<>();
    final Map<String, String> userSessionMap = new HashMap<>();
    public static Map<String, Player> connectedPlayers = new HashMap<>();

    public WebSocketController(SimpMessagingTemplate messagingTemplate, @Lazy GameSessionService gameSessionService) {
        this.messagingTemplate = messagingTemplate;
        this.gameSessionService = gameSessionService;
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
    if (connectedPlayers.containsKey(player.getId())) {
        messagingTemplate.convertAndSendToUser( player.getSessionId(), "/queue/errors", 
            "Le nom d'utilisateur \"" + player.getId() + "\" est n'est plus disponible.");
    } else {
        connectedUsers.add(player.toJson());
        connectedPlayers.put(player.getId(), player);
        userSessionMap.put(player.getSessionId(), player.getId());
        updateAvailableUsers();
    }
    }
    private void updateAvailableUsers() {
    Set<String> activePlayers = gameSessionService.getActivePlayers();
    Set<String> availableUsers = connectedUsers.stream()
        .filter(userJson -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Player player = mapper.readValue(userJson, Player.class);
                return !activePlayers.contains(player.getId());
            } catch (Exception e) {
                return false;
            }
        })
        .collect(Collectors.toSet());

    messagingTemplate.convertAndSend("/topic/availablePlayers", availableUsers);
    }

    public void refreshAvailableUsers() {
        updateAvailableUsers();
    }
}
