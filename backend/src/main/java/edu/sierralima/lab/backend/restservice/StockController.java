package edu.sierralima.lab.backend.restservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StockController {
  private static final Logger logger = LoggerFactory.getLogger(StockController.class);

  @GetMapping("/stock")
  public Map<Long, Item> getStock(@RequestParam(value = "code", required = false, defaultValue = "A") String code) {
    logger.debug("Getting Stock. Code: {}", code);

    Item i1 = new Item(1L, "Alpha");
    Item i2 = new Item(2L, "Bravo");

    return Map.of(i1.getId(), i1, i2.getId(), i2);
  }

}
