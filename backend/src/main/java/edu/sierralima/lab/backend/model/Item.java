package edu.sierralima.lab.backend.model;

public class Item {

  private final String sku;

  private String model;

  public Item(String sku, String model) {
    this.sku = sku;
    this.model = model;
  }

  public String getSku() {
    return sku;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  @Override
  public String toString() {
    return "Item{" +
        "sku='" + sku + '\'' +
        ", model='" + model + '\'' +
        '}';
  }

}

