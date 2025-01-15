package fr.uga.m1miage.pc.domain.model;

import fr.uga.m1miage.pc.domain.model.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void testGettersAndSetters() {
        Message message = new Message("info", "Hello, World!");
        
        assertEquals("info", message.getType());
        assertEquals("Hello, World!", message.getPayload());
        
        message.setType("warning");
        message.setPayload("This is a warning!");
        
        assertEquals("warning", message.getType());
        assertEquals("This is a warning!", message.getPayload());
    }

    @Test
    void testEqualsAndHashCode() {
        Message message1 = new Message("info", "Hello, World!");
        Message message2 = new Message("info", "Hello, World!");
        Message message3 = new Message("error", "This is an error!");

        assertEquals(message1, message2); // Equal messages should be equal
        assertNotEquals(message1, message3); // Different messages should not be equal
        assertEquals(message1.hashCode(), message2.hashCode()); // Same hash code for equal objects
    }

    @Test
    void testToString() {
        Message message = new Message("info", "Hello, World!");
        String expectedString = "Message{type='info', payload=Hello, World!}";
        assertEquals(expectedString, message.toString());
    }
}
