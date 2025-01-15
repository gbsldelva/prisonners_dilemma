package fr.uga.m1miage.pc.domain.model;

import fr.uga.m1miage.pc.domain.model.Result;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResultTest {
    @Test
    void testSettersAndGetters() {
        Result result = new Result();

        result.setStatus("Success");
        assertEquals("Success", result.getStatus());

        result.setParti("Parti 1");
        assertEquals("Parti 1", result.getParti());

        result.setScore("10-0");
        assertEquals("10-0", result.getScore());
    }

    @Test
    void testToJson() {
        Result result = new Result();
        result.setStatus("Success");
        result.setParti("Parti 1");
        result.setScore("10-0");

        String json = result.toJson();
        assertNotNull(json);
        assertTrue(json.contains("\"status\":\"Success\""));
        assertTrue(json.contains("\"parti\":\"Parti 1\""));
        assertTrue(json.contains("\"score\":\"10-0\""));
    }
}