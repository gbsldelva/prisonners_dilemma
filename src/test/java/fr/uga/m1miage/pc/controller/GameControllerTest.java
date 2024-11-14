package fr.uga.m1miage.pc.controller;

import fr.uga.m1miage.pc.model.ChoiceMessage;
import fr.uga.m1miage.pc.model.Invitation;
import fr.uga.m1miage.pc.model.InvitationAnswer;
import fr.uga.m1miage.pc.service.GameSessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class GameControllerTest {

    @Mock
    private GameSessionService gameSessionService;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
}
