package fr.uga.m1miage.pc.model;

import java.util.Objects;

public class ChoiceMessage {
    private String username;
    private String choice; // "c" or "t"

    public ChoiceMessage(String username, String choice) {
        this.username = username;
        this.choice = choice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChoiceMessage)) return false;
        ChoiceMessage that = (ChoiceMessage) o;
        return Objects.equals(username, that.username) && 
               Objects.equals(choice, that.choice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, choice);
    }

    @Override
    public String toString() {
        return "ChoiceMessage{" +
                "username='" + username + '\'' +
                ", choice='" + choice + '\'' +
                '}';
    }
}
