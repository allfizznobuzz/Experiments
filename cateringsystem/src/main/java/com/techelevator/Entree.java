package com.techelevator;

public class Entree extends Item {
    public Entree(String productName, double price, String type) {
        super(productName, price, type);
    }

    @Override
    public String toString() {
        return "Entree";
    }
}
