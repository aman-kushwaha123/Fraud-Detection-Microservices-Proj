package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();		
		//ws -> WebSocket endpoint
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// /topic -> used for broadcasting messages to ALL subscribed clients
	    // /queue -> used for sending messages to a SPECIFIC user (point-to-point)
		 registry.enableSimpleBroker("/topic","/queue");
		 
		// /app -> prefix for messages sent FROM client TO server (@MessageMapping)
		registry.setApplicationDestinationPrefixes("/app");
		
		//registry.setUserDestinationPrefix("/user");
	    // /user -> required for user-specific messaging
	    // when backend uses convertAndSendToUser(),
	    // Spring internally maps it to:
	    // /user/{username}/queue/...
		
	}

}
