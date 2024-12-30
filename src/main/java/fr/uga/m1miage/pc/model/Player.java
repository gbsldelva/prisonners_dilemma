package fr.uga.m1miage.pc.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.uga.m1miage.pc.strategy.StrategyType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Player {
    @Id
    private String id; // The id of the player will be considered as the ID
    private String sessionId;
    private int score;
    private boolean isServer;
    private StrategyType strategy;
    private boolean isPlaying;

    public Player() {
        // No-args constructor for deserialization
    }


    public Player(String id, String sessionId ) {
        this.id = id;
        this.sessionId = sessionId;
        this.score = 0;
        this.strategy = null;
        this.isServer = false;
        isPlaying = false;
    }
    
    public Player(String id) {
    	this.id = id;
    }

    public Player(String id, String sessionId, StrategyType strategy) {
        this.id = id;
        this.sessionId = sessionId;
        this.score = 0;
        this.strategy = strategy;
        this.isServer = false;
        isPlaying = false;
    }


    public Player(String id, String sessionId, StrategyType strategy, boolean isServer) {
        this.id = id;
        this.sessionId = sessionId;
        this.score = 0;
        this.strategy = strategy;
        this.isServer = isServer;
        isPlaying = false;
    }

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return id.equals(player.id);
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
        return (id + sessionId).hashCode();
    }
	
	@Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", score=" + score +
                ", strategy=" + strategy +
                '}';
    }
}
