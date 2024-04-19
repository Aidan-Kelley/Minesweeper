import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.awt.Point;

public class Board {
    public final int ROWS;
    public final int COLUMNS;
    private final int TOTAL_MINES;
    private final double MINE_PERCENTAGE = 0.12;
    private int tilesRevealed = 0;

    private String[][] displayField;
    private int[][] trueField;

    public Board(int r, int c) {
        ROWS = r;
        COLUMNS = c;
        TOTAL_MINES = (int) Math.ceil(r * c * MINE_PERCENTAGE);
        displayField = new String[ROWS][COLUMNS];
        trueField = new int[ROWS][COLUMNS];
        for (String[] row : displayField)
            Arrays.fill(row, " ? ");
    }

    public void firstMove(int[] action) {
        placeBombs(action[1], action[2]);
        breakTile(action[1], action[2]);
    }

    /**
     * Places bombs, avoiding the specified tile
     *
     */
    private void placeBombs(int avoidRow, int avoidCol) {
        List<Point> coords = new ArrayList<>();
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++)
                coords.add(new Point(i, j));
        Collections.shuffle(coords);
        int minesPlaced = 0, i = 0;
        while (minesPlaced < TOTAL_MINES) {
            int r = coords.get(i).y;
            int c = coords.get(i).x;
            if (r != avoidRow || c != avoidCol) {
                trueField[r][c] = 9;
                minesPlaced++;
            }
            i++;
        }

        for (int r = 0; r < trueField.length; r++) {// set up all the numbers
            for (int c = 0; c < trueField[0].length; c++) {
                trueField[r][c] = setNumber(r, c);
            }
        }
    }

    private int setNumber(int row, int col) {
        final int mineNumber = 9;
        if (trueField[row][col] == mineNumber) {// if it's a mine
            return mineNumber;
        }
        int counter = 0;
        for (int r = row - 1; r <= row + 1; r++)
            for (int c = col - 1; c <= col + 1; c++) {
                if (inBounds(r, c))
                    if (trueField[r][c] == mineNumber)
                        counter++;
            }
        return counter;
    }

    public void showField() {
        System.out.print("   ");
        for (int i = 1; i <= displayField[0].length; i++) { // add the column numbers
            System.out.print(" " + i + " ");
        }
        System.out.println();
        for (int r = 0; r < displayField.length; r++) { // add the row numbers
            System.out.print((r + 1) + " ");
            if (r < 9) {
                System.out.print(" ");
            }
            for (int c = 0; c < displayField[0].length; c++) {
                System.out.print(displayField[r][c]);
            }
            System.out.println();
        }
        System.out.println("\nF = Flagged\n");

    }

    /**
     * updated field based on player action
     * 
     * @param act the player's action
     * @return whether or not the game is finished
     */
    public boolean updateFields(int[] act) {
        int action = act[0];// 1: flag, 2: break, 3: unflag
        int colnew = act[1];
        int rownew = act[2];
        // next, set the field val to the truefield val to reveal number
        // if it is 9, its a mine. put X
        if (action == 0) {// if flag
            displayField[rownew][colnew] = " F ";
            return false;
        }
        if (action == 1) {// if break
            if (trueField[rownew][colnew] == 9) {// if it's a mine, you lose
                displayField[rownew][colnew] = " X ";
                return true;
            } else {
                breakTile(rownew, colnew);
            }
        }
        if (action == 2) {// if unflag
            displayField[rownew][colnew] = " ? ";
        }
        return gameWon();
    }

    public boolean isActionValid(int[] action) {
        int choice = action[0];
        int c = action[1];
        int r = action[2];
        if (!inBounds(r, c)) // ensure coord isn't out of boudn
            return false;
        if (choice == 0 && !displayField[r][c].equals(" ? ")) // only flag an unkkown
            return false;
        if (choice == 1 && !displayField[r][c].equals(" ? ")) // ensure tile isn't broken or flagged
            return false;
        if (choice == 2 && !displayField[r][c].equals(" F ")) // only allowed to unflag a flag
            return false;
        return true;
    }

    public boolean gameWon() {
        int totalSafeTiles = ROWS * COLUMNS - TOTAL_MINES;
        return tilesRevealed >= totalSafeTiles;
    }

    private void breakTile(int row, int col) {
        displayField[row][col] = " " + trueField[row][col] + " ";
        // tilesRevealed++;
        if (trueField[row][col] == 0) {
            for (int r = row - 1; r <= row + 1; r++)
                for (int c = col - 1; c <= col + 1; c++) {
                    if (inBounds(r, c))
                        if (displayField[r][c].equals(" ? "))
                            breakTile(r, c);
                }
        }
    }

    public boolean inBounds(int r, int c) {
        return r >= 0 && r < ROWS && c >= 0 && c < COLUMNS;
    }
}
