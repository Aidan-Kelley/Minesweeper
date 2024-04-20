package GUI;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

import Main.Board;

public class Tile extends JPanel {

    private Color color;
    private final Board board;
    private final int ROW, COLUMN;

    public Tile(int r, int c, Board board) {
        this.board = board;
        ROW = r;
        COLUMN = c;
        addMouseListener(new Listener());
        if ((r + c) % 2 == 0)
            color = new Color(0, 150, 0);
        else
            color = new Color(80, 200, 0);
        setBackground(color);
    }

    public void turnBlack() {
        setBackground(Color.BLACK);
        setVisible(false);
        setVisible(true);
    }

    private class Listener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            JPanel parent = (JPanel) e.getSource();
            parent.setBackground(new Color(120, 255, 120));
            parent.revalidate();

        }

        @Override
        public void mouseExited(MouseEvent e) {
            JPanel parent = (JPanel) e.getSource();
            parent.setBackground(color);
            parent.revalidate();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            board.breakTile(ROW, COLUMN);
        }
    }
}
