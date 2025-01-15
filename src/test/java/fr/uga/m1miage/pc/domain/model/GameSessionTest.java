package fr.uga.m1miage.pc.domain.model;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.model.GameSession;
import fr.uga.m1miage.pc.domain.model.Player;
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

        List<Decision> player1Choices = new ArrayList<>();
        player1Choices.add(Decision.BETRAY);
        player1Choices.add(Decision.BETRAY);
        gameSession.setPlayer1Choices(player1Choices);
        assertEquals(player1Choices, gameSession.getPlayer1Choices());

        List<Decision> player2Choices = new ArrayList<>();
        player2Choices.add(Decision.COOPERATE);
        player2Choices.add(Decision.COOPERATE);
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
        assertNotEquals(gameSession1.hashCode(), gameSession2.hashCode());
    }
}