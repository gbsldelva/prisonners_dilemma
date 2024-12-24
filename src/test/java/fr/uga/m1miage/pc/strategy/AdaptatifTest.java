package fr.uga.m1miage.pc.strategy;

import fr.uga.m1miage.pc.model.Decision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdaptatifTest {

    private Adaptatif adaptatif;

    @BeforeEach
    void setUp() {
        adaptatif = new Adaptatif();
    }

    @Test
    void playNextMove_shouldPlayInitialSequence() {
        List<Decision> myMoves = new ArrayList<>();
        List<Decision> opponentMoves = new ArrayList<>();

        // Test each move in the initial sequence
        for (Decision expectedMove : Adaptatif.INITIAL_SEQUENCE) {
            assertEquals(expectedMove, adaptatif.playNextMove(myMoves, opponentMoves));
            myMoves.add(expectedMove);
        }
    }

    @Test
    void playNextMove_shouldCooperateWhenCoopScoreIsHigher() {
        List<Decision> myMoves = Arrays.asList(Adaptatif.INITIAL_SEQUENCE);
        List<Decision> opponentMoves = Arrays.asList(Adaptatif.INITIAL_SEQUENCE);

        adaptatif.updateScores("c", "c"); // +3 to coopScore
        adaptatif.updateScores("c", "c"); // +3 to coopScore
        assertEquals("c", adaptatif.playNextMove(myMoves, opponentMoves).getValue());
    }

    @Test
    void playNextMove_shouldBetrayWhenBetrayScoreIsHigher() {
        List<Decision> myMoves = Arrays.asList(Adaptatif.INITIAL_SEQUENCE);
        List<Decision> opponentMoves = Arrays.asList(Adaptatif.INITIAL_SEQUENCE);

        adaptatif.updateScores("t", "c"); // +5 to betrayScore
        adaptatif.updateScores("t", "c"); // +5 to betrayScore
        assertEquals(Decision.COOPERATE, adaptatif.playNextMove(myMoves, opponentMoves));
    }

    @Test
    void playNextMove_shouldChooseRandomlyWhenScoresAreEqual() {
        List<Decision> myMoves = Arrays.asList(Adaptatif.INITIAL_SEQUENCE);
        List<Decision> opponentMoves = Arrays.asList(Adaptatif.INITIAL_SEQUENCE);

        // Call method and verify result
        Decision move = adaptatif.playNextMove(myMoves, opponentMoves);
        assertEquals(Decision.COOPERATE, move);
    }

    @Test
    void updateScores_shouldIncreaseCoopScoreForCC() {
        adaptatif.updateScores("c", "c");
        assertEquals(3, adaptatif.coopScore);
        assertEquals(0, adaptatif.betrayScore);
    }

    @Test
    void updateScores_shouldNotIncreaseCoopScoreForCT() {
        adaptatif.updateScores("c", "t");
        assertEquals(0, adaptatif.coopScore);
        assertEquals(0, adaptatif.betrayScore);
    }

    @Test
    void updateScores_shouldIncreaseBetrayScoreForTC() {
        adaptatif.updateScores("t", "c");
        assertEquals(0, adaptatif.coopScore);
        assertEquals(5, adaptatif.betrayScore);
    }

    @Test
    void updateScores_shouldIncreaseBetrayScoreForTT() {
        adaptatif.updateScores("t", "t");
        assertEquals(0, adaptatif.coopScore);
        assertEquals(1, adaptatif.betrayScore);
    }
}
