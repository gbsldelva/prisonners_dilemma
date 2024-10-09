package fr.uga.m1miage.pc.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvitationAnswerTest {
    @Test
    void testSettersAndGetters() {
        InvitationAnswer invitationAnswer = new InvitationAnswer();

        invitationAnswer.setMessage("Hello!");
        assertEquals("Hello!", invitationAnswer.getMessage());

        invitationAnswer.setOponentUsername("oponent");
        assertEquals("oponent", invitationAnswer.getOponentUsername());

        invitationAnswer.setPlayerUsername("player");
        assertEquals("player", invitationAnswer.getPlayerUsername());
    }
}