package fr.uga.m1miage.pc.controller;

import fr.uga.m1miage.pc.model.ChoiceMessage;
import fr.uga.m1miage.pc.model.GameSession;
import fr.uga.m1miage.pc.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameControllerTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private GameController gameController;

    private ConcurrentHashMap<String, GameSession> activeGames;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        activeGames = new ConcurrentHashMap<>();
    }

    @Test
    void testConnectPlayerAndPairing() {
        Player player1 = new Player("player1", "session1");
        Player player2 = new Player("player2", "session2");

        // Simulate connecting two players
        gameController.connect(player1, "session1");
        gameController.connect(player2, "session2");

        // Verify that a game session has been created and both players are notified
        verify(messagingTemplate, times(1)).convertAndSendToUser(eq("session1"), eq("/queue/gameStart"), anyString());
        verify(messagingTemplate, times(1)).convertAndSendToUser(eq("session2"), eq("/queue/gameStart"), anyString());
    }

//    @Test
//    public void testEndGame() {
//        // Arrange
//        Player player1 = new Player("session1", "Player1");
//        Player player2 = new Player("session2", "Player2");
//        GameSession session = new GameSession(player1, player2);
//        session.setTotalIterations(1);  // Ensure total iterations are set for the game to end
//        session.setCurrentIteration(1);  // Simulate that the game has reached the final iteration
//
//        activeGames.put("gameId", session);
//
//        // Act
//        gameController.endGame(session);
//
//        // Assert - Correcting to use session ID, not username
//        verify(messagingTemplate).convertAndSendToUser(eq("session1"), eq("/queue/gameEnd"),
//                contains("Final Score"));
//        verify(messagingTemplate).convertAndSendToUser(eq("session2"), eq("/queue/gameEnd"),
//                contains("Final Score"));
//    }

	/*
	 * @Test public void testMakeChoiceAndScoreComputation() { // Arrange Player
	 * player1 = new Player("session1", "Player1"); Player player2 = new
	 * Player("session2", "Player2"); GameSession session = new GameSession(player1,
	 * player2); session.setTotalIterations(3); // Set total iterations for the game
	 * session.setCurrentIteration(0); // Starting iteration
	 * 
	 * activeGames.put("gameId", session);
	 * 
	 * ChoiceMessage choice1 = new ChoiceMessage("Player1", "C"); ChoiceMessage
	 * choice2 = new ChoiceMessage("Player2", "D");
	 * 
	 * // Act gameController.makeChoice(choice1, "session1");
	 * gameController.makeChoice(choice2, "session2");
	 * 
	 * // Assert - Check score computations assertEquals(1, player1.getScore());
	 * assertEquals(5, player2.getScore());
	 * 
	 * verify(messagingTemplate).convertAndSendToUser(eq("session1"),
	 * eq("/queue/scoreUpdate"), contains("1-5"));
	 * verify(messagingTemplate).convertAndSendToUser(eq("session2"),
	 * eq("/queue/scoreUpdate"), contains("5-1")); }
	 */

}
