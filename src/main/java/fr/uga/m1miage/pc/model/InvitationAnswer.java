package fr.uga.m1miage.pc.model;

public class InvitationAnswer {
	private String message;
	private String oponentUsername;
	private String playerUsername;
	
	public InvitationAnswer(String message, String oponentUsername, String playerUsername) {
		this.message = message;
		this.oponentUsername = oponentUsername;
		this.playerUsername = playerUsername;
	}
	public InvitationAnswer() {
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOponentUsername() {
		return oponentUsername;
	}
	public void setOponentUsername(String oponentUsername) {
		this.oponentUsername = oponentUsername;
	}
	public String getPlayerUsername() {
		return playerUsername;
	}
	public void setPlayerUsername(String playerUsername) {
		this.playerUsername = playerUsername;
	}
}
