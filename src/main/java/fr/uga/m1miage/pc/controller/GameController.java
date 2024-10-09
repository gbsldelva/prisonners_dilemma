package fr.uga.m1miage.pc.controller;

import fr.uga.m1miage.pc.model.ChoiceMessage;
import fr.uga.m1miage.pc.model.GameSession;
import fr.uga.m1miage.pc.model.Invitation;
import fr.uga.m1miage.pc.model.InvitationAnswer;
import fr.uga.m1miage.pc.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Controller
public class GameController {

 @Autowired
 private SimpMessagingTemplate messagingTemplate;

 // Queue to hold waiting players
 private ConcurrentLinkedQueue<Player> waitingPlayers = new ConcurrentLinkedQueue<>();

 // Map to hold active game sessions
 private ConcurrentHashMap<String, GameSession> activeGames = new ConcurrentHashMap<>();

 @MessageMapping("/invite")
 public void invitePlayer(@Payload Invitation invitation) {
     // Send the message to the user
	messagingTemplate.convertAndSendToUser(invitation.getToUsername(), "/queue/invitation",invitation.getFromPlayer());
 }
 
 @MessageMapping("invitationAnswer")
 public void playerResponseToInvitation(@Payload InvitationAnswer answer) {
	 messagingTemplate.convertAndSend("/queue/gameStartHandler", answer.getMessage());
 }
 
 @MessageMapping("/connectPlayer")
 public void connect(@Payload Player player, @Header("simpSessionId") String sessionId) {
     player.setSessionId(sessionId);
     waitingPlayers.add(player);
     pairingPlayers();
 }

 private void pairingPlayers() {
     if (waitingPlayers.size() >= 2) {
         Player player1 = waitingPlayers.poll();
         Player player2 = waitingPlayers.poll();
         GameSession session = new GameSession();
         session.setPlayer1(player1);
         session.setPlayer2(player2);
         session.setCurrentIteration(0);
         // Assign a unique session ID, e.g., using current timestamp or UUID
         String gameId = "game-" + System.currentTimeMillis();
         activeGames.put(gameId, session);

         // Notify both players that the game has started
         messagingTemplate.convertAndSendToUser(player1.getSessionId(), "/queue/gameStart", gameId);
         messagingTemplate.convertAndSendToUser(player2.getSessionId(), "/queue/gameStart", gameId);
     }
 }

// @MessageMapping("/startGame")
// public void startGame(@Payload String gameId, @Header("simpSessionId") String sessionId) {
//     GameSession session = activeGames.get(gameId);
//     if (session != null) {
//         session.setTotalIterations(session.getTotalIterations());
//         // Notify players to set number of iterations
//     }
// }

 @MessageMapping("/makeChoice")
 public void makeChoice(@Payload ChoiceMessage choiceMessage, @Header("simpSessionId") String sessionId) {
     // Find the game session for this player
     GameSession session = findGameSession(sessionId);
     if (session != null) {
         session.getChoices().put(choiceMessage.getUsername(), choiceMessage.getChoice());

         // If both players have made their choices, compute scores
         if (session.getChoices().size() == 2) {
             computeScores(session);
             // Reset choices for next iteration
             session.getChoices().clear();
             session.setCurrentIteration(session.getCurrentIteration() + 1);

             // Check if game has ended
             if (session.getCurrentIteration() >= session.getTotalIterations()) {
                 endGame(session);
             }
         }
     }
 }

 private GameSession findGameSession(String sessionId) {
     return activeGames.values().stream()
             .filter(session -> session.getPlayer1().getSessionId().equals(sessionId) ||
                     session.getPlayer2().getSessionId().equals(sessionId))
             .findFirst()
             .orElse(null);
 }

 private void computeScores(GameSession session) {
     String choice1 = session.getChoices().get(session.getPlayer1().getUsername());
     String choice2 = session.getChoices().get(session.getPlayer2().getUsername());

     int score1 = 0;
     int score2 = 0;

     if (choice1.equals("C") && choice2.equals("C")) {
         score1 += 3;
         score2 += 3;
     } else if (choice1.equals("C") && choice2.equals("D")) {
         score1 += 1;
         score2 += 5;
     } else if (choice1.equals("D") && choice2.equals("C")) {
         score1 += 5;
         score2 += 1;
     } else if (choice1.equals("D") && choice2.equals("D")) {
         score1 += 0;
         score2 += 0;
     }

     session.getPlayer1().setScore(session.getPlayer1().getScore() + score1);
     session.getPlayer2().setScore(session.getPlayer2().getScore() + score2);

     // Send updated scores to both players
     messagingTemplate.convertAndSendToUser(session.getPlayer1().getSessionId(), "/queue/scoreUpdate",
             session.getPlayer1().getScore() + "-" + session.getPlayer2().getScore());
     messagingTemplate.convertAndSendToUser(session.getPlayer2().getSessionId(), "/queue/scoreUpdate",
             session.getPlayer2().getScore() + "-" + session.getPlayer1().getScore());
 }

 void endGame(GameSession session) {
     // Notify players that the game has ended
     messagingTemplate.convertAndSendToUser(session.getPlayer1().getSessionId(), "/queue/gameEnd",
             "Final Score: " + session.getPlayer1().getScore() + " - " + session.getPlayer2().getScore());
     messagingTemplate.convertAndSendToUser(session.getPlayer2().getSessionId(), "/queue/gameEnd",
             "Final Score: " + session.getPlayer2().getScore() + " - " + session.getPlayer1().getScore());

     // Remove the game session
     activeGames.values().removeIf(s -> s.equals(session));
 }
}
