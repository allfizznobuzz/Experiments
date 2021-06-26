public class TicTacToe {

    Tracker tracker;
    UI ui;

    public TicTacToe() {
        ui = new UI();
    }

    public void run() {
        ui.printGameStart();

        while(true) {
            String choice = ui.printGameMenu();

            if(choice.equals("1")) {

                playGame();

            } else if(choice.equals("2")) {

                break;

            } else {

                ui.printError();
            }
        }
        ui.printGoodbye();
    }

    public void playGame() {
        boolean gameOver = false;
        int turnSystem = 0;
        tracker = new Tracker();

        while(!gameOver) {

            ui.gameTemplate(tracker.getListOfRows());

            if(turnSystem % 2 == 0) {
                while(true) {

                    ui.printXTurn();
                    int chosenColumn = ui.askUserForColumn();
                    int chosenRow = ui.askUserForRow();

                    if (((chosenColumn >= 1 && chosenColumn <= 3) && (chosenRow >= 1 && chosenRow <= 3)) && tracker.getListOfRows().get(chosenRow - 1)[chosenColumn - 1].equals(" ")) {
                        tracker.getListOfRows().get(chosenRow - 1)[chosenColumn - 1] = "X";
                        break;
                    } else {
                        ui.printError();
                    }
                }
            } else {
                while(true) {

                    ui.printOTurn();
                    int chosenColumn = ui.askUserForColumn();
                    int chosenRow = ui.askUserForRow();

                    if (((chosenColumn >= 1 && chosenColumn <= 3) && (chosenRow >= 1 && chosenRow <= 3)) && tracker.getListOfRows().get(chosenRow - 1)[chosenColumn - 1].equals(" ")) {
                        tracker.getListOfRows().get(chosenRow - 1)[chosenColumn - 1] = "O";
                        break;
                    } else {
                        ui.printError();
                    }
                }
            }
            if(tracker.checkRowWin()) {
                gameOver = true;
            } else if (tracker.checkColumnWin()) {
                gameOver = true;
            } else gameOver = tracker.checkDiagonalWin();
            turnSystem++;
        }
        ui.gameTemplate(tracker.getListOfRows());
        ui.printWinner(turnSystem);
    }

    public static void main(String[] args) {
        TicTacToe app = new TicTacToe();

        app.run();
    }
}
