package fr.uga.m1miage.pc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Invitation {
	private String fromPlayer;
    private String toUsername;
    private int iteration;
}
