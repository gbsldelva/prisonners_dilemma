package fr.uga.m1miage.pc.application.game_service;

import fr.uga.m1miage.pc.domain.model.Decision;
import fr.uga.m1miage.pc.domain.utils.UtilFunctions;
import fr.uga.m1miage.pc.infrastructure.adapter.NotificationServiceAdapter;
import fr.uga.strats.g8.enums.TypeAction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StrategyCompetitionServiceTest {

    @Mock
    private NotificationServiceAdapter notificationServiceAdapter;

    @InjectMocks
    private StrategyCompetitionService strategyCompetitionService;

    @Test
    void testStartCompetition() {
        // Arrange
        String strategyGroupe210 = "DonnantDonnant";
        String strategyGroupe18 = "DonnantDonnant";
        int iterations = 1;
        String sessionId = "sessionId";

        // Act
        strategyCompetitionService.startCompetition(strategyGroupe210, strategyGroupe18, iterations, sessionId);

        // Assert
        verify(notificationServiceAdapter).sendCompetitionResult(anyString(), anyString());
    }

    @Test
    void testTextForChoice() {
        assertEquals("Cooperer", strategyCompetitionService.textForChoice(Decision.COOPERATE));
        assertEquals("Trahir", strategyCompetitionService.textForChoice(Decision.BETRAY));
    }

    @Test
    void testListMoveStringToTypeAction() {
        // Arrange
        List<Decision> decisions = Arrays.asList(Decision.BETRAY, Decision.COOPERATE);

        // Act
        List<TypeAction> result = StrategyCompetitionService.listMoveStringToTypeAction(decisions);

        // Assert
        assertEquals(2, result.size());
        assertEquals(TypeAction.TRAHIR, result.get(0));
        assertEquals(TypeAction.COOPERER, result.get(1));
    }

    @Test
    void testGetScore() {
        // Arrange
        Decision myDecision = Decision.COOPERATE;
        Decision opponentDecision = Decision.COOPERATE;

        // Act
        int result = UtilFunctions.getScore(myDecision, opponentDecision);

        // Assert
        assertEquals(3, result);
    }

    @Test
    void testGetScore_Betray() {
        // Arrange
        Decision myDecision = Decision.BETRAY;
        Decision opponentDecision = Decision.COOPERATE;

        // Act
        int result = UtilFunctions.getScore(myDecision, opponentDecision);

        // Assert
        assertEquals(5, result);
    }

    @Test
    void testGetScore_OpponentBetray() {
        // Arrange
        Decision myDecision = Decision.COOPERATE;
        Decision opponentDecision = Decision.BETRAY;

        // Act
        int result = UtilFunctions.getScore(myDecision, opponentDecision);

        // Assert
        assertEquals(0, result);
    }

    @Test
    void testGetScore_BothBetray() {
        // Arrange
        Decision myDecision = Decision.BETRAY;
        Decision opponentDecision = Decision.BETRAY;

        // Act
        int result = UtilFunctions.getScore(myDecision, opponentDecision);

        // Assert
        assertEquals(1, result);
    }
}