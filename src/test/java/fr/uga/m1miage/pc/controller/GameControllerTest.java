package fr.uga.m1miage.pc.controller;

import fr.uga.m1miage.pc.model.ChoiceMessage;
import fr.uga.m1miage.pc.model.Competition;
import fr.uga.m1miage.pc.model.Invitation;
import fr.uga.m1miage.pc.model.InvitationAnswer;
import fr.uga.m1miage.pc.model.PlayAgainstServerRequest;
import fr.uga.m1miage.pc.model.Player;
import fr.uga.m1miage.pc.service.GameSessionService;
import fr.uga.m1miage.pc.service.StrategyCompetitionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

class GameControllerTest {

    @Mock
    private GameSessionService gameSessionService;

    @InjectMocks
    private GameController gameController;
    
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
        gameController.invitePlayer(invitation);
        verify(gameSessionService, times(1)).handleInvitation(invitation);
    }

    @Test
    void testPlayerResponseToInvitation() {
        InvitationAnswer answer = new InvitationAnswer("John", "Doe", "accepted");
        gameController.playerResponseToInvitation(answer);
        verify(gameSessionService, times(1)).handleInvitationAnswer(answer);
    }

    @Test
    void testMakeChoice() {
        ChoiceMessage choiceMessage = new ChoiceMessage("John", "c");
        gameController.makeChoice(choiceMessage);
        verify(gameSessionService, times(1)).handleChoice(choiceMessage);
    }
    
    @Test
    void testDisconnect() {
        String username = "John";
        gameController.disconnect(username);
        verify(gameSessionService, times(1)).handleDisconnection(username);
    }

    @Test
    void testStartCompetition() {
        Competition competition = new Competition("DonnantDonnant", "DonnantDonnant", "sessionId", 10);
        competition.setIterations(10);
        competition.setSessionId("sessionId");
        gameController.startCompetition(competition);
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
        gameController.playAgainstServer(request);
        Player playerWanted = WebSocketController.connectedPlayers.get(request.getUsername());
        assertNotNull(playerWanted);
        verify(gameSessionService, times(1)).playAgainstServer(playerWanted, request.getIterations());
    }
}
