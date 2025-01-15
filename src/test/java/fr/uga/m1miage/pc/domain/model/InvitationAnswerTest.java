package fr.uga.m1miage.pc.domain.model;

import fr.uga.m1miage.pc.domain.model.InvitationAnswer;
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