package fr.coding;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static fr.coding.MazeSolver.*;
import static fr.coding.Menu.menu;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        menu();

        InputStream f = new FileInputStream("labyrinth.txt");

        String[] lines = readLines(f);

        char[][] maze = decimateHorizontally (lines);
        solveMaze (maze);


        String[] solvedLines = expandHorizontally (maze);

        for (String solvedLine : solvedLines) {
            System.out.println(solvedLine);
        }
    }
}
