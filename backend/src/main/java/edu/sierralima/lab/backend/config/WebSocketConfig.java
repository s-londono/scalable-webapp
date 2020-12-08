package edu.sierralima.lab.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    // Enables memory-based msg broker to carry messages back to the client on destinations with the specified prefixes
    // Refer to: https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-stomp
    config.enableSimpleBroker("/topic", "/queue")
      .setTaskScheduler(buildHeartbeatScheduler())
      .setHeartbeatValue(new long[] {10000, 15000}); // 1st: Incoming heartbeat time, 2nd: Outgoing heartbeat time

    // Prefix for messages that are bound for methods annotated with @MessageMapping (e.g. /stock-app/broadcast)
    config.setApplicationDestinationPrefixes("/stock-app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // The SockJS client will attempt to connect to /stock-ws and use the best available transport
    // Refer to: https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket
    registry.addEndpoint("/stock-ws").withSockJS();
  }

  private ThreadPoolTaskScheduler buildHeartbeatScheduler() {
    ThreadPoolTaskScheduler tPoolSchdlr = new ThreadPoolTaskScheduler();
    tPoolSchdlr.setPoolSize(2);
    tPoolSchdlr.setThreadNamePrefix("wss-heartbeat-thread");
    tPoolSchdlr.initialize();

    return tPoolSchdlr;
  }

}
