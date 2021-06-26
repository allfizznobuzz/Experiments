package com.techelevator;

public class Appetizer extends Item{
    public Appetizer(String productName, double price, String type) {
        super(productName, price, type);
    }

    @Override
    public String toString() {
        return "Appetizer";
    }
}
