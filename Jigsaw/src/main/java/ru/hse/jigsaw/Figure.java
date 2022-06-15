package ru.hse.jigsaw;

import java.util.ArrayList;
import java.util.List;

/**
 * Class of figure.
 */
public class Figure {
    /**
     * List of all types of blocks.
     */
    protected static List<Block[]> configs;

    /**
     * Array of blocks.
     */
    protected Block blocks[];

    /**
     * Constructor of blocks.
     */
    protected Figure() {
        blocks = null;
    }

    /**
     * Getter of Block.
     *
     * @param index - defines type of block.
     * @return - block.
     */
    Block getBlock(int index) {
        return blocks[index];
    }

    /**
     * Getter of num of Block.
     *
     * @return
     */
    int getNBlocks() {
        return blocks.length;
    }

    /**
     * Initialize all of figures.
     */
    protected static void initConfig() {
        configs = new ArrayList<Block[]>();
        String[] figures = {
                "0,1;0,0;0,-1;1,-1",  //0 - num of figure for debugging.
                "-1,-1;-1,0;0,0;1,0", //1
                "0,-1;0,0;0,1;-1,1", //2
                "-1,0;0,0;1,0;1,1", //3
                "-1,-1;0,-1;0,0;0,1", //4
                "-1,0;0,0;1,0;1,-1", //5
                "0,-1;0,0;0,1;1,1", //6
                "-1,1;-1,0;0,0;1,0", //7
                "-1,-1;-1,0;0,0;0,1",
                "-1,1;0,1;0,0;1,0",
                "-1,1;-1,0;0,0;0,-1",
                "-1,0;0,0;0,1;1,1",
                "-1,1;0,1;1,1;1,0;1,-1",
                "-1,-1;-1,0;-1,1;0,1;1,1",
                "-1,1;-1,0;-1,-1;0,-1;1,-1",
                "0,-1;0,0;-1,1;0,1;1,1",
                "-1,-1;0,-1;1,-1;0,0;0,1",
                "-1,-1;-1,0;-1,1;0,0;1,0",
                "-1,0;0,0;1,-1;1,0;1,1",
                "-1,0;0,0;1,0",
                "0,-1;0,0;0,1",
                "0,0",
                "0,1;0,0;1,0",
                "-1,0;0,0;0,1",
                "-1,0;0,0;0,-1",
                "0,-1;0,0;1,0",
                "0,-1;0,0;0,1;1,0",
                "-1,0;0,0;1,0;0,1",
                "-1,0;0,0;0,-1;0,1",
                "-1,0;0,0;1,0;-1,0"};
        for (int i = 0; i < figures.length; i++) {
            String[] points = figures[i].split(";");
            Block[] blocks = new Block[points.length];
            for (int j = 0; j < points.length; j++) {
                blocks[j] = Block.parse(points[j]);
            }
            configs.add(blocks);
        }

    }

    /**
     * Method generates random figure.
     *
     * @return - figure.
     */
    public static Figure getRandomFigure() {
        if (configs == null) {
            initConfig();
        }
        int size = configs.size();
        int kCount = (int) (Math.random() * size);
        Figure figure = new Figure();
        // For debugging.
        //System.out.println("Figure No "+kCount);
        figure.blocks = configs.get(kCount);
        return figure;
    }

    /**
     * Getter of figure by index of type of figure.
     *
     * @param index - index of figure.
     * @return figure.
     */
    public static Figure getFigure(int index) {
        if (configs == null) {
            initConfig();
        }
        Figure figure = new Figure();
        figure.blocks = configs.get(index);
        return figure;
    }

    /**
     * Getter for debugging.
     *
     * @return - num of figures.
     */
    public static int getNumOfPossibleFigures() {
        return configs.size();
    }
}

