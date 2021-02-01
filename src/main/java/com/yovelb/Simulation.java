package com.yovelb;

public class Simulation {
    public static int ALIVE = 1;
    public static int DEAD = 0;

    public final int width;
    public final int height;
    private int[][] board;

    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
    }

    public static Simulation copy(Simulation simulation) {
        Simulation copy = new Simulation(simulation.width, simulation.height);
        for (int x = 0; x < simulation.width; x++) {
            for (int y = 0; y < simulation.height; y++) {
                copy.setState(x, y, simulation.getState(x, y));
            }
        }
        return copy;
    }

    public void printBoard() {
        System.out.println("____");
        for (int y = 0; y < height; y++) {
            StringBuilder line = new StringBuilder("|");
            for (int x = 0; x < width; x++) {
                if (this.board[x][y] == DEAD) {
                    line.append(".");
                } else {
                    line.append("*");
                }
            }
            line.append("|");
            System.out.println(line);
        }
        System.out.println("____\n");
    }

    public int countAliveNeighbours(int x, int y) {
        int count = 0;
        count += getState(x - 1, y - 1);
        count += getState(x, y - 1);
        count += getState(x + 1, y - 1);

        count += getState(x - 1, y);
        count += getState(x + 1, y);

        count += getState(x - 1, y + 1);
        count += getState(x, y + 1);
        count += getState(x + 1, y + 1);

        return count;
    }

    public void step() {
        int[][] newBoard = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int aliveNeighbours = countAliveNeighbours(x, y);
                if (getState(x, y) == ALIVE) {
                    if (aliveNeighbours < 2) {
                        newBoard[x][y] = DEAD;
                    } else if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                        newBoard[x][y] = ALIVE;
                    } else if (aliveNeighbours > 3) {
                        newBoard[x][y] = DEAD;
                    }
                } else {
                    if (aliveNeighbours == 3) {
                        newBoard[x][y] = ALIVE;
                    }
                }
            }
        }
        this.board = newBoard;
    }

    public void clearBoard() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.board[x][y] = DEAD;
            }
        }
    }

    public int getState(int x, int y) {
        if ((x < 0 || x >= width) || (y < 0 || y >= height)) {
            return DEAD;
        }
        return this.board[x][y];
    }

    public void setState(int x, int y, int state) {
        if ((x < 0 || x >= width) || (y < 0 || y >= height)) {
            return;
        }
        this.board[x][y] = state;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
