package edu.sierralima.lab.backend.logic;

import com.hazelcast.core.HazelcastInstance;
import edu.sierralima.lab.backend.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockKeeper {

  private final HazelcastInstance hzClient;

  private Stock stock;

  @Autowired
  public StockKeeper(HazelcastInstance hzClient) {
    this.hzClient = hzClient;
    this.stock = new Stock();
  }

}
