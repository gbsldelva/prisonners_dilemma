package fr.uga.m1miage.pc.model;

public interface Strategy {

    String decideMove(GameSession session, Player player);
}
