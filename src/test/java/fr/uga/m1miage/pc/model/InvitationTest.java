package fr.uga.m1miage.pc.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvitationTest {
    @Test
    void testSettersAndGetters() {
        Invitation invitation = new Invitation("Player 1", "username", 5);
        assertEquals("Player 1", invitation.getFromPlayer());
        assertEquals("username", invitation.getToUsername());
        assertEquals(5, invitation.getIteration());
    }
}