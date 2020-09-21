package edu.sierralima.lab.backend.model;

public class TestMessage {

  private String content;

  public TestMessage() {
    this.content = "";
  }

  public TestMessage(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "TestMessage{" +
      "content='" + content + '\'' +
      '}';
  }
}
