package fr.uga.m1miage.pc.domain.model;

public enum Decision {
    COOPERATE("c"),
    BETRAY("t");

    private final String value;
    Decision(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Decision fromString(String value) {
        for (Decision decision : Decision.values()) {
            if (decision.value.equals(value)) {
                return decision;
            }
        }
        throw new IllegalArgumentException("Valeur decision inconnue : " + value);
    }
}
