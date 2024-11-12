package fr.uga.m1miage.pc.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    private String username;
    private String sessionId;
    private int score;

    public Player(String username, String sessionId) {
        this.username = username;
        this.sessionId = sessionId;
        this.score = 0;
    }
    
    public Player(String username) {
    	this.username = username;
    }
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return username.equals(player.username);
    }
	
	public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return "Error converting to JSON: " + e.getMessage();
        }
    }
	
	@Override
    public int hashCode() {
        return (username + sessionId).hashCode();
    }
	
	@Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", score=" + score +
                '}';
    }
    
}
