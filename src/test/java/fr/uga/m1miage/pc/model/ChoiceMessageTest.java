package fr.uga.m1miage.pc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChoiceMessageTest {

    @Test
    void testGettersAndSetters() {
        ChoiceMessage choiceMessage = new ChoiceMessage("Player1", "C");
        
        assertEquals("Player1", choiceMessage.getUsername());
        assertEquals("C", choiceMessage.getChoice());
        
        choiceMessage.setUsername("Player2");
        choiceMessage.setChoice("D");
        
        assertEquals("Player2", choiceMessage.getUsername());
        assertEquals("D", choiceMessage.getChoice());
    }

    @Test
    void testEqualsAndHashCode() {
        ChoiceMessage choiceMessage1 = new ChoiceMessage("Player1", "C");
        ChoiceMessage choiceMessage2 = new ChoiceMessage("Player1", "C");
        ChoiceMessage choiceMessage3 = new ChoiceMessage("Player2", "D");

        assertEquals(choiceMessage1, choiceMessage2); // Equal messages should be equal
        assertNotEquals(choiceMessage1, choiceMessage3); // Different messages should not be equal
        assertEquals(choiceMessage1.hashCode(), choiceMessage2.hashCode()); // Same hash code for equal objects
    }

    @Test
    void testToString() {
        ChoiceMessage choiceMessage = new ChoiceMessage("Player1", "C");
        String expectedString = "ChoiceMessage{username='Player1', choice='C'}";
        assertEquals(expectedString, choiceMessage.toString());
    }
}
