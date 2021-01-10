package edu.sierralima.lab.backend.controller;

import edu.sierralima.lab.backend.dto.frontend.Event;
import edu.sierralima.lab.backend.dto.frontend.Event.Type;
import edu.sierralima.lab.backend.logic.InventoryKeeper;
import edu.sierralima.lab.backend.model.Stock;
import edu.sierralima.lab.backend.model.TestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class StockWebSocketController {
  private static final Logger logger = LoggerFactory.getLogger(StockWebSocketController.class);

  private InventoryKeeper inventoryKeeper;

  @Autowired
  public StockWebSocketController(InventoryKeeper inventoryKeeper) {
    this.inventoryKeeper = inventoryKeeper;
  }

  @SubscribeMapping("/stock")
  public Event subscribeToTestTopic() {
    // Get current stock
    Stock stock = inventoryKeeper.getStock();

    Event<Stock> curStockEvent = new Event<>(Type.STOCK_SNAPSHOT, stock);

    // When a user subscribes to topic test, Spring will automatically send a message containing the return value
    // Refer to: https://dimitr.im/websockets-spring-boot#defining-a-controller
    return curStockEvent;
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
