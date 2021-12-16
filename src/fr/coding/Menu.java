package fr.coding;

import fr.coding.utils.Configuration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;

import static fr.coding.MazeSolver.*;
import static fr.coding.MazeSolver.expandHorizontally;

public class Menu {
    static Scanner scanner = new Scanner(System.in);
    static Configuration config = new Configuration(2);

    public static void menu() throws InterruptedException, IOException {
        System.out.println();
        System.out.println("------------------");
        System.out.println("| Menu :         |");
        System.out.println("| 1. Play        |");
        System.out.println("| 2. Config      |");
        System.out.println("| 3. Leaderboard |");
        System.out.println("| 4. Exit        |");
        System.out.println("------------------");

        int choose;

        do
        {
            System.out.print("-> ");
            choose = scanner.nextInt();
        } while (choose < 1 || choose > 4);

        System.out.println();

        switch (choose) {
            case 1 -> new Maze(config.getSize()[0], config.getSize()[1]);
            case 2 -> configuration();
            case 3 -> leaderboard();
            case 4 -> System.exit(0);
        }

        startMaze();
    }

    public static void startMaze() throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();

        boolean finish = false;
        boolean resolved = false;

        System.out.println("\nEnter 'finish' in the console if you find the exit or 'abort' to show the good path.");

        while (!finish) {
            System.out.print("-> ");

            String choose = scanner.next().toLowerCase(Locale.ROOT);

            if (choose.equals("finish")) {
                resolved = true;
                finish = true;
            } else if (choose.equals("abort")) {
                finish = true;
            }
        }

        endMaze(startTime, resolved);
    }

    public static void endMaze(long startTime, boolean resolved) throws IOException, InterruptedException {
        if (startTime != 0) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            long elapsedSeconds = elapsedTime / 1000;
            long elapsedMinutes = elapsedSeconds / 60;

            String time = elapsedMinutes % 60 + "m" + elapsedSeconds % 60 + "s";

            if (resolved) {
                System.out.println("\nYou have finish the labyrinth in " + time + "\n");
            } else {
                System.out.println("\nYou gave up the labyrinth in " + time + "s\n");
            }

            System.out.println("Enter your name: ");
            System.out.print("-> ");

            String name = scanner.next();

            BufferedWriter writer = new BufferedWriter(new FileWriter("leaderboard.txt", true));

            String log = "| Name: " + name + " | Time: " + time + " | Resolved: ";

            if (resolved) {
                log += "yes";
            } else {
                log += "no";
            }

            writer.write(log);
            writer.flush();
        }

        System.out.println("------------------");
        System.out.println("| 1. Show Path   |");
        System.out.println("| 2. Back 2 Menu |");
        System.out.println("------------------");

        int choose;

        do
        {
            System.out.print("-> ");
            choose = scanner.nextInt();
        } while (choose < 1 || choose > 2);

        switch (choose) {
            case 1 -> path();
            case 2 -> menu();
        }
    }

    public static void leaderboard() throws IOException, InterruptedException {
        String lb = Files.readString(Paths.get("leaderboard.txt"));

        if (new File("leaderboard.txt").length() != 0) {
            System.out.println("------------------");
            System.out.println("| Leaderboard : ");
            System.out.println("| \n" + lb);
            System.out.println("------------------");
        } else {
            System.out.println("Leaderboard is empty.");
        }

        menu();
    }

    public static void configuration() throws InterruptedException, IOException {
        System.out.println("------------------");
        System.out.println("| Config :       |");
        System.out.println("| 1. Easy        |");
        System.out.println("| 2. Medium      |");
        System.out.println("| 3. hard        |");
        System.out.println("| 4. Custom      |");
        System.out.println("| 5. Back        |");
        System.out.println("------------------");

        int choose;

        do
        {
            System.out.print("-> ");
            choose = scanner.nextInt();
        } while (choose < 1 || choose > 5);

        switch (choose) {
            case 1 -> config.setDifficulty(1);
            case 2 -> config.setDifficulty(2);
            case 3 -> config.setDifficulty(3);
            case 4 -> config.setDifficulty(4);
            case 5 -> menu();
        }

        menu();
    }

    public static int[] customSize() {
        System.out.println("\nEnter width :");

        int[] size = new int[2];

        do
        {
            System.out.print("-> ");
            size[0] = scanner.nextInt();
        } while (size[0] < 10 || size[0] > 133);

        System.out.println("\nEnter height :");

        do
        {
            System.out.print("-> ");
            size[1] = scanner.nextInt();
        } while (size[1] < 10 || size[1] > 133);

        return size;
    }

    public static void path() throws IOException, InterruptedException {
        InputStream f = new FileInputStream("labyrinth.txt");

        String[] lines = readLines(f);

        char[][] maze = decimateHorizontally (lines);
        solveMaze (maze);

        String[] solvedLines = expandHorizontally (maze);

        for (String solvedLine : solvedLines) {
            System.out.println(solvedLine);
        }

        System.out.println();

        endMaze(0, false);
    }
}