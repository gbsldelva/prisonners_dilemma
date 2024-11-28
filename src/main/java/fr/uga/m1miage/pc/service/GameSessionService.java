package fr.uga.m1miage.pc.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import fr.uga.m1miage.pc.controller.WebSocketController;
import fr.uga.m1miage.pc.model.ChoiceMessage;
import fr.uga.m1miage.pc.model.GameSession;
import fr.uga.m1miage.pc.model.Invitation;
import fr.uga.m1miage.pc.model.InvitationAnswer;
import fr.uga.m1miage.pc.model.Player;
import fr.uga.m1miage.pc.strategy.Strategy;
import fr.uga.m1miage.pc.strategy.StrategyFactory;
import fr.uga.m1miage.pc.strategy.StrategyType;

@Service
public class GameSessionService {

    private final NotificationService notificationService;
    private final WebSocketController webSocketController;
    public final Map<String, Integer> invitationIterationMap = new LinkedHashMap<>();
    private static final ConcurrentMap<String, GameSession> activeGames = new ConcurrentHashMap<>();

    public GameSessionService(NotificationService notificationService, WebSocketController webSocketController) {
        this.notificationService = notificationService;
        this.webSocketController = webSocketController;
    }
    
    void pairPlayers(String player1Username, String player2Username) {
        Player player1 = WebSocketController.connectedPlayers.get(player1Username);
        Player player2 = WebSocketController.connectedPlayers.get(player2Username);
        String key1 = player1.getUsername() + "&" + player2.getUsername();
        String key2 = player2.getUsername() + "&" + player1.getUsername();
        int nbIteration = 0;
        if (invitationIterationMap.containsKey(key1))
        	nbIteration = invitationIterationMap.get(key1);
        else if (invitationIterationMap.containsKey(key2))
        	nbIteration = invitationIterationMap.get(key2);
        
        createSession(player1, player2, nbIteration);
    }

    public GameSession playAgainstServer(Player player, int iterations) {
    if (iterations <= 0) {
        throw new IllegalArgumentException("Le nombre d'itérations doit être positif.");
    }
    // Create a server player
    Player server = new Player("Ordinateur", null);
    server.setServer(true);

    // Assign a random strategy to the server
    StrategyType randomStrategy = getRandomStrategy();
    server.setStrategy(randomStrategy);

    GameSession session = createSession(player, server, iterations);
    notificationService.notifyGameStart(player.getUsername(), "La partie commence contre l'ordinateur");

    return session;
}
    
    // Helper method to get a random StrategyType
    private StrategyType getRandomStrategy() {
      StrategyType[] strategies = StrategyType.values(); // Get all possible strategies
      int randomIndex = ThreadLocalRandom.current().nextInt(strategies.length); // Generate a random index
      return strategies[randomIndex];
    }

    public GameSession createSession(Player player1, Player player2, int iterations) {
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Les joueurs ne peuvent pas être nuls.");
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
		String key = invitation.getFromPlayer() + "&" + invitation.getToUsername();
		
        invitationIterationMap.put(key, invitation.getIteration());
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

        // D?termine quel joueur fait le choix (le joueur 1 est celui qui a envoy? le message)
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
            Strategy serverStrategy = StrategyFactory.createStrategy(player2.getStrategy());
            String serverChoice = serverStrategy.playNextMove(session.getPlayer1Choices(), session.getPlayer2Choices());
            session.getPlayer2Choices().add(serverChoice);
        }

        // Vérifie si la manche est termin?e et met ? jour ou termine la partie
        if (session.isRoundComplete()) {
            session.incrementIteration();
            if (session.isGameOver()) {
                endGame(session);
            } else {
                notificationService.updateScore(session);
            }
        }
    }

    public GameSession findGameSession(String username) {
    System.out.println("Finding session for username: " + username);
    for (GameSession session : activeGames.values()) {
        System.out.println("Checking session: " + session);
        if (session.containsPlayer(username)) {
            System.out.println("Found matching session: " + session);
            return session;
        }
    }
    System.out.println("No matching session found for username: " + username);
    return null;
}

    void endGame(GameSession session) {
        notificationService.endGame(session);
        activeGames.values().removeIf(s -> s.equals(session));
        webSocketController.refreshAvailableUsers();
    }

    public void handleDisconnection(String username) {
    System.out.println("Disconnecting player: " + username);

    // Normalize the username
    username = username.replaceAll("^\"|\"$", "").trim();
    GameSession session = findGameSession(username);

    if (session != null) {
        Player remainingPlayer;
        Player disconnectedPlayer;

        // Determine the remaining and disconnected players
        if (session.getPlayer1().getUsername().equals(username)) {
            remainingPlayer = session.getPlayer2();
            disconnectedPlayer = session.getPlayer1();
        } else {
            remainingPlayer = session.getPlayer1();
            disconnectedPlayer = session.getPlayer2();
        }

        // Replace the disconnected player with a server player
        Player serverPlayer = new Player("Ordinateur", null, disconnectedPlayer.getStrategy(), true);
        if (session.getPlayer1().getUsername().equals(username)) {
            session.setPlayer1(serverPlayer);
        } else {
            session.setPlayer2(serverPlayer);
        }

        // Notify the remaining player about the replacement
        notificationService.notifyPlayerReplacement(
            remainingPlayer.getUsername(),
            "Votre adversaire a été remplacé par un ordinateur avec la stratégie de votre adversaire initial."
        );

        // Simulate the server's move for the next round using its strategy
        if (session.getCurrentIteration() < session.getTotalIterations()) {
            Strategy serverStrategy = StrategyFactory.createStrategy(serverPlayer.getStrategy());
            String serverChoice = serverStrategy.playNextMove(session.getPlayer1Choices(), session.getPlayer2Choices());

            // Add the server's choice to the session
            if (serverPlayer.equals(session.getPlayer1())) {
                session.getPlayer1Choices().add(serverChoice);
            } else {
                session.getPlayer2Choices().add(serverChoice);
            }

            // Notify that the game continues
            notificationService.updateScore(session);
        }

        // Refresh available users since the disconnected player is no longer active
        webSocketController.refreshAvailableUsers();
    } else {
        System.out.println("No session found for disconnected player: " + username);
    }
}  
    @SuppressWarnings("unused")
     void clearActiveGames() {
         activeGames.clear();
     }

    public Set<String> getActivePlayers() {
      return activeGames.values().stream()
            .flatMap(game -> Stream.of(game.getPlayer1().getUsername(), game.getPlayer2().getUsername()))
            .collect(Collectors.toSet());
    }


}
