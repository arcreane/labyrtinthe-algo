package fr.coding;

import fr.coding.utils.Configuration;
import fr.coding.utils.Player;

import javax.swing.*;
import java.io.*;
import java.util.*;

import static fr.coding.MazeSolver.*;
import static fr.coding.MazeSolver.expandHorizontally;

// On crée une classe Menu.
public class Menu {
    static Scanner scanner = new Scanner(System.in);
    static Configuration config = new Configuration(2);
    static Map<Long, Player> map = new HashMap<>();

    // On affiche les différentes option que l'ont peut choisir sur le menu.
    public static void menu() throws InterruptedException, IOException {
        System.out.println();
        System.out.println("------------------");
        System.out.println("| Menu :         |");
        System.out.println("| 1. Play        |");
        System.out.println("| 2. Config      |");
        System.out.println("| 3. Leaderboard |");
        System.out.println("| 4. Exit        |");
        System.out.println("------------------");

        int choose; // Déclaration d'une variable Int "choose".

        do
        {
            System.out.print("-> ");
            choose = scanner.nextInt();
        } while (choose < 1 || choose > 4);

        System.out.println();

        // On déclare un switch pour créér les différentes option du menu.
        switch (choose) {
            case 1 -> new Maze(config.getSize()[0], config.getSize()[1]);
            case 2 -> configuration();
            case 3 -> leaderboard();
            case 4 -> System.exit(0);
        }

        startMaze();
    }

    // On crée une fonction qui va permettre de lancer le labyrinthe.
    public static void startMaze() throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();  // Création d'une variable long "startTime".

        boolean finish = false; // Création d'une variable boolean "finish".
        boolean resolved = false; // Création d'une variable boolean "resolved".

        System.out.println("\nEnter 'finish' in the console if you find the exit or 'abort' to show the good path.");

        // Déclaration d'une boucle while.
        while (!finish) {
            System.out.print("-> ");

            String choose = scanner.next().toLowerCase(Locale.ROOT);

            // Création d'une boucle if.
            if (choose.equals("finish")) {
                resolved = true;
                finish = true;
            } else if (choose.equals("abort")) {
                finish = true;
            }
        }

        endMaze(startTime, resolved);
    }

    // On crée une fonction pour terminer le labyrinthe.
    public static void endMaze(long startTime, boolean resolved) throws IOException, InterruptedException {
        if (startTime != 0) {
            long elapsedTime = System.currentTimeMillis() - startTime; // Création d'une variable long "elapsedTime".
            long elapsedSeconds = elapsedTime / 1000; // Création d'une variable long "elapsedSeconds".
            long elapsedMinutes = elapsedSeconds / 60; // Création d'une variable long "elapsedMinutes".

            String time = elapsedMinutes % 60 + "m" + elapsedSeconds % 60 + "s";

            // Création d'une boucle if.
            if (resolved) { // Si le labyrinthe est résolue afiicher le message "You have finish the labyrinth in "time"".
                System.out.println("\nYou have finish the labyrinth in " + time + "\n");
            } else { // Sinon afficher le message " You gave up the labyrinth in "time"".
                System.out.println("\nYou gave up the labyrinth in " + time + "\n");
            }

            final JFrame parent = new JFrame();
            parent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            parent.setVisible(true);
            JOptionPane.showMessageDialog(parent, "You finished well done");

            System.out.println("Enter your name: ");// Affichage d'un scanner.
            System.out.print("-> "); // Affichage d'un scanner.

            String name = scanner.next(); // Création d'une variable scanner en string.

            BufferedWriter writer = new BufferedWriter(new FileWriter("leaderboard.txt", true));

            String log = "| Name: " + name + " | Points : "+ (elapsedTime / 100) + " | Time: " + time + " | Resolved: ";

            // Création d'une boucle if pour savoir si le labyrinthe est résolue ou non.
            if (resolved) {
                log += "yes";
            } else {
                log += "no";
            }

            map.put((elapsedTime / 100), new Player(name, elapsedTime / 100, time, resolved));

            writer.write("\n" + log);
            writer.flush();
        }
// Affichage d'un nouveau menu.
        System.out.println("------------------");
        System.out.println("| 1. Show Path   |");
        System.out.println("| 2. Back 2 Menu |");
        System.out.println("------------------");

        int choose; // Déclaration d'une variable int "choose".

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

    // On crée une fonction "leaderboard" pour afficher les meilleurs joueurs.
    public static void leaderboard() throws IOException, InterruptedException {
        Map<Long, Player> treeMap = new TreeMap<>(map);

        if (treeMap.size() != 0) {
            System.out.println("------------------");
            System.out.println("| Leaderboard : \n|");


            for (long key : treeMap.keySet()) {
                String log = "| " + map.get(key).getName() + " | " + map.get(key).getPoints() + " | " + map.get(key).getTime() + " | ";
                if (map.get(key).isResolved()) {
                    log += "yes";
                } else {
                    log += "no";
                }

                System.out.println(log);
            }

            System.out.println("------------------");
        } else {
            System.out.println("Leaderboard is empty.");
        }

        menu();
    }

    // Création d'une fonction "configuration".
    public static void configuration() throws InterruptedException, IOException {
        System.out.println("------------------");
        System.out.println("| Config :       |");
        System.out.println("| 1. Easy        |"); // Permettre de choisir le niveau "Easy".
        System.out.println("| 2. Medium      |"); // Permettre de choisir le niveau "Medium".
        System.out.println("| 3. Hard        |"); // Permettre de choisir le niveau "Hard".
        System.out.println("| 4. Custom      |"); // Permettre de choisir le niveau "Custom".
        System.out.println("| 5. Back        |"); // Permettre de choisir le niveau "Back".
        System.out.println("------------------");


        int choose; // Création d'une variable int "choose"

        do
        {
            System.out.print("-> ");
            choose = scanner.nextInt();
        } while (choose < 1 || choose > 5);

// On déclare un switch de choose pour choisir la difficulté du labyrinthe que l'on veut.
        switch (choose) {
            case 1 -> config.setDifficulty(1); // = Difficulté "Easy".
            case 2 -> config.setDifficulty(2); // = Difficulté "Medium".
            case 3 -> config.setDifficulty(3); // = Difficulté "Hard".
            case 4 -> config.setDifficulty(4); // = Difficulté "Custom".
            case 5 -> menu(); // Retour au menu.
        }

        menu();
    }

    // Création d'une fontion "CustomSize" qui permet de choisir la taille du labyrinthe.
    public static int[] customSize() {
        System.out.println("\nEnter width :"); // Afficher la largeur voulu.

        int[] size = new int[2];

        do
        {
            System.out.print("-> ");
            size[0] = scanner.nextInt();
        } while (size[0] < 10 || size[0] > 133);

        System.out.println("\nEnter height :");  // Afficher la hauteur voulu.

        do
        {
            System.out.print("-> ");
            size[1] = scanner.nextInt();
        } while (size[1] < 10 || size[1] > 133);

        return size;
    }

    // Création de la fonction path qui permet d'écrire la solution du labirynthe.
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
