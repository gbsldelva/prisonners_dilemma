package fr.uga.m1miage.pc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    
    public List<String> getPlayer1Choices() {
		return player1Choices;
	}

	public void setPlayer1Choices(List<String> player1Choices) {
		this.player1Choices = player1Choices;
	}

	public List<String> getPlayer2Choices() {
		return player2Choices;
	}

	public void setPlayer2Choices(List<String> player2Choices) {
		this.player2Choices = player2Choices;
	}
	
	public void incrementIteration () {
		this.currentIteration++;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameSession)) return false;
        GameSession that = (GameSession) o;
        return totalIterations == that.totalIterations &&
               currentIteration == that.currentIteration &&
               Objects.equals(player1, that.player1) &&
               Objects.equals(player2, that.player2);
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
