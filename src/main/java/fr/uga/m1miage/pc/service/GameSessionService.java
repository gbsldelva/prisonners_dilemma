package fr.uga.m1miage.pc.service;

import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import fr.uga.m1miage.pc.controller.WebSocketController;
import fr.uga.m1miage.pc.model.ChoiceMessage;
import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.model.GameSession;
import fr.uga.m1miage.pc.model.Invitation;
import fr.uga.m1miage.pc.model.InvitationAnswer;
import fr.uga.m1miage.pc.model.Player;
import fr.uga.m1miage.pc.strategy.Strategy;
import fr.uga.m1miage.pc.strategy.StrategyFactory;
import fr.uga.m1miage.pc.strategy.StrategyType;
import fr.uga.m1miage.pc.utils.UtilFunctions;

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
        return null;
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
      SecureRandom random = new SecureRandom();
      StrategyType[] strategies = StrategyType.values(); // Get all possible strategies
      int randomIndex = random.nextInt(strategies.length); // Generate a random index
      return strategies[randomIndex];
    }

    public GameSession createSession(Player player1, Player player2, int iterations) {
        if (player1 == null || player2 == null) {
            return null;
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
        
        // Si le serveur est le 2e joueur, il fait son choix en fonction de sa stratÃ©gie
        if (player2.isServer()) {
            Strategy serverStrategy = StrategyFactory.createStrategy(player2.getStrategy());
            String serverChoice = serverStrategy.playNextMove(session.getPlayer1Choices(), session.getPlayer2Choices());
            session.getPlayer2Choices().add(serverChoice);
        }

        // VÃ©rifie si la manche est termin?e et met ? jour ou termine la partie
        if (session.isRoundComplete()) {
        	Decision player1LastDecision;
        	Decision  player2LastDecision;
        	if (currentPlayer.getUsername().equals(choiceMessage.getUsername())) {
        		player1LastDecision = Decision.fromString(choiceMessage.getChoice());
        		player2LastDecision = Decision.fromString(session.getPlayer2Choices().get(session.getPlayer2Choices().size() - 1));
        	} else {
        		player1LastDecision = Decision.fromString(session.getPlayer1Choices().get(session.getPlayer1Choices().size() - 1));
        		player2LastDecision = Decision.fromString(choiceMessage.getChoice());
        	}
        	currentPlayer.setScore(currentPlayer.getScore() + UtilFunctions.getScore(player1LastDecision, player2LastDecision));
        	player2.setScore(player2.getScore() + UtilFunctions.getScore(player2LastDecision, player1LastDecision));
            session.incrementIteration();
            if (session.isGameOver()) {
                endGame(session);
            } else {
                notificationService.updateScore(session);
            }
        }
    }
    
    public GameSession findGameSession(String username) {
    for (GameSession session : activeGames.values()) {
        if (session.containsPlayer(username)) {
            return session;
        }
    }
    return null;
}

    void endGame(GameSession session) {
        notificationService.endGame(session);
        activeGames.values().removeIf(s -> s.equals(session));
        webSocketController.refreshAvailableUsers();
    }
    
    boolean disconectedPlayerShouldPlayNow(Player disconnectedPlayer, GameSession gameSession) {
    	if (gameSession.getPlayer1().getUsername().equals(disconnectedPlayer.getUsername())) {
    		return gameSession.getPlayer2Choices().size() > gameSession.getPlayer1Choices().size();
    	} else {
    		return gameSession.getPlayer1Choices().size() > gameSession.getPlayer2Choices().size();
        	
    	}
    }
    
    public void handleDisconnection(String username) {
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
        disconnectedPlayer.setServer(true);
        if (!session.isGameOver() && !session.isRoundComplete() && disconectedPlayerShouldPlayNow(disconnectedPlayer, session)) {
        	Strategy serverStrategy = StrategyFactory.createStrategy(disconnectedPlayer.getStrategy());
            String serverChoice = serverStrategy.playNextMove(null, null);
        	ChoiceMessage nextMove = new ChoiceMessage(disconnectedPlayer.getUsername(), serverChoice);
        	handleChoice(nextMove);
        }

        // Notify the remaining player about the replacement
        notificationService.notifyPlayerReplacement(
            remainingPlayer.getUsername(),
            "Votre adversaire s'est déconnecté, tu vas continuer la partie contre le serveur (avec sa stratégie initiale)."
        );

        // Refresh available users since the disconnected player is no longer active
        webSocketController.refreshAvailableUsers();
    	}
    }

    public Set<String> getActivePlayers() {
      return activeGames.values().stream()
            .flatMap(game -> Stream.of(game.getPlayer1().getUsername(), game.getPlayer2().getUsername()))
            .collect(Collectors.toSet());
    }


}
