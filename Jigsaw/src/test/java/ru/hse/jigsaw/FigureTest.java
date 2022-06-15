package ru.hse.jigsaw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FigureTest {

    @Test
    void getBlock() {
        Figure figure = new Figure();

        String f = "-1,0;0,0;0,1;1,1";
        String[] points = f.split(";");
        figure.blocks = new Block[points.length];
        for (int j = 0; j < points.length; j++) {
            figure.blocks[j] = Block.parse(points[j]);
        }
        assertEquals(figure.getBlock(0), figure.blocks[0]);
    }

    @Test
    void getNBlocks() {
        Figure figure = new Figure();

        String f = "-1,0;0,0;0,1;1,1";
        String[] points = f.split(";");
        figure.blocks = new Block[points.length];
        for (int j = 0; j < points.length; j++) {
            figure.blocks[j] = Block.parse(points[j]);
        }
        assertEquals(figure.getNBlocks(), 4);
    }
}