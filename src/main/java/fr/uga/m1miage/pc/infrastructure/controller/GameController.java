package fr.uga.m1miage.pc.infrastructure.controller;

import fr.uga.m1miage.pc.domain.port.input.GamePort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import fr.uga.m1miage.pc.domain.model.ChoiceMessage;
import fr.uga.m1miage.pc.domain.model.Competition;
import fr.uga.m1miage.pc.domain.model.Invitation;
import fr.uga.m1miage.pc.domain.model.InvitationAnswer;
import fr.uga.m1miage.pc.domain.model.PlayAgainstServerRequest;
import fr.uga.m1miage.pc.domain.model.Player;
import fr.uga.m1miage.pc.application.game_service.GameSessionService;
import fr.uga.m1miage.pc.application.game_service.StrategyCompetitionService;

@Controller
public class GameController implements GamePort {

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

    @Override
    @MessageMapping("/playAgainstServer")
    public void playAgainstServer(@Payload PlayAgainstServerRequest request) {
        Player player = WebSocketController.connectedPlayers.get(request.getUsername());
        if (player == null) {
            return;
        }
        gameSessionService.playAgainstServer(player, request.getIterations());
    }
}

