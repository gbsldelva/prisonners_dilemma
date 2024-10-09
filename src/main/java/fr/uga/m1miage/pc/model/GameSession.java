package fr.uga.m1miage.pc.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GameSession {
    private Player player1;
    private Player player2;
    private int totalIterations;
    private int currentIteration;
    private Map<String, String> choices;

    public GameSession() {
        this.choices = new HashMap<>();
    }

    public GameSession(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.choices = new HashMap<>();
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getTotalIterations() {
        return totalIterations;
    }

    public void setTotalIterations(int totalIterations) {
        this.totalIterations = totalIterations;
    }

    public int getCurrentIteration() {
        return currentIteration;
    }

    public void setCurrentIteration(int currentIteration) {
        this.currentIteration = currentIteration;
    }

    public Map<String, String> getChoices() {
        return choices;
    }

    public void setChoices(Map<String, String> choices) {
        this.choices = choices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameSession)) return false;
        GameSession that = (GameSession) o;
        return totalIterations == that.totalIterations &&
               currentIteration == that.currentIteration &&
               Objects.equals(player1, that.player1) &&
               Objects.equals(player2, that.player2) &&
               Objects.equals(choices, that.choices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player1, player2, totalIterations, currentIteration, choices);
    }

    @Override
    public String toString() {
        return "GameSession{" +
                "player1=" + player1 +
                ", player2=" + player2 +
                ", totalIterations=" + totalIterations +
                ", currentIteration=" + currentIteration +
                ", choices=" + choices +
                '}';
    }
}
