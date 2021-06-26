package com.techelevator;

import com.techelevator.view.UserInterface;

public class CateringSystemCLI {

    private TotalSystemSalesReportWriter totalSystemSalesReportWriter = new TotalSystemSalesReportWriter();
    private CashRegister cashRegister = new CashRegister();
    private ShoppingCart shoppingCart = new ShoppingCart();
    private ReportWriter report = new ReportWriter();
    private FileReader newFile = new FileReader();
    private Change change = new Change();
    private Inventory inventory;
    private UserInterface ui;

    public CateringSystemCLI(UserInterface ui) {
        this.ui = ui;
    }

    public static void main(String[] args) {
        UserInterface menu = new UserInterface();
        CateringSystemCLI cli = new CateringSystemCLI(menu);
        cli.run();
    }

    //Runs program
    public void run() {

        //Loads inventory from file
        inventory = new Inventory(newFile.loadItems());

        //Checks to see if a TotalSales.rpt exists. If not, it creates a TotalSales.rpt
        totalSystemSalesReportWriter.createSalesReportFile();

        //Checks to see if Log.txt exists. If not, creates new Log.txt
        report.createFile();

        while (true) {

            //Prints main menu
            String userInput = ui.printMenu();

            if (userInput.equals("1")) {

                //Prints catering options
                ui.printCateringItems(inventory.getInventory());
            } else if (userInput.equals("2")) {

                //Enters submenu
                subMenu();
            } else if (userInput.equals("3")) {

                //Breaks loop, exits program
                break;
            }
        }
    }

    private void subMenu() {

        while (true) {

            //Prints submenu and passes current balance to print. Returns user desired menu option
            String userInput = ui.printSubMenu(cashRegister.getBalance());

            if (userInput.equals("1")) {

                //Adds to user balance
                subMenuOptionOne();
            } else if (userInput.equals("2")) {

                //Brings customer to option select
                subMenuOptionTwo();
            } else if (userInput.equals("3")) {

                //Prints receipt, change, clears shopping cart/balance, and breaks loop, returning user to main menu
                subMenuOptionThree();
                break;
            }
        }
    }

    private void subMenuOptionOne() {
        //Asks user for desired amount of money to add to balance
        double balanceIncrease = ui.printAddMoney();

        //Checks to see if desired amount is less than 5000 and that the desired amount plus the current balance is not over 5000
        if ((balanceIncrease <= 5000 && cashRegister.getBalance() + balanceIncrease <= 5000) && balanceIncrease >= 0) {

            //If true, balance is updated, and adds log to Log.txt
            cashRegister.addMoney(balanceIncrease);
            report.addMoneyLog(balanceIncrease, cashRegister.getBalance());
        } else {

            //informs user funds are over 5000
            ui.printTooMuchFunds();
        }
    }

    private void subMenuOptionTwo() {
        //Prints catering menu
        ui.printCateringItems(inventory.getInventory());

        //Obtain key and amount of the item the user desires
        String key = ui.getItemKey();
        int amount = ui.getItemAmount();

        //Informs user product code does not exist
        if (!inventory.hasKey(key)) {
            ui.printProductDoesNotExist();
        }

        //Informs user that item is sold out
        else if (inventory.getItemStock(key) == 0) {
            ui.printSoldOut();
        }

        //Informs user that they are requesting too much stock
        else if (amount > inventory.getItemStock(key)) {
            ui.printInsufficientStock();
        }
        else {
            //Creates ShoppingCartItem object from desired item and quantity desired
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(inventory.getItem(key), amount);

            //Informs user they do not have enough funds
            if (cashRegister.getBalance() < shoppingCartItem.getCost()) {
                ui.printNotSufficientFunds();
            } else {
                //Adds ShoppingCartItem into the ShoppingCartList, updates item stock, updates user balance, and adds log to Log.txt and TotalSales.rpt
                shoppingCart.addShoppingCartItem(shoppingCartItem);
                inventory.removeStock(key, amount);
                cashRegister.withdraw(shoppingCartItem.getCost());
                report.addItemLog(shoppingCartItem, cashRegister.getBalance(), key);
            }
        }
    }

    private void subMenuOptionThree() {

        //Prints total sales report
        totalSystemSalesReportWriter.addSalesReport(shoppingCart.getList());

        //Sets the Change object value to the remaining balance
        change.setDollarsAndCents(cashRegister.getBalance());

        //Prints receipt by listing the ShoppingCart
        ui.printReceipt(shoppingCart.getList());

        //Prints Change in proper format
        ui.printChange(change);

        //Add log to log.txt
        report.addChangeLog(cashRegister.getBalance());

        //Clears balance and ShoppingCart
        cashRegister.clearBalance();
        shoppingCart.emptyList();
    }
}
