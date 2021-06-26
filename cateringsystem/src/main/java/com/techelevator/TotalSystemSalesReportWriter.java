package com.techelevator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

public class TotalSystemSalesReportWriter {

    public File salesReportFile = new File("TotalSales.rpt");

    public void createSalesReportFile() {
        if (!salesReportFile.exists()) {
            try {
                salesReportFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addSalesReport(List<ShoppingCartItem> shoppingCart) {
        try (
                FileWriter writer = new FileWriter(salesReportFile, true);
                BufferedWriter buffer = new BufferedWriter(writer)) {

            double counter = 0;

            for (ShoppingCartItem item : shoppingCart) {

                String string = String.format("%s|%s|%s", item.getItem().getProductName(), item.getQuantitySelected(), NumberFormat.getCurrencyInstance().format(item.getCost()));

                buffer.write(string);
                buffer.newLine();
                counter = counter + item.getCost();
            }
            buffer.newLine();
            buffer.write("**TOTAL SALES** " + NumberFormat.getCurrencyInstance().format(counter));
            buffer.newLine();
            buffer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
