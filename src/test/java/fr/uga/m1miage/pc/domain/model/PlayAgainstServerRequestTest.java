package fr.uga.m1miage.pc.domain.model;

import fr.uga.m1miage.pc.domain.model.PlayAgainstServerRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayAgainstServerRequestTest {

    @Test
    void testGettersAndSetters() {
        PlayAgainstServerRequest request = new PlayAgainstServerRequest();
        
        request.setUsername("Player1");
        request.setIterations(5);
        
        assertEquals("Player1", request.getUsername());
        assertEquals(5, request.getIterations());
    }

    @Test
    void testEqualsAndHashCode() {
        PlayAgainstServerRequest request1 = new PlayAgainstServerRequest();
        request1.setUsername("Player1");
        request1.setIterations(5);

        PlayAgainstServerRequest request2 = new PlayAgainstServerRequest();
        request2.setUsername("Player1");
        request2.setIterations(5);

        PlayAgainstServerRequest request3 = new PlayAgainstServerRequest();
        request3.setUsername("Player2");
        request3.setIterations(3);

        assertEquals(request1, request2); 
        assertNotEquals(request1, request3); 
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testDefaultValues() {
        PlayAgainstServerRequest request = new PlayAgainstServerRequest();

        assertNull(request.getUsername()); 
        assertEquals(0, request.getIterations()); 
    }
}
