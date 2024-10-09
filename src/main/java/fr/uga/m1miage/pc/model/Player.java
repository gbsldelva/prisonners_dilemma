package fr.uga.m1miage.pc.model;

public class Player {
    private String username;
    private String sessionId;
    private int score;
    private String choice;

    public Player() {}

    public Player(String username, String sessionId) {
        this.username = username;
        this.sessionId = sessionId;
        this.score = 0;
        this.choice = "";
    }
    
    public Player(String username) {
    	this.username = username;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return username.equals(player.username);
    }
	
	@Override
    public int hashCode() {
        return username.hashCode();
    }
	
	@Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", score=" + score +
                '}';
    }
    
}
