package fr.uga.m1miage.pc.infrastructure.adapter;

import fr.uga.m1miage.pc.domain.model.ChoiceMessage;
import fr.uga.m1miage.pc.domain.model.Competition;
import fr.uga.m1miage.pc.domain.model.Invitation;
import fr.uga.m1miage.pc.domain.model.InvitationAnswer;
import fr.uga.m1miage.pc.domain.model.PlayAgainstServerRequest;
import fr.uga.m1miage.pc.domain.model.Player;
import fr.uga.m1miage.pc.application.game_service.GameSessionService;
import fr.uga.m1miage.pc.application.game_service.StrategyCompetitionService;

import fr.uga.m1miage.pc.infrastructure.controller.WebSocketController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

class GameControllerAdapterTest {

    @Mock
    private GameSessionService gameSessionService;

    @InjectMocks
    private GameControllerAdapter gameControllerAdapter;
    
    @Mock
    private StrategyCompetitionService competitionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        WebSocketController.connectedPlayers = new HashMap<>();
    }

    @Test
    void testInvitePlayer() {
        Invitation invitation = new Invitation("John", "Doe", 5);
        gameControllerAdapter.invitePlayer(invitation);
        verify(gameSessionService, times(1)).handleInvitation(invitation);
    }

    @Test
    void testPlayerResponseToInvitation() {
        InvitationAnswer answer = new InvitationAnswer("John", "Doe", "accepted");
        gameControllerAdapter.playerResponseToInvitation(answer);
        verify(gameSessionService, times(1)).handleInvitationAnswer(answer);
    }

    @Test
    void testMakeChoice() {
        ChoiceMessage choiceMessage = new ChoiceMessage("John", "c");
        gameControllerAdapter.makeChoice(choiceMessage);
        verify(gameSessionService, times(1)).handleChoice(choiceMessage);
    }
    
    @Test
    void testDisconnect() {
        String username = "John";
        gameControllerAdapter.disconnect(username);
        verify(gameSessionService, times(1)).handleDisconnection(username);
    }

    @Test
    void testStartCompetition() {
        Competition competition = new Competition("DonnantDonnant", "DonnantDonnant", "sessionId", 10);
        competition.setIterations(10);
        competition.setSessionId("sessionId");
        gameControllerAdapter.startCompetition(competition);
        verify(competitionService, times(1)).startCompetition(competition.getGroupe210Strategy(), competition.getGroupe18Strategy(), competition.getIterations(), competition.getSessionId());
    }

    @Test
    void testPlayAgainstServer() {
        PlayAgainstServerRequest request = new PlayAgainstServerRequest();
        Player player = new Player();
        player.setUsername("John");
        request.setUsername("John");
        request.setIterations(10);
        WebSocketController.connectedPlayers.put("John", player);
        gameControllerAdapter.playAgainstServer(request);
        Player playerWanted = WebSocketController.connectedPlayers.get(request.getUsername());
        assertNotNull(playerWanted);
        verify(gameSessionService, times(1)).playAgainstServer(playerWanted, request.getIterations());
    }
}
