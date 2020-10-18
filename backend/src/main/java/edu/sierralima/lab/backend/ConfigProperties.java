package edu.sierralima.lab.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "backend")
public class ConfigProperties {

  @Value("${backend.hazelcastclient.bind-address}")
  private String hazelcastBindAddress;

  @Value("${backend.hazelcastclient.outbound-port-def}")
  private String hazelcastOutboundPortDef;

  @Value("${backend.hazelcastclient.connection-timeout:5000}")
  private Integer hazelcastConnectionTimeout;

  public String getHazelcastBindAddress() {
    return hazelcastBindAddress;
  }

  public void setHazelcastBindAddress(String hazelcastBindAddress) {
    this.hazelcastBindAddress = hazelcastBindAddress;
  }

  public String getHazelcastOutboundPortDef() {
    return hazelcastOutboundPortDef;
  }

  public void setHazelcastOutboundPortDef(String hazelcastOutboundPortDef) {
    this.hazelcastOutboundPortDef = hazelcastOutboundPortDef;
  }

  public Integer getHazelcastConnectionTimeout() {
    return hazelcastConnectionTimeout;
  }

  public void setHazelcastConnectionTimeout(Integer hazelcastConnectionTimeout) {
    this.hazelcastConnectionTimeout = hazelcastConnectionTimeout;
  }

}
