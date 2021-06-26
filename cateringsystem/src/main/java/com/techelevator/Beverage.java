package com.techelevator;

public class Beverage extends Item{
    public Beverage(String productName, double price, String type) {
        super(productName, price, type);
    }
    @Override
    public String toString() {
        return "Beverage";
    }
}
