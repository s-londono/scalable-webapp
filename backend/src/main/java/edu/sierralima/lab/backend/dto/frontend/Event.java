package edu.sierralima.lab.backend.dto.frontend;

public class Event <T> {

  private Type type;

  private T payload;

  public Event(Type type, T payload) {
    this.type = type;
    this.payload = payload;
  }

  public Event() {
    this(null, null);
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public T getPayload() {
    return payload;
  }

  public void setPayload(T payload) {
    this.payload = payload;
  }

  @Override
  public String toString() {
    return "Event{" +
      "type='" + type + '\'' +
      ", payload=" + payload +
      '}';
  }

  public enum Type {
    STOCK_SNAPSHOT,
    STOCK_UPDATE,
    REQUEST_ALTER_ITEM_STOCK,
  }
}
