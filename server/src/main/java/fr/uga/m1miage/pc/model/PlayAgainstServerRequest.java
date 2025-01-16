package fr.uga.m1miage.pc.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode

public class PlayAgainstServerRequest {
    private String username;
    private int iterations;
}
