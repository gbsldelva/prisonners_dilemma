package fr.uga.m1miage.pc.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class GameSessionTest {

    @Test
    void testConstructor() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        GameSession gameSession = new GameSession(player1, player2);

        assertEquals(player1, gameSession.getPlayer1());
        assertEquals(player2, gameSession.getPlayer2());
        assertEquals(0, gameSession.getTotalIterations());
        assertEquals(0, gameSession.getCurrentIteration());
        assertNotNull(gameSession.getPlayer1Choices());
        assertNotNull(gameSession.getPlayer2Choices());
    }

    @Test
    void testSettersAndGetters() {
        GameSession gameSession = new GameSession(new Player("Player 1"), new Player("Player 2"));

        gameSession.setTotalIterations(5);
        assertEquals(5, gameSession.getTotalIterations());

        gameSession.setCurrentIteration(3);
        assertEquals(3, gameSession.getCurrentIteration());

        List<String> player1Choices = new ArrayList<>();
        player1Choices.add("Choice 1");
        player1Choices.add("Choice 2");
        gameSession.setPlayer1Choices(player1Choices);
        assertEquals(player1Choices, gameSession.getPlayer1Choices());

        List<String> player2Choices = new ArrayList<>();
        player2Choices.add("Choice 3");
        player2Choices.add("Choice 4");
        gameSession.setPlayer2Choices(player2Choices);
        assertEquals(player2Choices, gameSession.getPlayer2Choices());
    }

    @Test
    void testIncrementIteration() {
        GameSession gameSession = new GameSession(new Player("Player 1"), new Player("Player 2"));
        gameSession.setCurrentIteration(2);
        gameSession.incrementIteration();
        assertEquals(3, gameSession.getCurrentIteration());
    }

    @Test
    void testEqualsAndHashCode() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        GameSession gameSession1 = new GameSession(player1, player2);
        GameSession gameSession2 = new GameSession(player1, player2);

        assertEquals(gameSession1, gameSession2);
        assertEquals(gameSession1.hashCode(), gameSession2.hashCode());

        gameSession1.setCurrentIteration(1);
        assertNotEquals(gameSession1, gameSession2);
        assertNotEquals(gameSession1.hashCode(), gameSession2.hashCode());
    }
}