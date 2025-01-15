package fr.uga.m1miage.pc.domain.port.input;

import fr.uga.m1miage.pc.domain.model.*;
import org.springframework.messaging.handler.annotation.Payload;

public interface GamePort {
    void playAgainstServer(PlayAgainstServerRequest request);
    void startCompetition(Competition competition);
    void disconnect(@Payload String username);
    void makeChoice(@Payload ChoiceMessage choiceMessage);
    void playerResponseToInvitation(@Payload InvitationAnswer answer);
    void invitePlayer(@Payload Invitation invitation);
}
