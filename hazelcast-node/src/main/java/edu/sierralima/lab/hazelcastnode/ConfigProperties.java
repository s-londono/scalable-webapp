package edu.sierralima.lab.hazelcastnode;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "hazelcastnode")
public class ConfigProperties {

  private List<String> interfaces;

  private List<String> membersList;

  public List<String> getInterfaces() {
    return interfaces;
  }

  public void setInterfaces(List<String> interfaces) {
    this.interfaces = interfaces;
  }

  public List<String> getMembersList() {
    return membersList;
  }

  public void setMembersList(List<String> membersList) {
    this.membersList = membersList;
  }

}
