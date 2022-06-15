package ru.hse.jigsaw;

/**
 * Class of main field of play.
 */
public class Field {
    /**
     * Num of cells.
     */
    public static final int N_CELLS = 9;

    /**
     * State of cells.
     * 0 - free.
     * 1 - busy.
     */
    protected int[][] cells;
    /**
     * Count of moves.
     */
    protected int moves;

    /**
     * Constructor of Field class.
     */
    public Field() {
        cells = new int[N_CELLS][N_CELLS];
        for (int i = 0; i < N_CELLS; i++) {
            for (int j = 0; j < N_CELLS; j++) {
                cells[i][j] = 0;
            }
        }
        moves = 0;
    }

    /**
     * @param figure - figure.
     * @param x      offset of the "root" block of the figure.
     * @param y      offset of the "root" block of the figure.
     * @return
     */
    public boolean isFit(Figure figure, int x, int y) {
        for (int i = 0; i < figure.getNBlocks(); i++) {
            int xx = figure.getBlock(i).x() + x;
            int yy = figure.getBlock(i).y() + y;
            if (xx < 0 || xx >= N_CELLS || yy < 0 || yy >= N_CELLS) {
                return false;
            }
            if (cells[xx][yy] != 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * Method for making move of figure with coordinates x and y.
     *
     * @param figure - figure.
     * @param x      - x coordinate.
     * @param y      - y coordinate.
     * @return if move is successful return true.
     */
    public boolean makeMove(Figure figure, int x, int y) {
        if (!isFit(figure, x, y)) {
            return false;
        }
        for (int i = 0; i < figure.getNBlocks(); i++) {
            int xx = figure.getBlock(i).x() + x;
            int yy = figure.getBlock(i).y() + y;
            cells[xx][yy] = 1;
        }
        moves++;
        return true;
    }


    /**
     * The method that fulfills the end-of-game condition determines whether
     * this figure can be inserted somewhere.
     *
     * @param fig - figure.
     * @return - false if we can't place the figure.
     */
    public boolean canPlace(Figure fig) {
        for (int i = 0; i < N_CELLS; i++) {
            for (int j = 0; j < N_CELLS; j++) {
                if (isFit(fig, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Getter of cell.
     *
     * @param x - x coordinate.
     * @param y - y coordinate.
     * @return cell.
     */
    int get(int x, int y) {
        return cells[x][y];
    }

    /**
     * Getter of count of moves.
     *
     * @return count of moves.
     */
    public int getMoves() {
        return moves;
    }

    /**
     * Method for restarting play.
     */
    public void restart() {
        for (int i = 0; i < N_CELLS; i++) {
            for (int j = 0; j < N_CELLS; j++) {
                cells[i][j] = 0;
            }
        }
        moves = 0;
    }

    /**
     * Method for debugging.
     */
    public void print() {
        for (int row = 0; row < N_CELLS; row++) { // y
            for (int col = 0; col < N_CELLS; col++) // x
            {
                System.out.print(cells[col][row]);
            }
            System.out.println("");
        }
    }

}

