package fr.uga.m1miage.pc.controller;

import fr.uga.m1miage.pc.model.ChoiceMessage;
import fr.uga.m1miage.pc.model.GameSession;
import fr.uga.m1miage.pc.model.Invitation;
import fr.uga.m1miage.pc.model.InvitationAnswer;
import fr.uga.m1miage.pc.model.Player;
import fr.uga.m1miage.pc.model.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
public class GameController {

 @Autowired
 private SimpMessagingTemplate messagingTemplate;

 Map<String, Integer> invitationIterationMap = new LinkedHashMap<>();
 // Map to hold active game sessions
 static final ConcurrentMap<String, GameSession> activeGames = new ConcurrentHashMap<>();

 @MessageMapping("/invite")
 public void invitePlayer(@Payload Invitation invitation) {
	 invitationIterationMap.put(invitation.getFromPlayer() + "&" + invitation.getToUsername(), invitation.getIteration());
     // Send the message to the user
	messagingTemplate.convertAndSendToUser(invitation.getToUsername(), "/queue/invitation",invitation.getFromPlayer());
 }
 
 @MessageMapping("/invitationAnswer")
 public void playerResponseToInvitation(@Payload InvitationAnswer answer) {
	 messagingTemplate.convertAndSend("/queue/gameStartHandler", answer.getMessage());
	 if (answer.getMessage().equals("confirmed"))
		 pairingPlayers(answer.getPlayerUsername(), answer.getOponentUsername());
 }

 void pairingPlayers(String player1Username, String player2Username) {
     Player player1 = WebSocketController.connectedPlayers.get(player1Username);
     Player player2 = WebSocketController.connectedPlayers.get(player2Username);
     GameSession session = new GameSession(player1, player2);
     String key = player1.getUsername() + "&" + player2.getUsername();
     if (!invitationIterationMap.containsKey(key))
    	 key = player2.getUsername() + "&" + player1.getUsername();
     session.setTotalIterations(invitationIterationMap.get(key));
     String gameId = player1.getUsername() + "VS" + player2.getUsername();
     activeGames.put(gameId, session);
 }

 @MessageMapping("/makeChoice")
 public void makeChoice(@Payload ChoiceMessage choiceMessage) {
     // Find the game session for this player
     GameSession session = findGameSession(choiceMessage.getUsername());
     
     if (session == null) {
    	 return; 
     }
     if (session.getPlayer1().getUsername().equals(choiceMessage.getUsername()))
     	session.getPlayer1Choices().add(choiceMessage.getChoice());
	else
 		session.getPlayer2Choices().add(choiceMessage.getChoice());
     if (session.getPlayer1Choices().size() == session.getPlayer2Choices().size() && session.getPlayer1Choices().size() == session.getCurrentIteration() + 1) {
    	 session.incrementIteration();
    	 computeScores(session);
     }
 }

 public GameSession findGameSession(String username) {
     GameSession result = null;
	 for (Map.Entry<String, GameSession> entry : activeGames.entrySet()) {
    	 if (entry.getKey().contains(username)) {
    		 result = entry.getValue(); 
    	 }
     }
     return result;
 }

 void computeScores(GameSession session) {
     String choice1 = session.getPlayer1Choices().get(session.getPlayer1Choices().size() - 1);
     String choice2 = session.getPlayer2Choices().get(session.getPlayer2Choices().size() - 1);

     int score1 = 0;
     int score2 = 0;

     if (choice1.equals("c") && choice2.equals("c")) {
         score1 = 3;
         score2 = 3;
     } else if (choice1.equals("c") && choice2.equals("t")) {
         score2 = 5;
     } else if (choice1.equals("t") && choice2.equals("c")) {
         score1 = 5;
     } else if (choice1.equals("t") && choice2.equals("t")) {
         score1 = 1;
         score2 = 1;
     }

     session.getPlayer1().setScore(session.getPlayer1().getScore() + score1);
     session.getPlayer2().setScore(session.getPlayer2().getScore() + score2);
     
     if (session.getCurrentIteration() == session.getTotalIterations()) {
         endGame(session);
     } else {
    	 Result result = new Result();
    	 result.setScore(session.getPlayer1().getUsername() + "(" + session.getPlayer1().getScore() + ") - " + session.getPlayer2().getUsername() + "(" + session.getPlayer2().getScore() + ")");
    	 result.setStatus("En cours");
    	 result.setParti((session.getCurrentIteration() + 1) + "/" + session.getTotalIterations());
    	// Send updated scores to both players
         messagingTemplate.convertAndSend("/queue/scoreUpdate", result.toJson()); 
     }
 }

 void endGame(GameSession session) {
	 Result result = new Result();
	 result.setScore(session.getPlayer1().getUsername() + "(" + session.getPlayer1().getScore() + ") - " + session.getPlayer2().getUsername() + "(" + session.getPlayer2().getScore() + ")");
	 result.setStatus("Terminé");
	 result.setParti(session.getTotalIterations() + "/" + session.getTotalIterations());
	// Send updated scores to both players
     messagingTemplate.convertAndSend("/queue/gameEnd", result.toJson());

     // Remove the game session
     activeGames.values().removeIf(s -> s.equals(session));
 }
}
