package edu.sierralima.lab.backend.controller;

import edu.sierralima.lab.backend.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/service")
public class StockRestController {
  private static final Logger logger = LoggerFactory.getLogger(StockRestController.class);

  /**
   * Retrieves all items currently in Stock
   * @return All items in stock, indexed by SKU
   */
  @GetMapping("/stock")
  public Map<String, Item> getStock() {
    logger.debug("Get all Stock");

    Item i3 = new Item("A3", "Alpha Mk.III");

    Map<String, Item> result = new HashMap<>();
    result.put(i3.getSku(), i3);

    return result;
  }

  /**
   * Retrieves information about an item in Stock, given its SKU
   * @param sku SKU of the Item to be retrieved
   * @return Item in stock having the specified SKU
   */
  @GetMapping("/stock/{sku}")
  public Item getStockItem(@PathVariable String sku) {
    logger.debug("Get Stock Item: {}", sku);

    return null;
  }

  /**
   * Increments in one the Stock of an Item, given its SKU
   * @param sku SKU of an Item. The number of Items of this SKU will increase by one in the Stock
   */
  @PostMapping("/stock/{sku}/numInStock")
  public long incrementStockOfItem(@PathVariable String sku) {
    logger.debug("Increase Stock of Item: {}", sku);

    return 0L;
  }

  /**
   * Decreases in one the Stock of an Item, given its SKU
   * @param sku SKU of an Item. The number of Items of this SKU will decrease by one in the Stock
   */
  @DeleteMapping("/stock/{sku}/numInStock")
  public long decreaseStockOfItem(@PathVariable String sku) {
    logger.debug("Decrease Stock of Item: {}", sku);

    return 0L;
  }

}
