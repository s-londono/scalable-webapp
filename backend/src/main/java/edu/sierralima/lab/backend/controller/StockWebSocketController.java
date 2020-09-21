package edu.sierralima.lab.backend.controller;

import edu.sierralima.lab.backend.model.TestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class StockWebSocketController {
  private static final Logger logger = LoggerFactory.getLogger(StockWebSocketController.class);

  @MessageMapping("/broadcast")
  @SendTo("/topic/test")
  public TestMessage testBroadcast(TestMessage inMsg) {
    try {
      Thread.sleep(3000L);
    } catch (InterruptedException e) {
      logger.error("Interrupted while sleeping", e);
    }

    return new TestMessage("Broadcasting");
  }

  @MessageMapping("dialog")
  @SendToUser
  public TestMessage testDialog(TestMessage inMsg) {
    try {
      Thread.sleep(3000L);
    } catch (InterruptedException e) {
      logger.error("Interrupted while sleeping", e);
    }

    return new TestMessage("Chatting");
  }
}
