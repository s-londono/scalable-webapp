package edu.sierralima.lab.backend.simulation;

import edu.sierralima.lab.backend.dto.frontend.Event;
import edu.sierralima.lab.backend.logic.InventoryKeeper;
import edu.sierralima.lab.backend.model.ItemStock;
import edu.sierralima.lab.backend.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.Random;

@Component
public class StockActivitySimulator {

  private final SimpMessagingTemplate msgTmplt;

  private final InventoryKeeper inventoryKeeper;

  private final Random rand;

  @Autowired
  public StockActivitySimulator(SimpMessagingTemplate msgTmplt, InventoryKeeper inventoryKeeper) {
    this.msgTmplt = msgTmplt;
    this.inventoryKeeper = inventoryKeeper;
    this.rand = new Random(Instant.now().toEpochMilli());
  }

  @Scheduled(initialDelay = 3000L, fixedRate = 20000L)
  public void simulateUpdate() {
    ItemStock itemStock = chooseRandomItemInStock();

    if (itemStock != null) {
      double curStockAmount = itemStock.getAmountInStock();
      double newStockAmount = curStockAmount + (double) (rand.nextInt(1001) - Math.round(curStockAmount));

      ItemStock newItemStock = new ItemStock(itemStock.getItem(), newStockAmount);
      Event<ItemStock> ev = new Event<>(Event.Type.STOCK_UPDATE, newItemStock);

      msgTmplt.convertAndSend("/topic/stock/event", ev);
    }
  }

  public ItemStock chooseRandomItemInStock() {
    Stock stock = inventoryKeeper.getStock();
    Map<String, ItemStock> mapSkuItemsInStock = stock.getSkuItemsInStock();
    int countItems = mapSkuItemsInStock.size();

    return mapSkuItemsInStock.values().toArray(new ItemStock[countItems])[rand.nextInt(countItems)];
  }

}
