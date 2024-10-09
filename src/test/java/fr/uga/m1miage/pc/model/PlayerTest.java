package fr.uga.m1miage.pc.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testGettersAndSetters() {
        Player player = new Player("Player1");
        assertEquals("Player1", player.getUsername());
        
        player.setUsername("NewPlayer");
        assertEquals("NewPlayer", player.getUsername());
        
        assertEquals(0, player.getScore());
        player.setScore(10);
        assertEquals(10, player.getScore());
    }

    @Test
    void testEqualsAndHashCode() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player1");
        Player player3 = new Player("Player2");

        assertEquals(player1, player2); // Equal usernames should result in equality
        assertNotEquals(player1, player3); // Different usernames should not be equal
        assertEquals(player1.hashCode(), player2.hashCode()); // Same hash code for equal objects
    }

    @Test
    void testToString() {
        Player player = new Player("Player1");
        String expectedString = "Player{username='Player1', score=0}";
        assertEquals(expectedString, player.toString());
    }
}
