package fr.uga.m1miage.pc.controller;

import fr.uga.m1miage.pc.model.ChoiceMessage;
import fr.uga.m1miage.pc.model.Invitation;
import fr.uga.m1miage.pc.model.InvitationAnswer;
import fr.uga.m1miage.pc.service.GameSessionService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    private final GameSessionService gameSessionService;

    public GameController(GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    @MessageMapping("/invite")
    public void invitePlayer(@Payload Invitation invitation) {
        gameSessionService.handleInvitation(invitation);
    }

    @MessageMapping("/invitationAnswer")
    public void playerResponseToInvitation(@Payload InvitationAnswer answer) {
        gameSessionService.handleInvitationAnswer(answer);
    }

    @MessageMapping("/makeChoice")
    public void makeChoice(@Payload ChoiceMessage choiceMessage) {
        gameSessionService.handleChoice(choiceMessage);
    }
}
