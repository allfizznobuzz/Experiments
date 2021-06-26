package com.techelevator;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<ShoppingCartItem> listOfItems = new ArrayList<>();

    public void addShoppingCartItem(ShoppingCartItem item) {
            listOfItems.add(item);
    }

    public List<ShoppingCartItem> getList() {
        return listOfItems;
    }

    public void emptyList() {
        listOfItems.removeAll(listOfItems);
    }

}
