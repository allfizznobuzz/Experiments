package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ShoppingCartTest {

    ShoppingCart shoppingCart = new ShoppingCart();
    ShoppingCartItem shoppingCartItem1 = new ShoppingCartItem(new Dessert("Pie",1.50,"Dessert"), 10);
    ShoppingCartItem shoppingCartItem2 = new ShoppingCartItem(new Beverage("Coke",2.50,"Beverage"), 30);
    ShoppingCartItem shoppingCartItem3 = new ShoppingCartItem(new Entree("Fries",4.50,"Entree"), 5);
    ShoppingCartItem shoppingCartItem4 = new ShoppingCartItem(new Appetizer("Pickles",3.00,"Appetizer"), 6);

    @Test
    public void addShoppingCartItem() {
        List<ShoppingCartItem> shoppingCarts = shoppingCart.getList();

        Assert.assertEquals(true, shoppingCarts.isEmpty());

        shoppingCart.addShoppingCartItem(shoppingCartItem1);
        Assert.assertEquals(true, shoppingCarts.contains(shoppingCartItem1));

        Assert.assertEquals(false, shoppingCarts.contains(shoppingCartItem2));
    }

    @Test
    public void getList() {
        List<ShoppingCartItem> testCart = new ArrayList<>();

        Assert.assertEquals(testCart, shoppingCart.getList());

        testCart.add(shoppingCartItem3);
        shoppingCart.addShoppingCartItem(shoppingCartItem3);
        Assert.assertEquals(testCart, shoppingCart.getList());
    }

    @Test
    public void emptyList() {
        List<ShoppingCartItem> testCart = new ArrayList<>();

        shoppingCart.emptyList();
        Assert.assertEquals(testCart, shoppingCart.getList());

        shoppingCart.addShoppingCartItem(shoppingCartItem1);
        shoppingCart.addShoppingCartItem(shoppingCartItem2);
        shoppingCart.addShoppingCartItem(shoppingCartItem3);
        shoppingCart.addShoppingCartItem(shoppingCartItem4);

        shoppingCart.emptyList();
        Assert.assertEquals(testCart, shoppingCart.getList());
    }
}