package edu.sierralima.lab.backend.restservice;

import edu.sierralima.lab.backend.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StockController {
  private static final Logger logger = LoggerFactory.getLogger(StockController.class);

  /**
   * Retrieves all items currently in Stock
   * @return All items in stock, indexed by SKU
   */
  @GetMapping("/stock")
  public Map<String, Item> getStock() {
    logger.debug("Get all Stock");

    Item i1 = new Item("A1", "Alpha Mk.I");
    Item i2 = new Item("A2", "Alpha Mk. II");

    Map<String, Item> result = new HashMap<>();
    result.put(i1.getSku(), i1);
    result.put(i2.getSku(), i2);

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
