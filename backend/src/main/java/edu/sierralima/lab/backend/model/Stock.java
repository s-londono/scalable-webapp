package edu.sierralima.lab.backend.model;

import java.util.HashMap;
import java.util.Map;

public class Stock {

  private Map<String, Item> skuIndexedItems;

  public Stock() {
    this.skuIndexedItems = new HashMap<>();
  }

  public Map<String, Item> getSkuIndexedItems() {
    return skuIndexedItems;
  }

  public void setSkuIndexedItems(Map<String, Item> skuIndexedItems) {
    this.skuIndexedItems = skuIndexedItems;
  }

}
