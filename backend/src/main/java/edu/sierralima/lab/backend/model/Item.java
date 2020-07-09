package edu.sierralima.lab.backend.model;

public class Item {

  private final String sku;

  private String model;

  private long numInStock;

  public Item(String sku, String model) {
    this.sku = sku;
    this.model = model;
    this.numInStock = 0;
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

  public long getNumInStock() {
    return numInStock;
  }

  public void setNumInStock(long numInStock) {
    this.numInStock = numInStock;
  }

  @Override
  public String toString() {
    return "Item{" +
        "sku='" + sku + '\'' +
        ", model='" + model + '\'' +
        ", numInStock=" + numInStock +
        '}';
  }

}

