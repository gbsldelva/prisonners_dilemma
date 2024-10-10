package fr.uga.m1miage.pc.model;

public class AlwaysCooperateStrategy implements Strategy {

    @Override
    public String decideMove(GameSession session, Player player) {
        return "c";
    }
}
