package fr.uga.m1miage.pc.model;

public class Invitation {
    private String fromPlayer;
    private String toUsername;
    private int iteration;
    
    public Invitation(String fromPlayer, String toUsername, int iteration) {
		this.fromPlayer = fromPlayer;
		this.toUsername = toUsername;
		this.iteration = iteration;
	}

	public Invitation() {
	}

	public String getFromPlayer() {
        return fromPlayer;
    }

    public void setFromPlayer(String fromPlayer) {
        this.fromPlayer = fromPlayer;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

	public int getIteration() {
		return iteration;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}
}
