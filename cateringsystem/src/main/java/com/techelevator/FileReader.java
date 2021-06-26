package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader {
    public Map<String, Item> loadItems() {

        Scanner scan;

        Map<String, Item> newItemList = new TreeMap<>();

        try {

            //Scans the selected file to create objects and to populate the list
            scan = new Scanner(new File("cateringsystem.csv"));

            while (scan.hasNextLine()) {

                //Stores current line in file and separates it into an array, to further be made into an object
                String holdNextLine = scan.nextLine();
                String[] lineOfItems = holdNextLine.split("\\|");

                Item item;

                //Makes Appetizer object
                if (lineOfItems[3].equals("A")) {
                    item = new Appetizer(lineOfItems[1], Double.parseDouble(lineOfItems[2]), lineOfItems[3]);
                    newItemList.put(lineOfItems[0], item);
                }
                //Makes Beverage object
                else if (lineOfItems[3].equals("B")) {
                    item = new Beverage(lineOfItems[1], Double.parseDouble(lineOfItems[2]), lineOfItems[3]);
                    newItemList.put(lineOfItems[0], item);
                }
                //Makes Entree object
                else if (lineOfItems[3].equals("E")) {
                    item = new Entree(lineOfItems[1], Double.parseDouble(lineOfItems[2]), lineOfItems[3]);
                    newItemList.put(lineOfItems[0], item);
                }
                //Makes Dessert object
                else if (lineOfItems[3].equals("D")) {
                    item = new Dessert(lineOfItems[1], Double.parseDouble(lineOfItems[2]), lineOfItems[3]);
                    newItemList.put(lineOfItems[0], item);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return newItemList;
    }

}
