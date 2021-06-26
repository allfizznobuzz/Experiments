package com.techelevator;

import java.io.*;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class ReportWriter {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a ");
    LocalDateTime now = LocalDateTime.now();

    public File logFile = new File("Log.txt");

    public void createFile() {
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addMoneyLog(double moneyAdded, double balance) {
        try (
                FileWriter writer = new FileWriter(logFile, true);
                BufferedWriter buffer = new BufferedWriter(writer)) {

            buffer.write(formatter.format(now) + "ADD MONEY: " + NumberFormat.getCurrencyInstance().format(moneyAdded) + " " + NumberFormat.getCurrencyInstance().format(balance));
            buffer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addItemLog(ShoppingCartItem cost, double balance, String key) {
        try (
                FileWriter writer = new FileWriter(logFile, true);
                BufferedWriter buffer = new BufferedWriter(writer)) {


            buffer.write(formatter.format(now) + cost.getQuantitySelected() + " " + cost.getItem().getProductName() + " " + key + " " + NumberFormat.getCurrencyInstance().format(cost.getCost()) + " " + NumberFormat.getCurrencyInstance().format(balance));
            buffer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addChangeLog(double balance) {
        try (
                FileWriter writer = new FileWriter(logFile, true);
                BufferedWriter buffer = new BufferedWriter(writer)) {


            buffer.write(formatter.format(now) + "GIVE CHANGE: " + NumberFormat.getCurrencyInstance().format(balance) + " $0.00");
            buffer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
