package com.chat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * @author Shreya Viramgama
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketDefaultConfig extends AbstractWebSocketMessageBrokerConfigurer {
	static final Logger logger = LoggerFactory.getLogger(WebSocketDefaultConfig.class);

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic/", "/queue/");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat").setAllowedOrigins("*").setAllowedOrigins("*").withSockJS()
				.setHeartbeatTime(60000);

	}

}