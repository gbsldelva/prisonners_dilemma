package fr.uga.m1miage.pc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameSession {
    private Player player1;
    private Player player2;
    private int totalIterations;
    private int currentIteration;
    List<String> player1Choices;
    List<String> player2Choices;

    public GameSession(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        player1Choices = new ArrayList<>();
        player2Choices = new ArrayList<>();
    }
	
	public void incrementIteration () {
		this.currentIteration++;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameSession)) return false;
        GameSession that = (GameSession) o;
        return Objects.equals(player1, that.player1) &&
               Objects.equals(player2, that.player2);
    }
	
	public boolean isRoundComplete() {
        return player1Choices.size() == player2Choices.size() && player1Choices.size() > currentIteration;
    }

    public boolean isGameOver() {
        return currentIteration >= totalIterations;
    }
    
    public boolean containsPlayer(String username) {
        return player1.getUsername().equals(username) || player2.getUsername().equals(username);
    }
	
    public String getScoreSummary() {
        return player1.getUsername() + "(" + player1.getScore() + ") - " +
               player2.getUsername() + "(" + player2.getScore() + ")";
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(player1, player2, totalIterations, currentIteration);
    }

    @Override
    public String toString() {
        return "GameSession{" +
                "player1=" + player1 +
                ", player2=" + player2 +
                ", totalIterations=" + totalIterations +
                ", currentIteration=" + currentIteration +
                '}';
    }
}
