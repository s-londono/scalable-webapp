package edu.sierralima.lab.backend.controller;

import edu.sierralima.lab.backend.model.TestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class StockWebSocketController {
  private static final Logger logger = LoggerFactory.getLogger(StockWebSocketController.class);

  @SubscribeMapping("/stock")
  public String subscribeToTestTopic() {
    // When a user subscribes to topic test, Spring will automatically send a message containing the return value
    // Refer to: https://dimitr.im/websockets-spring-boot#defining-a-controller
    return "Roger. Subscribed";
  }

  @MessageMapping("/broadcast")
  public TestMessage testBroadcast(TestMessage inMsg) {
    try {
      Thread.sleep(3000L);
    } catch (InterruptedException e) {
      logger.error("Interrupted while sleeping", e);
    }

    // The STOMP broker routes the return value of this method to: topic/broadcast (derived from the input destination)
    return new TestMessage("Broadcasting");
  }

  @MessageMapping("/broadcast-to-test")
  @SendTo("/topic/stock")
  public TestMessage testBroadcastToTest(TestMessage inMsg) {
    try {
      Thread.sleep(3000L);
    } catch (InterruptedException e) {
      logger.error("Interrupted while sleeping", e);
    }

    return new TestMessage("Broadcasting");
  }

  @MessageMapping("dialog")
  @SendToUser("/queue/command")
  public TestMessage testDialog(TestMessage inMsg) {
    try {
      Thread.sleep(3000L);
    } catch (InterruptedException e) {
      logger.error("Interrupted while sleeping", e);
    }

    return new TestMessage("Chatting");
  }
}
