package fr.uga.m1miage.pc.service;

import fr.uga.m1miage.pc.model.Decision;
import fr.uga.m1miage.pc.model.GameSession;
import fr.uga.m1miage.pc.model.Player;
import fr.uga.m1miage.pc.service.StrategyCompetitionService;
import fr.uga.m1miage.pc.strategy.StrategyFactory;
import fr.uga.m1miage.pc.strategy.StrategyType;
import fr.uga.m1miage.pc.utils.UtilFunctions;
import fr.uga.strats.g8.enums.TypeAction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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

    private Player player1;
    private Player player2;
    private GameSession gameSession;

    @BeforeEach
    public void setup() {
        player1 = new Player("Groupe Gabriel & Nadine", "id1");
        player2 = new Player("Groupe Amine & Manal", "id2");
        gameSession = new GameSession(player1, player2);
    }

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
        // Arrange
        String choice = "c";

        // Act
        String result = strategyCompetitionService.textForChoice(choice);

        // Assert
        assertEquals("Coopérer", result);
    }

    @Test
    public void testListMoveStringToTypeAction() {
        // Arrange
        List<String> moveStrings = new ArrayList<>();
        moveStrings.add("c");
        moveStrings.add("t");

        // Act
        List<TypeAction> result = StrategyCompetitionService.listMoveStringToTypeAction(moveStrings);

        // Assert
        assertEquals(2, result.size());
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