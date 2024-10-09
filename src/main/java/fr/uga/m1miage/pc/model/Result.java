package fr.uga.m1miage.pc.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Result {
	String status;
	String parti; 
	String score;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getParti() {
		return parti;
	}
	public void setParti(String parti) {
		this.parti = parti;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return "Error converting to JSON: " + e.getMessage();
        }
    }
	
}
