package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CashRegisterTest {

    CashRegister cashRegister = new CashRegister();

    @Test
    public void addMoney() {
        cashRegister.addMoney(25.50);
        Assert.assertEquals(25.50, cashRegister.getBalance(), 0);

        cashRegister.addMoney(0);
        Assert.assertEquals(25.50, cashRegister.getBalance(), 0);

        cashRegister.addMoney(5.50);
        Assert.assertEquals(31.00, cashRegister.getBalance(), 0);
    }

    @Test
    public void getBalance() {
        Assert.assertEquals(0.00, cashRegister.getBalance(), 0);

        cashRegister.addMoney(10.50);
        Assert.assertEquals(10.50, cashRegister.getBalance(), 0);
    }

    @Test
    public void withdraw() {
        cashRegister.addMoney(20.20);
        cashRegister.withdraw(10.20);
        Assert.assertEquals(10.00, cashRegister.getBalance(),0);

        cashRegister.withdraw(10.00);
        Assert.assertEquals(0, cashRegister.getBalance(),0);
    }

    @Test
    public void clearBalance() {
        cashRegister.addMoney(20.00);
        cashRegister.clearBalance();
        Assert.assertEquals(0, cashRegister.getBalance(), 0);

        cashRegister.clearBalance();
        Assert.assertEquals(0, cashRegister.getBalance(), 0);
    }
}