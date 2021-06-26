package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShoppingCartItemTest {

    Dessert dessert = new Dessert("Pie",1.50,"Dessert");
    Beverage beverage = new Beverage("Coke",2.50,"Beverage");
    Entree entree = new Entree("Fries",4.50,"Entree");
    Appetizer appetizer = new Appetizer("Pickles",3.00,"Appetizer");

    ShoppingCartItem shoppingCartItem1 = new ShoppingCartItem(dessert, 10);
    ShoppingCartItem shoppingCartItem2 = new ShoppingCartItem(beverage, 30);
    ShoppingCartItem shoppingCartItem3 = new ShoppingCartItem(entree, 5);
    ShoppingCartItem shoppingCartItem4 = new ShoppingCartItem(appetizer, 0);

    @Test
    public void getItem() {

        Assert.assertEquals(dessert, shoppingCartItem1.getItem());
        Assert.assertEquals(beverage, shoppingCartItem2.getItem());
        Assert.assertEquals(entree, shoppingCartItem3.getItem());
        Assert.assertEquals(appetizer, shoppingCartItem4.getItem());
    }

    @Test
    public void getQuantitySelected() {

        Assert.assertEquals(10, shoppingCartItem1.getQuantitySelected());
        Assert.assertEquals(30, shoppingCartItem2.getQuantitySelected());
        Assert.assertEquals(5, shoppingCartItem3.getQuantitySelected());
        Assert.assertEquals(0, shoppingCartItem4.getQuantitySelected());
    }

    @Test
    public void getCost() {

        Assert.assertEquals(15.00, shoppingCartItem1.getCost(), 0);
        Assert.assertEquals(75, shoppingCartItem2.getCost(), 0);
        Assert.assertEquals(22.50, shoppingCartItem3.getCost(), 0);
        Assert.assertEquals(0, shoppingCartItem4.getCost(), 0);
    }
}