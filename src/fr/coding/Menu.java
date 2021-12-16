package fr.coding;

import fr.coding.utils.Configuration;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

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

    public static void startMaze() {
        long startTime = System.currentTimeMillis();

        boolean finish = false;

        System.out.println("\nEnter 'finish' in the console if you find the exit or 'resolve' to show the good path.");
        System.out.print("-> ");

        do {
            if (scanner.next().toLowerCase(Locale.ROOT).equals("finish")) {
                finish = true;
            } else if (scanner.next().toLowerCase(Locale.ROOT).equals("resolve")) {
                System.out.print("-> ");
            }
        } while (!finish);

        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        long elapsedMinutes = elapsedSeconds / 60;

        System.out.println("\nYou have finish the labyrinth in " + elapsedMinutes % 60 + "m" + elapsedSeconds % 60 + "s");

        endMaze();
    }

    public static void endMaze() {

    }

    public static void leaderboard() {
        System.out.println("Show leaderboard");
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
}