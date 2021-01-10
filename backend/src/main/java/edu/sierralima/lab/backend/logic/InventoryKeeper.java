package edu.sierralima.lab.backend.logic;

import com.hazelcast.core.HazelcastInstance;
import edu.sierralima.lab.backend.model.Item;
import edu.sierralima.lab.backend.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryKeeper {

  private final HazelcastInstance hzClient;

  private Stock stock;

  @Autowired
  public InventoryKeeper(HazelcastInstance hzClient) {
    this.hzClient = hzClient;

    this.initializeStock();
  }

  private void initializeStock() {
    this.stock = new Stock();

    Item i1 = new Item("Mk.I.black", "M1");
    Item i2 = new Item("Mk.II.red", "M2");
    Item i3 = new Item("Mk.III.red", "M3");

    this.stock.addItem(i1);
    this.stock.addItem(i2);
    this.stock.addItem(i3);
  }

  public Stock getStock() {
    return this.stock;
  }

}
