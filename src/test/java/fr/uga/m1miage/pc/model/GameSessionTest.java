package fr.uga.m1miage.pc.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameSessionTest {

    @Test
    void testGettersAndSetters() {
        Player player1 = new Player("Player1"); // Assuming a Player class exists
        Player player2 = new Player("Player2");
        GameSession gameSession = new GameSession(player1, player2);
        
        gameSession.setTotalIterations(5);
        gameSession.setCurrentIteration(1);
        HashMap<String, String> choices = new HashMap<>();
        choices.put("Player1", "C");
        choices.put("Player2", "D");
        gameSession.setChoices(choices);
        
        assertEquals(player1, gameSession.getPlayer1());
        assertEquals(player2, gameSession.getPlayer2());
        assertEquals(5, gameSession.getTotalIterations());
        assertEquals(1, gameSession.getCurrentIteration());
        assertEquals(choices, gameSession.getChoices());
    }

    @Test
    void testEqualsAndHashCode() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        GameSession gameSession1 = new GameSession(player1, player2);
        GameSession gameSession2 = new GameSession(player1, player2);
        GameSession gameSession3 = new GameSession(new Player("Player3"), new Player("Player4"));

        gameSession1.setTotalIterations(5);
        gameSession1.setCurrentIteration(1);
        gameSession2.setTotalIterations(5);
        gameSession2.setCurrentIteration(1);
        
        assertEquals(gameSession1, gameSession2); // Equal sessions should be equal
        assertNotEquals(gameSession1, gameSession3); // Different sessions should not be equal
        assertEquals(gameSession1.hashCode(), gameSession2.hashCode()); // Same hash code for equal objects
    }

    @Test
    void testToString() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        GameSession gameSession = new GameSession(player1, player2);
        gameSession.setTotalIterations(5);
        gameSession.setCurrentIteration(1);
        
        String expectedString = "GameSession{" +
                "player1=" + player1 +
                ", player2=" + player2 +
                ", totalIterations=5" +
                ", currentIteration=1" +
                ", choices={}" +
                '}';
        
        assertEquals(expectedString, gameSession.toString());
    }
}
