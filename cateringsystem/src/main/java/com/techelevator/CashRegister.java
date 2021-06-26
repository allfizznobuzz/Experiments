package com.techelevator;

public class CashRegister {

    private final double STARTING_BALANCE = 0;

    private double balance;

    public CashRegister() {
        balance = STARTING_BALANCE;
    }

    public void addMoney(double amount) {
        balance = balance + amount;
    }

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount) {
        balance = balance - amount;
    }

    public void clearBalance() {
        balance = STARTING_BALANCE;
    }
}
