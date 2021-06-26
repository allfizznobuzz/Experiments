package com.techelevator.view;

import com.techelevator.*;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class UserInterface {

    Scanner scan = new Scanner(System.in);

    public String printMenu() {

        //Prints main menu
        System.out.print("(1) Display Catering Items\n(2) Order\n(3) Quit\n");
        System.out.println("*-------------------------*");

        //Returns user input
        return scan.nextLine();
    }

    //Prints submenu and returns user input for desired option
    public String printSubMenu(double balance) {

        //Prints submenu
        System.out.println("*--------------------------------*");
        System.out.print("(1) Add Money\n(2) Select Products\n(3) Complete Transaction\nCurrent Account Balance: " + NumberFormat.getCurrencyInstance().format(balance) + "\n");
        System.out.println("*--------------------------------*");

        //Returns user input
        return scan.nextLine();
    }

    //Prints list of current catering items FIX TYPE OF ITEM PASSED
    public void printCateringItems(Map<String, Item> items) {

        //ItemType[] itemType = new ItemType[] {new Item()};
        Set<String> key = items.keySet();
        System.out.println("*-----------------------------------------------*");

        //Loads map and prints each item and quantity of item remaining
        for (String string : key) {

            Item item = items.get(string);

            if (item.getStock() == 0) {
                System.out.printf("%-10s %-5s %-25s %-5s\n", "SOLD OUT", string, item.getProductName(), NumberFormat.getCurrencyInstance().format(item.getPrice()));
            } else {
                System.out.printf("%-10d %-5s %-25s %-5s\n", item.getStock(), string, item.getProductName(), NumberFormat.getCurrencyInstance().format(item.getPrice()));
            }
        }
        System.out.println("*-----------------------------------------------*");
    }

    //Prints statement requesting whole dollar amount from user
    public double printAddMoney() {

        System.out.print("Give whole dollar amount to add: ");
        //Parses nextLine to double and returns value
        return Double.parseDouble(scan.nextLine());
    }

    //Prints list of current catering items and returns user selection
    public String getItemKey() {

        System.out.println("Please enter product code of item");

        //returns user input
        return scan.nextLine().toUpperCase();
    }

    //Prints list of current catering items and returns user selection
    public int getItemAmount() {

        System.out.println("Please enter amount");

        //returns user input
        return Integer.parseInt(scan.nextLine());
    }

    //Informs user that item is out of stock
    public void printSoldOut() {
        System.out.println("*--------------------------------*");
        System.out.println("Item is out of stock");
    }

    //Informs user that there is insufficient stock for their request
    public void printInsufficientStock() {
        System.out.println("*--------------------------------*");
        System.out.println("Insufficient stock");
    }

    //Informs user that item does not exist
    public void printProductDoesNotExist() {
        System.out.println("*--------------------------------*");
        System.out.println("Product does not exist");
    }

    //Prints change of user
    public void printChange(Change change) {
        System.out.println("Your change is:\n" + change.getAmountOf20DollarBills() + " - 20 dollar bills, ");
        System.out.println(change.getAmountOf10DollarBills() + " - 10 dollar bills, ");
        System.out.println(change.getAmountOf5DollarBills() + " - 5 dollar bills, ");
        System.out.println(change.getAmountOf1DollarBills() + " - 1 dollar bills, ");
        System.out.println(change.getAmountOfQuarters() + " - quarters, ");
        System.out.println(change.getAmountOfDimes() + " - dimes, and ");
        System.out.println(change.getAmountOfNickles() + " - nickles");
        System.out.println("*-------------------------*");
    }

    //Prints user receipt
    public void printReceipt(List<ShoppingCartItem> shoppingCart) {
        double counter = 0;

        System.out.println("*--------------------------------------------------------*");
        for (ShoppingCartItem item : shoppingCart) {
            System.out.printf("%-5s %-10s %-25s %-7s %-5s\n", item.getQuantitySelected(), item.getItem().toString(), item.getItem().getProductName(), NumberFormat.getCurrencyInstance().format(item.getItem().getPrice()), NumberFormat.getCurrencyInstance().format(item.getCost()));
            counter = counter + item.getCost();
        }
        System.out.println("\nTotal: " + NumberFormat.getCurrencyInstance().format(counter));
        System.out.println("*--------------------------------------------------------*");
    }

    //prints not enough funds available for the amount of items selected
    public void printNotSufficientFunds() {
        System.out.println("*--------------------------------*");
        System.out.println("Not enough funds available");
    }

    //Prints that funds exceed 5000 threshold
    public void printTooMuchFunds() {
        System.out.println("*--------------------------------*");
        System.out.println("Fund amount entered exceeds $5000.00 threshold");
    }
}
