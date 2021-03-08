package edu.sierralima.lab.backend.controller;

import edu.sierralima.lab.backend.dto.frontend.Event;
import edu.sierralima.lab.backend.dto.frontend.Event.Type;
import edu.sierralima.lab.backend.logic.InventoryKeeper;
import edu.sierralima.lab.backend.model.ItemStock;
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

import java.util.Optional;

@Controller
public class StockWebSocketController {
  private static final Logger logger = LoggerFactory.getLogger(StockWebSocketController.class);

  private final InventoryKeeper inventoryKeeper;

  @Autowired
  public StockWebSocketController(InventoryKeeper inventoryKeeper) {
    this.inventoryKeeper = inventoryKeeper;
  }

  @SubscribeMapping("/stockevent")
  @SendToUser("/queue/response")
  public Event<Stock> subscribeToTestTopic() {
    // Get current stock
    Stock stock = inventoryKeeper.getStock();

    // When a user subscribes to topic test, Spring will automatically send a message containing the return value
    // Refer to: https://dimitr.im/websockets-spring-boot#defining-a-controller
    return new Event<>(Type.STOCK_SNAPSHOT, stock);
  }

  @SubscribeMapping("/command")
  public void subscribeToCommandQueue() {
    logger.debug("Subscribed to command queue");
  }

  @MessageMapping("/stockoperation")
  @SendTo("/topic/stockevent")
  public Event<ItemStock> processStockOperation(Event<ItemStock> op) {
    if (op == null || op.getType() == null) {
      return null;
    }

    ItemStock itemStock = op.getPayload();
    String sku = itemStock.getItem() != null ? itemStock.getItem().getSku() : null;
    Double delta = Optional.ofNullable(itemStock.getAmountInStock()).orElse(0.0);

    if (op.getType() == Type.REQUEST_ALTER_ITEM_STOCK) {
      inventoryKeeper.getStock().addItemStock(sku, delta);

      ItemStock updtdItemStock = inventoryKeeper.getStock().getItemSotck(sku);
      return new Event<>(Event.Type.STOCK_UPDATE, updtdItemStock);
    }

    return null;
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
