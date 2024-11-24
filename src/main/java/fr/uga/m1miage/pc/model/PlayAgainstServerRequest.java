package fr.uga.m1miage.pc.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayAgainstServerRequest {
    private String username;
    private int iterations;
}
