package fr.uga.m1miage.pc.service;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.utils.UtilFunctions;
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
    private NotificationService notificationService;

    @InjectMocks
    private StrategyCompetitionService strategyCompetitionService;

    @Test
    public void testStartCompetition() {
        // Arrange
        String strategyGroupe210 = "DonnantDonnant";
        String strategyGroupe18 = "DonnantDonnant";
        int iterations = 1;
        String sessionId = "sessionId";

        // Act
        strategyCompetitionService.startCompetition(strategyGroupe210, strategyGroupe18, iterations, sessionId);

        // Assert
        verify(notificationService).sendCompetitionResult(anyString(), anyString());
    }

    @Test
    public void testTextForChoice() {
        assertEquals("Cooperer", strategyCompetitionService.textForChoice(Decision.COOPERATE));
        assertEquals("Trahir", strategyCompetitionService.textForChoice(Decision.BETRAY));
    }

    @Test
    public void testListMoveStringToTypeAction() {
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
    public void testGetScore() {
        // Arrange
        Decision myDecision = Decision.COOPERATE;
        Decision opponentDecision = Decision.COOPERATE;

        // Act
        int result = UtilFunctions.getScore(myDecision, opponentDecision);

        // Assert
        assertEquals(3, result);
    }

    @Test
    public void testGetScore_Betray() {
        // Arrange
        Decision myDecision = Decision.BETRAY;
        Decision opponentDecision = Decision.COOPERATE;

        // Act
        int result = UtilFunctions.getScore(myDecision, opponentDecision);

        // Assert
        assertEquals(5, result);
    }

    @Test
    public void testGetScore_OpponentBetray() {
        // Arrange
        Decision myDecision = Decision.COOPERATE;
        Decision opponentDecision = Decision.BETRAY;

        // Act
        int result = UtilFunctions.getScore(myDecision, opponentDecision);

        // Assert
        assertEquals(0, result);
    }

    @Test
    public void testGetScore_BothBetray() {
        // Arrange
        Decision myDecision = Decision.BETRAY;
        Decision opponentDecision = Decision.BETRAY;

        // Act
        int result = UtilFunctions.getScore(myDecision, opponentDecision);

        // Assert
        assertEquals(1, result);
    }
}