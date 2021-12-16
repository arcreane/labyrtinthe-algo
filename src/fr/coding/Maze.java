package fr.coding;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

// https://rosettacode.org/wiki/Maze_generation#Java

public class Maze {
    private final int width;
    private final int height;
    private final int[][] maze;

    public Maze(int width, int height) throws IOException {
        this.width = width;
        this.height = height;

        maze = new int[this.width][this.height];

        generateMaze(0, 0);
        display();
    }

    private void generateMaze(int cx, int cy) {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));

        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;

            if (between(nx, width) && between(ny, height) && (maze[nx][ny] == 0)) {
                maze[cx][cy] |= dir.bit;
                maze[nx][ny] |= dir.opposite.bit;

                generateMaze(nx, ny);
            }
        }
    }

    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    public void display() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("labyrinth.txt", false));

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 && j == 0) {
                    System.out.print("#   ");
                } else {
                    System.out.print((maze[j][i] & 1) == 0 ? "#---" : "#   ");
                }

                writer.write((maze[j][i] & 1) == 0 ? "#---" : "#   ");
            }
            writer.write("# \n");
            System.out.println("#");

            for (int j = 0; j < width; j++) {
                writer.write((maze[j][i] & 8) == 0 ? "|   " : "    ");
                System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
            }

            writer.write("|\n");
            System.out.println("|");
        }

        for (int j = 0; j < width - 1; j++) {
            System.out.print("#---");
        }

        for (int j = 0; j < width; j++) {
            writer.write("#---");
        }

        writer.write("#\n");
        System.out.println("#   #");

        writer.flush();
    }

    private enum DIR {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);

        private final int bit;
        private final int dx;
        private final int dy;
        private DIR opposite;

        // use the static initializer to resolve forward references
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    }
}
