package com.techelevator;


public class ShoppingCartItem {

    private Item item;
    private int quantitySelected;
    private double cost;


    public ShoppingCartItem(Item item, int quantitySelected) {
        this.item = item;
        this.quantitySelected = quantitySelected;
        this.cost = quantitySelected * item.getPrice();
    }


    public Item getItem() {
        return item;
    }

    public void setQuantitySelected(int quantitySelected) {
        this.quantitySelected = quantitySelected;
    }

    public int getQuantitySelected() {
        return quantitySelected;
    }

    public double getCost() {
        return cost;
    }
}
