package fr.uga.m1miage.pc.service;

import fr.uga.m1miage.pc.controller.WebSocketController;
import fr.uga.m1miage.pc.model.*;
import fr.uga.m1miage.pc.strategy.Strategy;
import fr.uga.m1miage.pc.strategy.VraiPacificateur;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class GameSessionService {

    private final NotificationService notificationService;
    public final Map<String, Integer> invitationIterationMap = new LinkedHashMap<>();
    private static final ConcurrentMap<String, GameSession> activeGames = new ConcurrentHashMap<>();

    public GameSessionService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    void pairPlayers(String player1Username, String player2Username) {
        Player player1 = WebSocketController.connectedPlayers.get(player1Username);
        Player player2 = WebSocketController.connectedPlayers.get(player2Username);
        String key = player1.getUsername() + "&" + player2.getUsername();
        
        createSession(player1, player2, invitationIterationMap.get(key));
    }
    
    public GameSession playAgainstServer(Player player, int iterations) {
        // Crée une session de jeu avec le joueur humain et un joueur serveur utilisant la stratégie donnée
        Player server = new Player("Ordinateur", null);  // Crée un joueur "serveur"
        server.setServer(true);
        GameSession session = new GameSession(player, server);
        Strategy serverStrategy = new VraiPacificateur();
        server.setStrategy(serverStrategy);
        session.setTotalIterations(iterations);
        
        activeGames.put(generateSessionKey(player, server), session);
        return session;
    }
    
    public GameSession createSession(Player player1, Player player2, int iterations) {
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Both players must be provided to create a game session.");
        }
        GameSession session = new GameSession(player1, player2);
        session.setTotalIterations(iterations);
        
		activeGames.put(generateSessionKey(player1, player2), session);
        return session;
    }
    
    private String generateSessionKey(Player player1, Player player2) {
        return player1.getUsername() + "VS" + player2.getUsername();
    }
    
	public void handleInvitation(Invitation invitation) {
        invitationIterationMap.put(invitation.getFromPlayer() + "&" + invitation.getToUsername(), invitation.getIteration());
        notificationService.notifyInvitation(invitation);
    }

    public void handleInvitationAnswer(InvitationAnswer answer) {
        notificationService.notifyGameStart(answer.getMessage());
        if ("confirmed".equals(answer.getMessage())) {
            pairPlayers(answer.getPlayerUsername(), answer.getOponentUsername());
        }
    }
    
    public void handleChoice(ChoiceMessage choiceMessage) {
        GameSession session = findGameSession(choiceMessage.getUsername());
        if (session == null) return;

        // Détermine quel joueur fait le choix (le joueur 1 est celui qui a envoyé le message)
        Player currentPlayer = session.getPlayer1();
        Player player2 = session.getPlayer2(); // Le joueur 2 est toujours le serveur

        // Ajoute le choix du joueur
        if (currentPlayer.getUsername().equals(choiceMessage.getUsername())) {
            session.getPlayer1Choices().add(choiceMessage.getChoice());
        } else {
            session.getPlayer2Choices().add(choiceMessage.getChoice());
        }

        // Si le serveur est le 2e joueur, il fait son choix en fonction de sa stratégie
        if (player2.isServer()) {
            String serverChoice = player2.getStrategy().playNextMove(session.getPlayer1Choices(), session.getPlayer2Choices());
            session.getPlayer2Choices().add(serverChoice);
        }

        // Vérifie si la manche est terminée et met à jour ou termine la partie
        if (session.isRoundComplete()) {
            session.incrementIteration();
            if (session.isGameOver()) {
                endGame(session);
            } else {
                notificationService.updateScore(session);
            }
        }
    }


    GameSession findGameSession(String username) {
        return activeGames.values().stream()
                .filter(session -> session.containsPlayer(username))
                .findFirst()
                .orElse(null);
    }

    void endGame(GameSession session) {
        notificationService.endGame(session);
        activeGames.values().removeIf(s -> s.equals(session));
    }
}
