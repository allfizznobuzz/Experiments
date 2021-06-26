package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChangeTest {

    Change change = new Change();

    @Test
    public void setDollarsAndCents() {
        change.setDollarsAndCents(123.25);
        Assert.assertEquals(6 ,change.getAmountOf20DollarBills());
        Assert.assertEquals(0 ,change.getAmountOf10DollarBills());
        Assert.assertEquals(0 ,change.getAmountOf5DollarBills());
        Assert.assertEquals(3 ,change.getAmountOf1DollarBills());
        Assert.assertEquals(1 ,change.getAmountOfQuarters());
        Assert.assertEquals(0 ,change.getAmountOfDimes());
        Assert.assertEquals(0 ,change.getAmountOfNickles());

        change.setDollarsAndCents(0);
        Assert.assertEquals(0 ,change.getAmountOf20DollarBills());
        Assert.assertEquals(0 ,change.getAmountOf10DollarBills());
        Assert.assertEquals(0 ,change.getAmountOf5DollarBills());
        Assert.assertEquals(0 ,change.getAmountOf1DollarBills());
        Assert.assertEquals(0 ,change.getAmountOfQuarters());
        Assert.assertEquals(0 ,change.getAmountOfDimes());
        Assert.assertEquals(0 ,change.getAmountOfNickles());

        change.setDollarsAndCents(15.15);
        Assert.assertEquals(0 ,change.getAmountOf20DollarBills());
        Assert.assertEquals(1 ,change.getAmountOf10DollarBills());
        Assert.assertEquals(1 ,change.getAmountOf5DollarBills());
        Assert.assertEquals(0 ,change.getAmountOf1DollarBills());
        Assert.assertEquals(0 ,change.getAmountOfQuarters());
        Assert.assertEquals(1 ,change.getAmountOfDimes());
        Assert.assertEquals(1 ,change.getAmountOfNickles());
    }
}