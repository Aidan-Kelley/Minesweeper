
import java.util.Scanner;

public class Minesweeper {

    private Board board;

    private final int ROWS = 9;
    private final int COLUMNS = 9;

    private final Scanner scan = new Scanner(System.in);

    public Minesweeper() {
        initGame();
        gameLoop();
        end(board.gameWon());
    }

    private void initGame() {
        board = new Board(9, 9);
        Graphics.displayTitle();
        Graphics.displayRules();
        scan.nextLine();
    }

    // returns true if won
    private void gameLoop() {
        int[] act = new int[3];
        while (true) {
            board.showField();
            act = getUserAction();
            if (board.updateFields(act))
                return;
        }
    }

    private int[] getUserAction() {
        System.out.print("Enter: ");
        String ans = scan.nextLine();
        ans = ans.toUpperCase();
        String x = ans.substring(ans.indexOf(" ") + 1, ans.indexOf(" ", ans.indexOf(" ") + 1));
        String y = ans.substring(1 + ans.indexOf(" ", ans.indexOf(" ") + 1));
        int[] action = new int[3];

        if (ans.substring(0, 1).equals("F")) {// if flag
            action[0] = 1;
        } else if (ans.substring(0, 1).equals("B")) {// if break
            action[0] = 2;
        } else {// assume unflag
            action[0] = 3;
        }

        // check for coordinates
        for (int i = 1; i <= COLUMNS; i++) {// extracts column value
            if (x.equals(i + "")) {
                action[1] = i;
                break;
            }
        }
        for (int i = 1; i <= ROWS; i++) {// extracts row value
            if (y.equals("" + i)) {
                action[2] = i;
                break;
            }
        }
        return action;
    }

    private void end(boolean won) {
        scan.close();
        if (won) {
            Graphics.displayWinMessage();
        } else {
            Graphics.displayLoseMessage();
        }
    }

    private void showTrueField(int[][] field) {
        System.out.print("  ");
        for (int i = 1; i <= field[0].length; i++) { // add the column numbers
            System.out.print(" " + i + " ");
        }
        System.out.println();
        for (int r = 0; r < field.length; r++) { // add the row numbers
            System.out.print((r + 1) + " ");
            if (r < 9) {
                System.out.print(" ");
            }
            for (int c = 0; c < field[0].length; c++) {
                System.out.print(field[r][c] + ", ");
            }
            System.out.println();
        }
        System.out.println("\nF = Flagged");

    }
}