package fr.uga.m1miage.pc.controller;

import fr.uga.m1miage.pc.model.ChoiceMessage;
import fr.uga.m1miage.pc.model.GameSession;
import fr.uga.m1miage.pc.model.Invitation;
import fr.uga.m1miage.pc.model.InvitationAnswer;
import fr.uga.m1miage.pc.model.Player;
import fr.uga.m1miage.pc.model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GameControllerTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Clear static maps before each test to ensure test isolation
        WebSocketController.connectedPlayers.clear();
        GameController.activeGames.clear();
        gameController.invitationIterationMap.clear();
    }

    @Test
    void testInvitePlayer() {
        // Arrange
        Invitation invitation = new Invitation("Alice", "Bob", 5);

        // Act
        gameController.invitePlayer(invitation);

        // Assert
        String key = "Alice&Bob";
        assertEquals(5, gameController.invitationIterationMap.get(key), "Invitation iteration should be recorded correctly.");

        // Verify that a message is sent to Bob's queue with the invitation from Alice
        verify(messagingTemplate, times(1)).convertAndSendToUser("Bob", "/queue/invitation", "Alice");
    }

    @Test
    void testPlayerResponseToInvitationConfirmed() {
        // Arrange
        InvitationAnswer answer = new InvitationAnswer("confirmed", "Alice", "Bob");

        // Mock players in WebSocketController.connectedPlayers
        Player player1 = new Player("Alice", "session1");
        Player player2 = new Player("Bob", "session2");
        WebSocketController.connectedPlayers.put("Alice", player1);
        WebSocketController.connectedPlayers.put("Bob", player2);

        // Set invitation iteration
        String key = "Alice&Bob";
        gameController.invitationIterationMap.put(key, 5);

        // Act
        gameController.playerResponseToInvitation(answer);

        // Assert
        // Verify that a confirmation message is sent to the game start handler
        verify(messagingTemplate, times(1)).convertAndSend("/queue/gameStartHandler", "confirmed");
    }

    @Test
    void testPlayerResponseToInvitationRejected() {
        // Arrange
        InvitationAnswer answer = new InvitationAnswer("rejected", "Alice", "Bob");

        // Act
        gameController.playerResponseToInvitation(answer);

        // Assert
        // Verify that a rejection message is sent to the game start handler
        verify(messagingTemplate, times(1)).convertAndSend("/queue/gameStartHandler", "rejected");

        // Verify that no GameSession is created
        String gameId = "AliceVSBob";
        assertFalse(GameController.activeGames.containsKey(gameId), "No GameSession should be created for a rejected invitation.");
    }

    @Test
    void testPairingPlayers() {
        // Arrange
        Player player1 = new Player("Alice", "session1");
        Player player2 = new Player("Bob", "session2");
        WebSocketController.connectedPlayers.put("Alice", player1);
        WebSocketController.connectedPlayers.put("Bob", player2);

        // Set invitation iteration
        String key = "Alice&Bob";
        gameController.invitationIterationMap.put(key, 5);

        // Act
        gameController.pairingPlayers("Alice", "Bob");

        // Assert
        String gameId = "AliceVSBob";
        assertTrue(GameController.activeGames.containsKey(gameId), "GameSession should be stored in activeGames.");

        GameSession session = GameController.activeGames.get(gameId);
        assertNotNull(session, "GameSession should not be null.");
        assertEquals(player1, session.getPlayer1(), "Player1 should be Alice.");
        assertEquals(player2, session.getPlayer2(), "Player2 should be Bob.");
        assertEquals(5, session.getTotalIterations(), "Total iterations should match the invitation.");
    }

    @Test
    void testMakeChoice_Player1() {
        // Arrange
        Player player1 = new Player("Alice", "session1");
        Player player2 = new Player("Bob", "session2");
        WebSocketController.connectedPlayers.put("Alice", player1);
        WebSocketController.connectedPlayers.put("Bob", player2);

        // Create GameSession
        GameSession session = new GameSession(player1, player2);
        session.setTotalIterations(5);
        String gameId = "AliceVSBob";
        GameController.activeGames.put(gameId, session);

        // Create a ChoiceMessage for player1
        ChoiceMessage choiceMessage = new ChoiceMessage("Alice", "c");

        // Act
        gameController.makeChoice(choiceMessage);

        // Assert
        assertEquals(1, session.getPlayer1Choices().size(), "Player1 should have one choice recorded.");
        assertEquals("c", session.getPlayer1Choices().get(0), "Player1's choice should be 'c'.");

        // Since player2 hasn't made a choice, computeScores should not be called yet
        verify(messagingTemplate, never()).convertAndSend(anyString(), any(Object.class));

        // Add a choice for player2 to reach the current iteration
        ChoiceMessage choiceMessage2 = new ChoiceMessage("Bob", "t");
        gameController.makeChoice(choiceMessage2);

        // Now computeScores should be called
        assertEquals(1, session.getPlayer2Choices().size(), "Player2 should have one choice recorded.");
        assertEquals("t", session.getPlayer2Choices().get(0), "Player2's choice should be 't'.");

        // Verify that convertAndSend was called to /queue/scoreUpdate
        verify(messagingTemplate, times(1)).convertAndSend(eq("/queue/scoreUpdate"), anyString());

        // Check that scores are updated correctly
        assertEquals(0, player1.getScore(), "Player1's score should be updated to 0.");
        assertEquals(5, player2.getScore(), "Player2's score should be updated to 4.");
    }

    @Test
    void testMakeChoice_Player2() {
        // Arrange
        Player player1 = new Player("Alice", "session1");
        Player player2 = new Player("Bob", "session2");
        WebSocketController.connectedPlayers.put("Alice", player1);
        WebSocketController.connectedPlayers.put("Bob", player2);

        // Create GameSession
        GameSession session = new GameSession(player1, player2);
        session.setTotalIterations(5);
        String gameId = "AliceVSBob";
        GameController.activeGames.put(gameId, session);

        // Create a ChoiceMessage for player2
        ChoiceMessage choiceMessage = new ChoiceMessage("Bob", "c");

        // Act
        gameController.makeChoice(choiceMessage);

        // Assert
        assertEquals(1, session.getPlayer2Choices().size(), "Player2 should have one choice recorded.");
        assertEquals("c", session.getPlayer2Choices().get(0), "Player2's choice should be 'c'.");

        // Since player1 hasn't made a choice, computeScores should not be called yet
        verify(messagingTemplate, never()).convertAndSend(anyString(), any(Object.class));

        // Add a choice for player1 to reach the current iteration
        ChoiceMessage choiceMessage2 = new ChoiceMessage("Alice", "t");
        gameController.makeChoice(choiceMessage2);

        // Now computeScores should be called
        assertEquals(1, session.getPlayer1Choices().size(), "Player1 should have one choice recorded.");
        assertEquals("t", session.getPlayer1Choices().get(0), "Player1's choice should be 't'.");

        // Verify that convertAndSend was called to /queue/scoreUpdate
        verify(messagingTemplate, times(1)).convertAndSend(eq("/queue/scoreUpdate"), anyString());

        // Check that scores are updated correctly
        assertEquals(5, player1.getScore(), "Player1's score should be updated to 5.");
        assertEquals(0, player2.getScore(), "Player2's score should be updated to 0.");
    }

    @Test
    void testComputeScores_CooperateBoth() {
        // Arrange
        Player player1 = new Player("Alice", "session1");
        Player player2 = new Player("Bob", "session2");
        WebSocketController.connectedPlayers.put("Alice", player1);
        WebSocketController.connectedPlayers.put("Bob", player2);

        // Create GameSession
        GameSession session = new GameSession(player1, player2);
        session.setTotalIterations(1);
        session.setCurrentIteration(1);
        String gameId = "AliceVSBob";
        GameController.activeGames.put(gameId, session);

        // Add choices
        session.getPlayer1Choices().add("c");
        session.getPlayer2Choices().add("c");

        // Act
        gameController.computeScores(session);

        // Assert
        assertEquals(3, player1.getScore(), "Player1's score should be 3 after both cooperate.");
        assertEquals(3, player2.getScore(), "Player2's score should be 3 after both cooperate.");

        // Since totalIterations is reached, game should end
        assertFalse(GameController.activeGames.containsKey(gameId), "GameSession should be removed after game end.");
    }

    @Test
    void testComputeScores_BetrayAndCooperate() {
        // Arrange
        Player player1 = new Player("Alice", "session1");
        Player player2 = new Player("Bob", "session2");
        WebSocketController.connectedPlayers.put("Alice", player1);
        WebSocketController.connectedPlayers.put("Bob", player2);

        // Create GameSession
        GameSession session = new GameSession(player1, player2);
        session.setTotalIterations(1);
        session.setCurrentIteration(1);
        String gameId = "AliceVSBob";
        GameController.activeGames.put(gameId, session);

        // Add choices: Alice betrays, Bob cooperates
        session.getPlayer1Choices().add("t");
        session.getPlayer2Choices().add("c");

        // Act
        gameController.computeScores(session);

        // Assert
        assertEquals(5, player1.getScore(), "Player1's score should be 5 after betraying.");
        assertEquals(0, player2.getScore(), "Player2's score should be 0 after being betrayed.");

        // Since totalIterations is reached, game should end
        assertFalse(GameController.activeGames.containsKey(gameId), "GameSession should be removed after game end.");

        // Verify that convertAndSend was called to /queue/gameEnd
        verify(messagingTemplate, times(1)).convertAndSend(eq("/queue/gameEnd"), anyString());
    }

    @Test
    void testEndGame() {
        // Arrange
        Player player1 = new Player("Alice", "session1");
        Player player2 = new Player("Bob", "session2");
        player1.setScore(10);
        player2.setScore(8);
        WebSocketController.connectedPlayers.put("Alice", player1);
        WebSocketController.connectedPlayers.put("Bob", player2);

        // Create GameSession
        GameSession session = new GameSession(player1, player2);
        session.setTotalIterations(5);
        session.setCurrentIteration(5);
        String gameId = "AliceVSBob";
        GameController.activeGames.put(gameId, session);

        // Act
        gameController.endGame(session);

        // Assert
        // Create expected Result JSON
        Result expectedResult = new Result();
        expectedResult.setScore("Alice(10) - Bob(8)");
        expectedResult.setStatus("Terminé");
        expectedResult.setParti("5/5");
        String expectedMessage = expectedResult.toJson();

        // Verify that convertAndSend was called to /queue/gameEnd with the correct message
        verify(messagingTemplate, times(1)).convertAndSend("/queue/gameEnd", expectedMessage);

        // Verify that the session is removed from activeGames
        assertFalse(GameController.activeGames.containsKey(gameId), "GameSession should be removed after ending the game.");
    }

    @Test
    void testFindGameSession() {
        // Arrange
        Player player1 = new Player("Alice", "session1");
        Player player2 = new Player("Bob", "session2");
        WebSocketController.connectedPlayers.put("Alice", player1);
        WebSocketController.connectedPlayers.put("Bob", player2);

        // Create GameSession
        GameSession session = new GameSession(player1, player2);
        session.setTotalIterations(5);
        String gameId = "AliceVSBob";
        GameController.activeGames.put(gameId, session);

        // Act
        GameSession foundSessionAlice = gameController.findGameSession("Alice");
        GameSession foundSessionBob = gameController.findGameSession("Bob");
        GameSession notFoundSession = gameController.findGameSession("Charlie");

        // Assert
        assertNotNull(foundSessionAlice, "Session should be found for Alice.");
        assertEquals(session, foundSessionAlice, "Found session should match the created session for Alice.");

        assertNotNull(foundSessionBob, "Session should be found for Bob.");
        assertEquals(session, foundSessionBob, "Found session should match the created session for Bob.");

        assertNull(notFoundSession, "No session should be found for Charlie.");
    }

    @Test
    void testMakeChoice_EndGame() {
        // Arrange
        Player player1 = new Player("Alice", "session1");
        Player player2 = new Player("Bob", "session2");
        WebSocketController.connectedPlayers.put("Alice", player1);
        WebSocketController.connectedPlayers.put("Bob", player2);

        // Create GameSession with last iteration
        GameSession session = new GameSession(player1, player2);
        session.setTotalIterations(1);
        session.setCurrentIteration(0);
        String gameId = "AliceVSBob";
        GameController.activeGames.put(gameId, session);

        // Add choices
        ChoiceMessage choiceMessage1 = new ChoiceMessage("Alice", "c");
        ChoiceMessage choiceMessage2 = new ChoiceMessage("Bob", "c");

        // Act
        gameController.makeChoice(choiceMessage1);
        gameController.makeChoice(choiceMessage2);

        // Assert
        // Scores should be updated
        assertEquals(3, player1.getScore(), "Player1's score should be updated to 3.");
        assertEquals(3, player2.getScore(), "Player2's score should be updated to 3.");

        // Verify that the session is removed from activeGames
        assertFalse(GameController.activeGames.containsKey(gameId), "GameSession should be removed after game end.");
    }

    @Test
    void testMakeChoice_NotEndGame() {
        // Arrange
        Player player1 = new Player("Alice", "session1");
        Player player2 = new Player("Bob", "session2");
        WebSocketController.connectedPlayers.put("Alice", player1);
        WebSocketController.connectedPlayers.put("Bob", player2);

        // Create GameSession with multiple iterations
        GameSession session = new GameSession(player1, player2);
        session.setTotalIterations(3);
        session.setCurrentIteration(0);
        String gameId = "AliceVSBob";
        GameController.activeGames.put(gameId, session);

        // Act
        // First round
        ChoiceMessage choiceMessage1 = new ChoiceMessage("Alice", "c");
        ChoiceMessage choiceMessage2 = new ChoiceMessage("Bob", "t");
        gameController.makeChoice(choiceMessage1);
        gameController.makeChoice(choiceMessage2);

        // Assert
        assertEquals(0, player1.getScore(), "Player1's score should be 0 after cooperating.");
        assertEquals(5, player2.getScore(), "Player2's score should be 5 after betraying.");

        // Verify that convertAndSend was called to /queue/scoreUpdate
        verify(messagingTemplate, times(1)).convertAndSend(eq("/queue/scoreUpdate"), anyString());
        verify(messagingTemplate, never()).convertAndSend(eq("/queue/gameEnd"), anyString());

        // Second round
        ChoiceMessage choiceMessage3 = new ChoiceMessage("Alice", "t");
        ChoiceMessage choiceMessage4 = new ChoiceMessage("Bob", "c");
        gameController.makeChoice(choiceMessage3);
        gameController.makeChoice(choiceMessage4);

        // Assert
        assertEquals(5, player1.getScore(), "Player1's score should be 8 after betraying.");
        assertEquals(5, player2.getScore(), "Player2's score should remain 5 after being betrayed.");

        // Verify that convertAndSend was called to /queue/scoreUpdate again
        verify(messagingTemplate, times(2)).convertAndSend(eq("/queue/scoreUpdate"), anyString());
        verify(messagingTemplate, never()).convertAndSend(eq("/queue/gameEnd"), anyString());

        // Third round
        ChoiceMessage choiceMessage5 = new ChoiceMessage("Alice", "c");
        ChoiceMessage choiceMessage6 = new ChoiceMessage("Bob", "c");
        gameController.makeChoice(choiceMessage5);
        gameController.makeChoice(choiceMessage6);

        // Assert
        assertEquals(8, player1.getScore(), "Player1's score should be 11 after cooperating.");
        assertEquals(8, player2.getScore(), "Player2's score should be 8 after cooperating.");

        // Verify that convertAndSend was called to /queue/scoreUpdate and to /queue/gameEnd
        verify(messagingTemplate, times(2)).convertAndSend(eq("/queue/scoreUpdate"), anyString());
        verify(messagingTemplate, times(1)).convertAndSend(eq("/queue/gameEnd"), anyString());

        // Verify that the session is removed
        assertFalse(GameController.activeGames.containsKey(gameId), "GameSession should be removed after game end.");
    }
}
