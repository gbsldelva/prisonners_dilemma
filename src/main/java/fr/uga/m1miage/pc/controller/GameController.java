package fr.uga.m1miage.pc.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import fr.uga.m1miage.pc.model.ChoiceMessage;
import fr.uga.m1miage.pc.model.Competition;
import fr.uga.m1miage.pc.model.Invitation;
import fr.uga.m1miage.pc.model.InvitationAnswer;
import fr.uga.m1miage.pc.model.PlayAgainstServerRequest;
import fr.uga.m1miage.pc.model.Player;
import fr.uga.m1miage.pc.service.GameSessionService;
import fr.uga.m1miage.pc.service.StrategyCompetitionService;

@Controller
public class GameController {

    private final GameSessionService gameSessionService;
    private StrategyCompetitionService competitionService;

    public GameController(GameSessionService gameSessionService, StrategyCompetitionService competitionService) {
        this.gameSessionService = gameSessionService;
        this.competitionService = competitionService;
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
    @MessageMapping("/disconnect")
    public void disconnect(@Payload String username) {
        gameSessionService.handleDisconnection(username);
    }
    
    @MessageMapping("/startCompetition")
    public void startCompetition(Competition competition) {
    	if (competition.getIterations() > 0)
    		competitionService.startCompetition(competition.getGroupe210Strategy(), competition.getGroupe18Strategy(), competition.getIterations(), competition.getSessionId());
    }

    @MessageMapping("/playAgainstServer")
    public void playAgainstServer(@Payload PlayAgainstServerRequest request) {
        Player player = WebSocketController.connectedPlayers.get(request.getUsername());
        if (player == null) {
            return;
        }
        gameSessionService.playAgainstServer(player, request.getIterations());
    }
}

