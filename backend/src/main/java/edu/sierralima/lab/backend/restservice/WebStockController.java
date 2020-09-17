package edu.sierralima.lab.backend.restservice;

import edu.sierralima.lab.backend.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/web")
public class WebStockController {
  private static final Logger logger = LoggerFactory.getLogger(WebStockController.class);

  @GetMapping("stock")
  public String getStock(Model model) {
    logger.debug("Get all Stock");

    Item i1 = new Item("A1", "Alpha Mk.I");
    Item i2 = new Item("A2", "Alpha Mk. II");

    Map<String, Item> result = new HashMap<>();
    result.put(i1.getSku(), i1);
    result.put(i2.getSku(), i2);

    model.addAttribute("stock", result);

    return "stock";
  }
}
