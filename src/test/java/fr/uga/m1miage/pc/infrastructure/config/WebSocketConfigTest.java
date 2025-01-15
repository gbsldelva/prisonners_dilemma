package fr.uga.m1miage.pc.infrastructure.config;

import fr.uga.m1miage.pc.infrastructure.config.WebSocketConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class WebSocketConfigTest {

    private WebSocketConfig webSocketConfig;
    private StompEndpointRegistry stompEndpointRegistry;
    private StompWebSocketEndpointRegistration stompWebSocketEndpointRegistration;

    @BeforeEach
    public void setup() {
        // Initialize the WebSocketConfig and mock dependencies
        webSocketConfig = new WebSocketConfig();
        stompEndpointRegistry = Mockito.mock(StompEndpointRegistry.class);
        stompWebSocketEndpointRegistration = Mockito.mock(StompWebSocketEndpointRegistration.class);

        // Mock the return value for addEndpoint() to return the mocked StompWebSocketEndpointRegistration
        when(stompEndpointRegistry.addEndpoint("/ws")).thenReturn(stompWebSocketEndpointRegistration);
        
        // Mock the return value for setAllowedOriginPatterns() to allow method chaining
        when(stompWebSocketEndpointRegistration.setAllowedOriginPatterns(Mockito.any())).thenReturn(stompWebSocketEndpointRegistration);
    }

    @Test
    void testRegisterStompEndpoints() {
        // Act
        webSocketConfig.registerStompEndpoints(stompEndpointRegistry);

        // Assert
        // Verify that the endpoint is registered and SockJS is enabled
        verify(stompWebSocketEndpointRegistration, times(1)).setAllowedOriginPatterns(Mockito.any());
        verify(stompWebSocketEndpointRegistration, times(1)).withSockJS();
    }
}
