import java.util.List;
import java.util.Scanner;

public class UI {

    Scanner scanner;

    public UI () {
        scanner = new Scanner(System.in);
    }

    public void printGameStart() {
        System.out.print(" _____  _                 _____                       _____                \n");
        System.out.print("/__   \\(_)  ___          /__   \\ __ _   ___          /__   \\ ___    ___ \n");
        System.out.print("  / /\\/| | / __|  _____    / /\\// _` | / __|  _____    / /\\// _ \\  / _ \\\n");
        System.out.print(" / /   | || (__  |_____|  / /  | (_| || (__  |_____|  / /  | (_) ||  __/\n");
        System.out.print(" \\/    |_| \\___|          \\/    \\__,_| \\___|          \\/    \\___/  \\___|\n");
        System.out.println();
    }

    public String printGameMenu() {
        System.out.println("Please enter your choice");
        System.out.println("1) New game");
        System.out.println("2) Quit");

        return  scanner.nextLine();
    }

    public void printError() {
        System.out.print("\n**************\n");
        System.out.print("Invalid Option\n");
        System.out.println("**************\n");
    }

    public void gameTemplate(List<String[]> rows) {
        System.out.println("\n  Columns");
        System.out.print(" 1   2   3" +
                        "\n " + rows.get(2)[0] + " | " + rows.get(2)[1] + " | " + rows.get(2)[2] + "  3\n" +
                        "___|___|___\n" +
                        " " + rows.get(1)[0] + " | " + rows.get(1)[1] + " | " + rows.get(1)[2] + "  2 Rows\n" +
                        "___|___|___\n" +
                        " " + rows.get(0)[0] + " | " + rows.get(0)[1] + " | " + rows.get(0)[2] + "  1\n" +
                        "   |   |   \n");
    }

    public int askUserForRow() {
        System.out.println("Please enter desired row");
        return Integer.parseInt(scanner.nextLine());
    }

    public int askUserForColumn() {
        System.out.println("Please enter desired column");
        return Integer.parseInt(scanner.nextLine());
    }

    public void printXTurn() {
        System.out.println("\nWho's turn: X");
    }

    public void printOTurn() {
        System.out.println("\nWho's turn: O");
    }

    public void printWinner(int winningTeam) {
        if(winningTeam % 2 == 1) {
            System.out.println("Congratulations, team X has won the game!\n");
        } else {
            System.out.println("Congratulations, team O has won the game!\n");
        }
    }

    public void printGoodbye() {
        System.out.println("\nHope you enjoyed your game and thank you for playing:");
        System.out.print(" _____  _                 _____                       _____                \n");
        System.out.print("/__   \\(_)  ___          /__   \\ __ _   ___          /__   \\ ___    ___ \n");
        System.out.print("  / /\\/| | / __|  _____    / /\\// _` | / __|  _____    / /\\// _ \\  / _ \\\n");
        System.out.print(" / /   | || (__  |_____|  / /  | (_| || (__  |_____|  / /  | (_) ||  __/\n");
        System.out.print(" \\/    |_| \\___|          \\/    \\__,_| \\___|          \\/    \\___/  \\___|\n");
    }
}
