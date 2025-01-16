package fr.uga.m1miage.pc.domain.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChoiceMessage {
    private String username;
    private String choice; // "c" or "t"

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChoiceMessage)) return false;
        ChoiceMessage that = (ChoiceMessage) o;
        return Objects.equals(username, that.username) && 
               Objects.equals(choice, that.choice);
    }

    public Decision getDecision() {
        return Decision.fromString(this.getChoice());
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
