package fr.uga.m1miage.pc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

 @Override
 public void configureMessageBroker(@NonNull MessageBrokerRegistry config) {
     config.enableSimpleBroker("/topic", "/queue", "/user");
     config.setApplicationDestinationPrefixes("/app");
 }
 
 @Override
 public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
     registry.addEndpoint("/ws")
     .setAllowedOriginPatterns("*")
     .withSockJS();
 }
}

