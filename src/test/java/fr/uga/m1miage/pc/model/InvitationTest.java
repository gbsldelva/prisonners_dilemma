package fr.uga.m1miage.pc.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvitationTest {
    @Test
    void testSettersAndGetters() {
        Invitation invitation = new Invitation();

        invitation.setFromPlayer("Player 1");
        assertEquals("Player 1", invitation.getFromPlayer());

        invitation.setToUsername("username");
        assertEquals("username", invitation.getToUsername());

        invitation.setIteration(5);
        assertEquals(5, invitation.getIteration());
    }
}