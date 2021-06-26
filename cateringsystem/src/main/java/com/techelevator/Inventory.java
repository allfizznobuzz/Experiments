package com.techelevator;

import java.util.Map;

public class Inventory {

    private Map<String, Item> currentInventory;

    public Inventory(Map<String, Item> currentInventory) {
        this.currentInventory = currentInventory;
    }

    public Map<String, Item> getInventory() {
        return currentInventory;
    }

    public void removeStock(String key, int amount) {
        Item item = currentInventory.get(key);
        item.removeFromStock(amount);
    }

    public int getItemStock(String key) {
        return currentInventory.get(key).getStock();
    }

    public Item getItem(String key) {
        return currentInventory.get(key);
    }

    public boolean hasKey(String key) {
        return currentInventory.containsKey(key);
    }
}
