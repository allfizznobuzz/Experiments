package com.techelevator;

public class Change {

    private int amountOf20DollarBills;
    private int amountOf10DollarBills;
    private int amountOf5DollarBills;
    private int amountOf1DollarBills;
    private int amountOfQuarters;
    private int amountOfDimes;
    private int amountOfNickles;

    public void setDollarsAndCents(double currentBalance) {

        amountOf20DollarBills = (int) currentBalance / 20;
        currentBalance = currentBalance - (amountOf20DollarBills * 20);

        amountOf10DollarBills = (int) currentBalance / 10;
        currentBalance = currentBalance - (amountOf10DollarBills * 10);

        amountOf5DollarBills = (int) currentBalance / 5;
        currentBalance = currentBalance - (amountOf5DollarBills * 5);

        amountOf1DollarBills = (int) currentBalance;
        currentBalance = currentBalance - (amountOf1DollarBills);

        currentBalance = currentBalance * 100;

        amountOfQuarters = (int) currentBalance / 25;
        currentBalance = currentBalance - (amountOfQuarters * 25);

        amountOfDimes = (int) currentBalance / 10;
        currentBalance = currentBalance - (amountOfDimes * 10);

        amountOfNickles = (int) currentBalance / 5;
        currentBalance = currentBalance - (amountOfNickles * 5);

    }

    public int getAmountOf20DollarBills() {
        return amountOf20DollarBills;
    }

    public int getAmountOf10DollarBills() {
        return amountOf10DollarBills;
    }

    public int getAmountOf5DollarBills() {
        return amountOf5DollarBills;
    }

    public int getAmountOf1DollarBills() {
        return amountOf1DollarBills;
    }

    public int getAmountOfQuarters() {
        return amountOfQuarters;
    }

    public int getAmountOfDimes() {
        return amountOfDimes;
    }

    public int getAmountOfNickles() {
        return amountOfNickles;
    }
}
