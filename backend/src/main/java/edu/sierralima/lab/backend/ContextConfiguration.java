package edu.sierralima.lab.backend;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ContextConfiguration {

  @Bean
  public ClientConfig clientConfig() {
    ClientConfig hzClientCfg = new ClientConfig();

    hzClientCfg.getNetworkConfig()
        .addAddress("127.0.0.1")
        .setSmartRouting(false)
        .addOutboundPortDefinition("34700-34710")
        .setRedoOperation(false)
        .setConnectionTimeout(5000);

    return hzClientCfg;
  }

  @Bean
  @Primary
  public HazelcastInstance hazelcastClient(ClientConfig config) {
    return HazelcastClient.newHazelcastClient(config);
  }

}
