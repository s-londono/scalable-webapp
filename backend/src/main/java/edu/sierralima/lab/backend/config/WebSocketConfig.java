package edu.sierralima.lab.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    // Enables simple memory-based msg broker to carry messages back to the client on destinations prefixed with /topic
    config.enableSimpleBroker("/topic");

    // Prefix for messages that are bound for methods annotated with @MessageMapping (e.g. /stock-app/broadcast)
    config.setApplicationDestinationPrefixes("/stock-app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // The SockJS client will attempt to connect to /stock-ws and use the best available transport
    registry.addEndpoint("/stock-ws").withSockJS();
  }

}
