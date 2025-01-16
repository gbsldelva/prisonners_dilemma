package fr.uga.m1miage.pc.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Competition {
	String groupe210Strategy;
	String groupe18Strategy;
	String sessionId;
	int iterations;
}
