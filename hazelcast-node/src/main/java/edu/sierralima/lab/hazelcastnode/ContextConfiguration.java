package edu.sierralima.lab.hazelcastnode;

import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ContextConfiguration {

  private final ConfigProperties configProps;

  @Autowired
  public ContextConfiguration(ConfigProperties configProps) {

    this.configProps = configProps;
  }

  @Bean
  public Config hazelcastConfig() {

    Config config = new Config();
    NetworkConfig netConfig = config.getNetworkConfig();

    // Can define the interfaces to connect through. Otherwise will connect through the first one found
    if (this.configProps.getInterfaces() != null) {
      // Specify the network interfaces Hazelcast should use. Very useful for compatibility with Docker Swarm
      netConfig.getInterfaces()
          .setEnabled(true)
          .setInterfaces(this.configProps.getInterfaces());
    }

    // A MembersList being specified, means that we want to use TCP Discovery
    if (this.configProps.getMembersList() != null) {
      // Disable MultiCast discovery, which is enabled by default
      netConfig.getJoin()
          .getMulticastConfig()
          .setEnabled(false);

      // Setup TCP discovery
      netConfig.getJoin()
          .getTcpIpConfig()
          .setEnabled(true)
          .setMembers(this.configProps.getMembersList());
    }

    return config;
  }

}
