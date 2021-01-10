package edu.sierralima.lab.backend.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Stock {
  private final Logger logger = LoggerFactory.getLogger(Stock.class);

  // Key: SKU, value: Item with amount in stock
  private Map<String, ItemStock> skuItemStock;

  public Stock() {
    this.skuItemStock = new HashMap<>();
  }

  public void addItem(Item newItem) {
    Objects.requireNonNull(newItem);
    Objects.requireNonNull(newItem.getSku());

    ItemStock newItemStock = new ItemStock(newItem, 0.0);

    skuItemStock.put(newItem.getSku(), newItemStock);
    logger.debug("New Item added to Stock. {}", newItem);
  }

  public void addItemStock(String sku, double stockDelta) {
    ItemStock curItemStock = skuItemStock.get(sku);

    if (curItemStock == null) {
      throw new IllegalArgumentException("Invalid SKU " + sku);
    }

    double curAmount = Optional.ofNullable(curItemStock.getAmountInStock()).orElse(0.0);
    curItemStock.setAmountInStock(curAmount + stockDelta);

    skuItemStock.put(sku, curItemStock);
    logger.debug("Stock updated. {}. Prev: {}", curItemStock, curAmount);
  }

  public Map<String, ItemStock> getSkuItemsInStock() {
    return skuItemStock;
  }

  public void setSkuItemsInStock(Map<String, ItemStock> skuItemStock) {
    this.skuItemStock = skuItemStock;
  }

  @Override
  public String toString() {
    return "Stock{" +
      "skuItemStock=" + skuItemStock +
      '}';
  }
}
