package com.klasevich.task.prince.creator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.klasevich.task.prince.creator.Data.*;

public class Labyrinth {
    private static Logger logger = LogManager.getLogger();
    private int[][][] labyrinth;
    private int[][][] changedLabyrinth;

    public int numberOfLevels;
    public int width;
    public int length;

    private int princeX;
    private int princeY;


    public void createLabyrinth(List<String> lines) {
        setLabyrinthSize(lines.get(0));
        lines.remove(0);
        createLabyrinthAreas(lines);
    }


    private void createLabyrinthAreas(List<String> lines) {

        for (int i = 0; i < numberOfLevels; i++) {
            for (int j = 0; j < width; j++) {
                String[] strings = lines.get(FIRST).split(DELIMITER);
                for (int k = 0; k < length; k++) {
                    labyrinth[i][j][k] = changeToInt(strings[k]);
                }
                lines.remove(FIRST);
            }
        }
    }

    private int changeToInt(String symbol) {
        return switch (symbol) {
            case "1" -> PRINCE;
            case "2" -> PRINCESS;
            case "." -> FREE;
            case "o" -> WALL;
            default -> Integer.MIN_VALUE;
        };
    }

    private void setLabyrinthSize(String line) {
        String[] data = line.split(DELIMITER);

        numberOfLevels = Integer.parseInt(data[FIRST]);
        width = Integer.parseInt(data[SECOND]);
        length = Integer.parseInt(data[THIRD]);

        labyrinth = new int[numberOfLevels][width][length];
        changedLabyrinth = new int[numberOfLevels][width][length];

        logger.info("The number of labyrinth levels is {}", numberOfLevels);
        logger.info("The size of each area is {} X {}", width, length);
    }

    public int findMinTime() {
        findPrince();
        findPrincess();
        findWay();
        int result = MIN_WAY * TIME;
        logger.info("min time to find princess is {}", result);
        return result;
    }


    public void findPrince() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (labyrinth[FIRST][i][j] == PRINCE) {
                    changedLabyrinth[FIRST][i][j] = PRINCE;
                    princeX = j;
                    princeY = i;
                    return;
                }
            }
        }
    }

    public void findPrincess() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (labyrinth[length - 1][i][j] == PRINCESS) {
                    changedLabyrinth[length - 1][i][j] = PRINCESS;
                    return;
                }
            }
        }
    }

    public void findWay() {
        checkNeighbouringAreas(0, princeX, princeY, 1);
        for (int iteration = 1; MIN_WAY == Integer.MAX_VALUE; iteration++) {
            findArea(iteration);
        }
    }

    private void findArea(int iteration) {
        for (int i = 0; i < numberOfLevels; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < length; k++) {
                    if (changedLabyrinth[i][j][k] == iteration) {
                        checkNeighbouringAreas(i, j, k, iteration + 1);
                    }
                }
            }
        }
    }

    private void checkNeighbouringAreas(int z, int x, int y, int iteration) {
        if (x > 0) {
            checkArea(z, x - 1, y, iteration);
        }
        if (x < width - 1) {
            checkArea(z, x + 1, y, iteration);
        }
        if (y > 0) {
            checkArea(z, x, y - 1, iteration);
        }
        if (y < length - 1) {
            checkArea(z, x, y + 1, iteration);
        }
        if (z < length - 1) {
            checkArea(z + 1, x, y, iteration);
        }
    }

    private void checkArea(int z, int x, int y, int iteration) {
        if (isPrincess(z, x, y)) {
            if (iteration < MIN_WAY) {
                MIN_WAY = iteration;
            }
            changedLabyrinth[z][x][y] = PRINCESS;
        } else {

            if (isFree(z, x, y)) {
                changedLabyrinth[z][x][y] = iteration;
            } else {

                if (isWall(z, x, y)) {
                    changedLabyrinth[z][x][y] = -1;
                } else {

                    if (isShorterWay(z, x, y, iteration)) {
                        changedLabyrinth[z][x][y] = iteration;
                    }
                }
            }
        }
    }

    private boolean isPrincess(int z, int x, int y) {
        return changedLabyrinth[z][x][y] == PRINCESS;
    }

    private boolean isFree(int z, int x, int y) {
        return labyrinth[z][x][y] == 0 && changedLabyrinth[z][x][y] == 0;
    }

    private boolean isWall(int z, int x, int y) {
        return labyrinth[z][x][y] == -1 && changedLabyrinth[z][x][y] == 0;
    }

    private boolean isShorterWay(int z, int x, int y, int iteration) {
        return changedLabyrinth[z][x][y] > iteration && changedLabyrinth[z][x][y] == 0;
    }
}
