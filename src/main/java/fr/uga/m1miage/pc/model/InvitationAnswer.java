package fr.uga.m1miage.pc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InvitationAnswer {
	private String message;
	private String oponentUsername;
	private String playerUsername;
}
