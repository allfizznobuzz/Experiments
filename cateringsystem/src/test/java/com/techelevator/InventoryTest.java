package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class InventoryTest {

    Dessert dessert = new Dessert("Pie",1.50,"Dessert");
    Beverage beverage = new Beverage("Coke",2.50,"Beverage");
    Entree entree = new Entree("Fries",4.50,"Entree");
    Appetizer appetizer = new Appetizer("Pickles",3.00,"Appetizer");

    Map<String, Item> testMap = new TreeMap<String, Item>();

    Inventory inventory = new Inventory(testMap);

    @Test
    public void getInventory() {

        Assert.assertEquals(testMap, inventory.getInventory());

        testMap.put("A1", appetizer);
        Assert.assertEquals(testMap, inventory.getInventory());

        testMap.put("D1", dessert);
        Assert.assertEquals(testMap, inventory.getInventory());

        Map<String, Item> emptyMap = new TreeMap<String, Item>();
        Assert.assertNotEquals(emptyMap, inventory.getInventory());
    }

    @Test
    public void removeStock() {
        testMap.put("A1", appetizer);
        testMap.put("D1", dessert);
        testMap.put("E1", entree);

        inventory.removeStock("D1", 20);
        Assert.assertEquals(30, dessert.getStock());

        inventory.removeStock("A1", 50);
        Assert.assertEquals(0, appetizer.getStock());

        inventory.removeStock("E1", 0);
        Assert.assertEquals(50, entree.getStock());
    }

    @Test
    public void getItemStock() {
        testMap.put("A1", appetizer);
        testMap.put("D1", dessert);
        testMap.put("E1", entree);

        inventory.removeStock("D1", 30);
        Assert.assertEquals(20, inventory.getItemStock("D1"));

        inventory.removeStock("A1", 50);
        Assert.assertEquals(0, inventory.getItemStock("A1"));

        Assert.assertEquals(50, inventory.getItemStock("E1"));
    }

    @Test
    public void getItem() {
        testMap.put("A1", appetizer);
        testMap.put("D1", dessert);
        testMap.put("E1", entree);

        Assert.assertEquals(appetizer, inventory.getItem("A1"));

        Assert.assertEquals(dessert, inventory.getItem("D1"));

        Assert.assertEquals(entree, inventory.getItem("E1"));
    }

    @Test
    public void hasKey() {
        testMap.put("A1", appetizer);
        testMap.put("D1", dessert);
        testMap.put("E1", entree);

        Assert.assertEquals(true, inventory.hasKey("A1"));

        Assert.assertEquals(true, inventory.hasKey("D1"));

        Assert.assertEquals(true, inventory.hasKey("E1"));

        Assert.assertEquals(false, inventory.hasKey("B1"));
    }
}