
import java.util.Scanner;

public class Minesweeper {

    private Board board;

    private final Scanner scan = new Scanner(System.in);

    public Minesweeper() {
        initGame();
        gameLoop();
        end();
    }

    private void initGame() {
        board = new Board(6, 10);
        Graphics.displayTitle();
        Graphics.displayRules();
        scan.nextLine();
        board.showField();
        board.firstMove(getUserAction());
    }

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
                action[0] = 0;
            } else if (choice.equals("B")) {// if break
                action[0] = 1;
            } else if (choice.equals("U")) {// if unflag
                action[0] = 2;
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
            action[1] = x - 1;
            action[2] = y - 1;
            if (!board.isActionValid(action)) {
                System.out.println("Cannot complete action");
                scan.nextLine();
                continue;
            }
            return action;
        }
    }

    private void end() {
        scan.close();
        if (board.gameWon()) {
            Graphics.displayWinMessage();
        } else {
            Graphics.displayLoseMessage();
        }
    }
}