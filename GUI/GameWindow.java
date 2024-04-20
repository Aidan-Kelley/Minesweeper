package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame {

    private int TILE_WIDTH = 40;
    private int TILE_HEIGHT = 40;
    private Tile[][] tiles;
    private Container pane;

    public GameWindow(int rows, int cols) {
        setTitle("Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(50 * rows, 50 * cols);
        setResizable(false);
        pane = getContentPane();
        tiles = new Tile[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) {
                tiles[i][j] = new Tile(i, j);
                pane.add(tiles[i][j]);
            }
        pane.setLayout(new GridLayout(rows, cols));

        addMouseListener(new WindowListener());
        setVisible(true);
    }

    private class WindowListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            TILE_WIDTH = tiles[0][0].getWidth();
            TILE_HEIGHT = tiles[0][0].getHeight();

            System.out.println((int) pane.getMousePosition().getY());
            int rowSelected = ((int) pane.getMousePosition().getY()) / TILE_HEIGHT;
            int columnSelected = ((int) pane.getMousePosition().getX()) / TILE_WIDTH;
            System.out.println("Row: " + rowSelected + " Col: " + columnSelected);
            if (rowSelected >= 0 && rowSelected < tiles.length && columnSelected >= 0
                    && columnSelected < tiles[0].length)
                tiles[rowSelected][columnSelected].turnBlack();

        }
    }
}
