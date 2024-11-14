package fr.uga.m1miage.pc.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvitationTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange: Create an instance using the constructor
        String fromPlayer = "Alice";
        String toUsername = "Bob";
        int iteration = 5;
        
        // Act: Instantiate the Invitation object
        Invitation invitation = new Invitation(fromPlayer, toUsername, iteration);
        
        // Assert: Verify that fields are correctly initialized
        assertEquals(fromPlayer, invitation.getFromPlayer());
        assertEquals(toUsername, invitation.getToUsername());
        assertEquals(iteration, invitation.getIteration());
    }

    @Test
    void testSetters() {
        // Arrange: Create an empty Invitation object
        Invitation invitation = new Invitation("Alice", "Bob", 5);

        // Act: Modify fields using setters
        invitation.setFromPlayer("Charlie");
        invitation.setToUsername("Dave");
        invitation.setIteration(10);

        // Assert: Verify that fields are updated correctly
        assertEquals("Charlie", invitation.getFromPlayer());
        assertEquals("Dave", invitation.getToUsername());
        assertEquals(10, invitation.getIteration());
    }
}
