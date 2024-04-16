import java.util.Arrays;

public class Board {
    public final int ROWS;
    public final int COLUMNS;
    private final int MINE_AMOUNT = 10;
    private int tilesRevealed = 0;

    private String[][] displayField;
    private int[][] trueField;

    public Board(int r, int c) {
        ROWS = r;
        COLUMNS = c;
        displayField = new String[ROWS][COLUMNS];
        trueField = new int[ROWS][COLUMNS];
        setupFields();
    }

    private void setupFields() {
        for (int r = 0; r < ROWS; r++) {
            Arrays.fill(displayField[r], " ? ");
        }

        // reveal middle 9 spots (3x3), then generate the mines
        // while MINE_AMOUTN != 0, go through each element to test mine
        // once MINE_AMOUTN == 0, calculate numbers
        for (int r = (ROWS / 2) - 1; r < (ROWS / 2) + 2; r++) {
            for (int c = (COLUMNS / 2) - 1; c < (COLUMNS / 2) + 2; c++) {
                trueField[r][c] = 11;// 11 will be skipped by the mine placing
                tilesRevealed++;
            }
        }
        int minesPlaced = 0;
        while (minesPlaced < MINE_AMOUNT) {
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLUMNS; c++) {
                    if (trueField[r][c] != 11 && Math.random() * 100 <= 5) { // if its not the middle 9, and hits the 5%
                                                                             // chance, places the mine
                        trueField[r][c] = 9; // 10 is a mine
                        minesPlaced++;
                        break;
                    }
                }
            }
        }

        for (int r = 0; r < trueField.length; r++) {// set up all the numbers
            for (int c = 0; c < trueField[0].length; c++) {
                trueField[r][c] = setNumber(r, c);
            }
        }
        for (int r = (ROWS / 2) - 1; r < (ROWS / 2) + 2; r++) {// set the middle
            for (int c = (COLUMNS / 2) - 1; c < (COLUMNS / 2) + 2; c++) {
                displayField[r][c] = " " + trueField[r][c] + " ";
            }
        }
    }

    private int setNumber(int row, int col) {
        int counter = 0;
        int mineNumber = 9;
        // for same row
        if (trueField[row][col] == 9) {// if it's a mine
            return 9;
        }
        if (col > 0) {// not touching the left wall
            if (trueField[row][col - 1] == mineNumber) {// mine at top left
                counter++;
            }
        }
        if (col < trueField.length - 1) {// not touching the right wall
            if (trueField[row][col + 1] == mineNumber) {// mine at top right
                counter++;
            }
        }

        // for top row
        if (row > 0) {// if row is not touching the ceiling, check for top 3 elements
            // let int[row][col] be the element we're modifying
            if (trueField[row - 1][col] == mineNumber) {// mine at top
                counter++;
            }
            if (col > 0) {// not touching the left wall
                if (trueField[row - 1][col - 1] == mineNumber) {// mine at top left
                    counter++;
                }
            }
            if (col < trueField[0].length - 1) {// not touching the right wall
                if (trueField[row - 1][col + 1] == mineNumber) {// mine at top right
                    counter++;
                }
            }
        }
        if (row < trueField.length - 1) {// not touching the bottom
            if (trueField[row + 1][col] == mineNumber) {// mine at bottom
                counter++;
            }
            if (col > 0) {// not touching the left wall
                if (trueField[row + 1][col - 1] == mineNumber) {// mine at top left
                    counter++;
                }
            }
            if (col < trueField[0].length - 1) {// not touching the right wall
                if (trueField[row + 1][col + 1] == mineNumber) {// mine at top right
                    counter++;
                }
            }

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
                displayField[rownew][colnew] = " " + trueField[rownew][colnew] + " ";
                tilesRevealed++;
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
        if (r < 0 || r > ROWS || c < 0 || c > COLUMNS) // ensure coord isn't out of boudn
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
        int totalSafeTiles = ROWS * COLUMNS - MINE_AMOUNT;
        return tilesRevealed >= totalSafeTiles;
    }
}
