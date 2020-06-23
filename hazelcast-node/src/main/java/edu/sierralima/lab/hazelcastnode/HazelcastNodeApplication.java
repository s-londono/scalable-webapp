package edu.sierralima.lab.hazelcastnode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class HazelcastNodeApplication {

  public static void main(String[] args) {
    SpringApplication.run(HazelcastNodeApplication.class, args);
  }

}
