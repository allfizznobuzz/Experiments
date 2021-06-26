package com.techelevator;

public class Dessert extends Item {
    public Dessert(String productName, double price, String type) {
        super(productName, price, type);
    }

    @Override
    public String toString() {
        return "Dessert";
    }

}
