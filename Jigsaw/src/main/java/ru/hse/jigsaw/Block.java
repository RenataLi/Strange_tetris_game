package ru.hse.jigsaw;

/**
 * Defines one "square" of the figure.
 * x,y, -- coordinates of the square inside the figure relative to the "main" square.
 * x -> left
 * y -> down
 */
public record Block(int x, int y) {
    /**
     * Parses x and y coordinates of Block.
     *
     * @param str - input string.
     * @return Block.
     */
    public static Block parse(String str) {
        String[] parseStr = str.split(",");
        int xx = Integer.valueOf(parseStr[0]);
        int yy = Integer.valueOf(parseStr[1]);
        return new Block(xx, yy);
    }
}
