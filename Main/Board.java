package Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import GUI.Tile;

import java.awt.Point;

public class Board {
    public final int ROWS;
    public final int COLUMNS;
    private final int TOTAL_MINES;
    private final double MINE_PERCENTAGE = 0.3;
    private int tilesRevealed = 0;
    private boolean firstMove = true;

    public Tile[][] tiles;
    private int[][] trueField;

    public Board(int r, int c) {
        trueField = new int[r][c];
        tiles = new Tile[r][c];
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++) {
                tiles[i][j] = new Tile(i, j, this);
            }
        ROWS = r;
        COLUMNS = c;
        TOTAL_MINES = (int) Math.ceil(r * c * MINE_PERCENTAGE);
    }

    private void placeMines(int avoidRow, int avoidCol) {
        List<Point> coords = new ArrayList<>();
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++)
                coords.add(new Point(j, i));
        Collections.shuffle(coords);
        int minesPlaced = 0, i = 0;
        while (minesPlaced < TOTAL_MINES) {
            int r = coords.get(i).y;
            int c = coords.get(i).x;
            if (r < avoidRow - 1 || r > avoidRow + 1 || c < avoidCol - 1 || c > avoidCol + 1) {
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

    public boolean gameWon() {
        int totalSafeTiles = ROWS * COLUMNS - TOTAL_MINES;
        return tilesRevealed >= totalSafeTiles;
    }

    public boolean breakTile(int row, int col) {
        if (firstMove) {
            // replaceMine(row, col);
            placeMines(row, col);
            revealTiles(row, col);
            firstMove = false;
        }
        if (trueField[row][col] == 9) { // if it's a mine
            tiles[row][col].set(" X ");
            endGame();
        } else
            revealTiles(row, col);
        return false;
    }

    private void revealTiles(int row, int col) {
        tiles[row][col].set(trueField[row][col] + "");
        tiles[row][col].revealed = true;
        tilesRevealed++;
        if (trueField[row][col] == 0) {
            for (int r = row - 1; r <= row + 1; r++)
                for (int c = col - 1; c <= col + 1; c++) {
                    if (inBounds(r, c))
                        if (tiles[r][c].toString().equals(" ? "))
                            revealTiles(r, c);
                }
        }
    }

    public boolean inBounds(int r, int c) {
        return r >= 0 && r < ROWS && c >= 0 && c < COLUMNS;
    }

    private void endGame() {
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLUMNS; c++) {
                tiles[r][c].set(":(");
                tiles[r][c].disable();
            }
    }
}
