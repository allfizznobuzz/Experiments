package com.techelevator;

public abstract class Item {

    private String productName;
    private double price;
    private String type;
    private int stock = 50;

    public Item(String productName, double price, String type) {
        this.productName = productName;
        this.price = price;
        this.type = type;

    }

    public abstract String toString();

    public int getStock() {
        return stock;
    }

    public void removeFromStock(int amountSelected) {
        this.stock = this.stock - amountSelected;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }
}
