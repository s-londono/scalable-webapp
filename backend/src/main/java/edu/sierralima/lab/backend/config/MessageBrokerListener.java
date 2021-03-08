package edu.sierralima.lab.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.stereotype.Component;

@Component
public class MessageBrokerListener implements ApplicationListener<BrokerAvailabilityEvent> {
  private static final Logger logger = LoggerFactory.getLogger(MessageBrokerListener.class);

  @Override
  public void onApplicationEvent(BrokerAvailabilityEvent event) {
    logger.info("MessageBrokerEvent. Available? {}. {}", event.isBrokerAvailable(), event);
  }

}
