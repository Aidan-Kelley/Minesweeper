package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.Board;

public class Tile extends JPanel {

    private final Color defaultColor;
    private Color flagColor;
    private Color color;
    private final Board board;
    private final int ROWS, COLUMNS;
    private JLabel label;
    private String text;
    private Listener listener;
    public boolean flagged = false;
    public boolean revealed = false;

    public Tile(int r, int c, Board board) {
        label = new JLabel();
        label.setFont(new Font("Verdana", 1, 20));
        add(label);
        set(" ? ");
        this.board = board;
        ROWS = r;
        COLUMNS = c;
        listener = new Listener();
        addMouseListener(listener);

        if ((r + c) % 2 == 0) {
            defaultColor = new Color(40, 255, 40); // light green
            flagColor = new Color(230, 180, 0); // light orange
        } else {
            defaultColor = new Color(0, 150, 0); // dark green
            flagColor = new Color(180, 140, 80); // dark orange

        }
        color = defaultColor;
        setBackground(color);
    }

    public void turnBlack() {
        setBackground(Color.BLACK);
        setVisible(false);
        setVisible(true);
    }

    public void set(String value) {
        text = value;
        label.setText(text);
    }

    public void disable() {
        if ((ROWS + COLUMNS) % 2 == 0)
            setBackground(new Color(100, 0, 0)); // dark red
        else
            setBackground(new Color(200, 50, 50)); // light red

        removeMouseListener(listener);

    }

    public void toggleFlag() {
        if (flagged) {
            flagged = false;
            set(" ? ");
            color = defaultColor;
            setBackground(defaultColor);
        } else {
            flagged = true;
            set(" F ");
            color = flagColor;
            setBackground(flagColor);
        }
    }

    public Color brighten(Color color, double fraction) {

        int red = (int) Math.round(Math.min(255, color.getRed() + 150 * fraction));
        int green = (int) Math.round(Math.min(255, color.getGreen() + 255 * fraction));
        int blue = (int) Math.round(Math.min(255, color.getBlue() + 150 * fraction));

        return new Color(red, green, blue);

    }

    public String toString() {
        return text;
    }

    private class Listener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            JPanel parent = (JPanel) e.getSource();
            parent.setBackground(color);
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
            if (e.getButton() == MouseEvent.BUTTON1 && !flagged && !revealed) {
                board.breakTile(ROWS, COLUMNS);
            } else if (e.getButton() == MouseEvent.BUTTON3 && !revealed)
                toggleFlag();
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
