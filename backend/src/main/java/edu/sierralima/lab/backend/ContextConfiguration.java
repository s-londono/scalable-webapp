package edu.sierralima.lab.backend;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ContextConfiguration {

  private ConfigProperties config;

  public ContextConfiguration(ConfigProperties config) {
    this.config = config;
  }

  @Bean
  public ClientConfig clientConfig() {
    ClientConfig hzClientCfg = new ClientConfig();

    hzClientCfg.getNetworkConfig()
        .addAddress(config.getHazelcastBindAddress())
        .setSmartRouting(false)
        .addOutboundPortDefinition(config.getHazelcastOutboundPortDef())
        .setRedoOperation(false)
        .setConnectionTimeout(config.getHazelcastConnectionTimeout());

    return hzClientCfg;
  }

  @Bean
  @Primary
  public HazelcastInstance hazelcastClient(ClientConfig config) {
    return HazelcastClient.newHazelcastClient(config);
  }

}
