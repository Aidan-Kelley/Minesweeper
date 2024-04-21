package GUI;

import java.awt.*;
import javax.swing.JFrame;
import Main.Board;

public class GameWindow extends JFrame {

    private Container pane;
    private Board board;
    private final int ROWS;
    private final int COLUMNS;

    public GameWindow(int r, int c) {
        ROWS = r;
        COLUMNS = c;
        initGame();

    }

    private void initGame() {
        board = new Board(ROWS, COLUMNS);
        setTitle("Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(50 * ROWS, 50 * COLUMNS);
        setResizable(false);
        pane = getContentPane();
        for (int i = 0; i < COLUMNS; i++)
            for (int j = 0; j < ROWS; j++) {
                pane.add(board.tiles[i][j]);
            }
        pane.setLayout(new GridLayout(ROWS, COLUMNS));
        setVisible(true);
        board.placeMines();
    }

}
