
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
        int[] action = new int[3];
        String choice; // want to rename the choice variable, it stores the F, B, or U
        int x, y;
        while (true) {
            System.out.print("Enter: ");
            choice = scan.next().toUpperCase();
            if (choice.equals("F")) {// if flag
                action[0] = 1;
            } else if (choice.equals("B")) {// if break
                action[0] = 2;
            } else if (choice.equals("U")) {// if unflag
                action[0] = 3;
            } else {
                System.out.println("Start your action with F, B, or U");
                scan.nextLine();
                continue;
            }
            try {
                x = scan.nextInt();
                y = scan.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid Input");
                scan.nextLine();
                continue;
            }

            if (x < 1 || x > COLUMNS || y < 1 || y > ROWS) {
                System.out.println("Invalid coordinate");
                scan.nextLine();
                continue;
            }
            action[1] = x;
            action[2] = y;
            return action;
        }
    }

    private void end(boolean won) {
        scan.close();
        if (won) {
            Graphics.displayWinMessage();
        } else {
            Graphics.displayLoseMessage();
        }
    }
}