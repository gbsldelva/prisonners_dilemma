package fr.uga.m1miage.pc.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvitationAnswerTest {
    @Test
    void testSettersAndGetters() {
        InvitationAnswer invitationAnswer = new InvitationAnswer("Hello!", "oponent", "player");
        assertEquals("Hello!", invitationAnswer.getMessage());
        assertEquals("oponent", invitationAnswer.getOponentUsername());
        assertEquals("player", invitationAnswer.getPlayerUsername());
    }
}