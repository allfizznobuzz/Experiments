import java.util.ArrayList;
import java.util.List;

public class Tracker {

    private String[] topRow;
    private String[] middleRow;
    private String[] bottomRow;

    public Tracker() {
        topRow = new String[]{" ", " " , " "};
        middleRow = new String[]{" ", " " , " "};
        bottomRow = new String[]{" ", " " , " "};
    }

    public List<String[]> getListOfRows() {
        List<String[]> listOfRows = new ArrayList<>();
        listOfRows.add(topRow);
        listOfRows.add(middleRow);
        listOfRows.add(bottomRow);
        return listOfRows;
    }

    public boolean checkRowWin() {

        if(topRow[0].equals("O") && topRow[1].equals("O") && topRow[2].equals("O") || topRow[0].equals("X") && topRow[1].equals("X") && topRow[2].equals("X")) {
            return true;
        } else if (middleRow[0].equals("O") && middleRow[1].equals("O") && middleRow[2].equals("O") || middleRow[0].equals("X") && middleRow[1].equals("X") && middleRow[2].equals("X")) {
            return true;
        } else return bottomRow[0].equals("O") && bottomRow[1].equals("O") && bottomRow[2].equals("O") || (bottomRow[0].equals("X") && bottomRow[1].equals("X") && bottomRow[2].equals("X"));
    }

    public boolean checkColumnWin() {

        if(topRow[0].equals("O") && middleRow[0].equals("O") && bottomRow[0].equals("O") || topRow[0].equals("X") && middleRow[0].equals("X") && bottomRow[0].equals("X")) {
            return true;
        } else if (topRow[1].equals("O") && middleRow[1].equals("O") && bottomRow[1].equals("O") || topRow[1].equals("X") && middleRow[1].equals("X") && bottomRow[1].equals("X")) {
            return true;
        } else return topRow[2].equals("O") && middleRow[2].equals("O") && bottomRow[2].equals("O") || topRow[2].equals("X") && middleRow[2].equals("X") && bottomRow[2].equals("X");
    }

    public boolean checkDiagonalWin() {

        if(topRow[0].equals("O") && middleRow[1].equals("O") && bottomRow[2].equals("O") || topRow[0].equals("X") && middleRow[1].equals("X") && bottomRow[2].equals("X")) {
            return true;
        } else return topRow[2].equals("O") && middleRow[1].equals("O") && bottomRow[0].equals("O") || topRow[2].equals("X") && middleRow[1].equals("X") && bottomRow[0].equals("X");
    }
}
