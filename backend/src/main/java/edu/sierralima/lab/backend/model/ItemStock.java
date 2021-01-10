package edu.sierralima.lab.backend.model;

public class ItemStock {

  private final Item item;

  private Double amountInStock;

  public ItemStock(Item item, Double amountInStock) {
    this.item = item;
    this.amountInStock = amountInStock;
  }

  public ItemStock(Item item) {
    this(item, 0.0);
  }

  public Item getItem() {
    return item;
  }

  public Double getAmountInStock() {
    return amountInStock;
  }

  public void setAmountInStock(Double amountInStock) {
    this.amountInStock = amountInStock;
  }

  @Override
  public String toString() {
    return "ItemStock{" +
      "item=" + item +
      ", amountInStock=" + amountInStock +
      '}';
  }
}
