package com.techelevator.rpgtest;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Menu {

    private static final TimeUnit SECONDS = TimeUnit.SECONDS;

    public void testString() {
        int[] numbers = new int[] {21, 32, 432, 54, 321, 3, 432};
        String[] names = new String[] {"Loooooong", "short","hi","Bye","Forward","Backwards","Final"};
        double[] floats = new double[] {432.32, 34.43,321.43, 43.3, 0.32, 321.32, 32.13};


        for (int i = 0; i < 7; i++) {
            System.out.printf("%-10s %15d %20s", names[i], numbers[i], NumberFormat.getCurrencyInstance().format(floats[i]));
            System.out.println();
        }

    }

    public void startOfGame() {

        System.out.println("*------------------------------------------*");
        System.out.printf("  WELCOME TO: RPG TESTER - THE GREAT TEST  \n");
        System.out.println("*------------------------------------------*");
        System.out.println();
        System.out.println("Please select your choice (number only)");
        System.out.println();
        System.out.println("1. Start a new game");
        System.out.println("2. Exit\n");
    }

    public void characterSelection(){
        System.out.println("Please select your hero (number only)");
        System.out.println();
        System.out.println("1. Mage");
        System.out.println("2. Barbarian");
        System.out.println("3. Paladin");
        System.out.println("4. Archer\n");
    }

    public void heroName() {
        System.out.println("Please enter the name of your hero");
    }

    public void printNewHero(String heroName, String heroType) {
        System.out.println("The newest hero to the realm, " + heroName + " the " + heroType + ", has begun their journey.");
    }

    /*
    * List all error messages below
    */
    public void errorMessage() {
        System.err.print("Error: ");
    }

    public void menuError() {
        errorMessage();
        System.err.print("Please enter a valid input\n");
    }

    /*
     * List all story elements below
     */
    public void introStory() {
        System.out.println();
    }

    public void mainMenu() {

    }

}
