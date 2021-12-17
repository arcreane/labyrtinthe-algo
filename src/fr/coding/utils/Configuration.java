package fr.coding.utils;

import fr.coding.Menu;

// Création de la classe configuration.
public class Configuration {
    private int[] size = new int[2];

    private final int[] EASY = { 15, 5 }; // On déclare la taille du labyrinthe pour la difficulté easy.
    private final int[] MEDIUM = { 25, 15 }; // On déclare la taille du labyrinthe pour la difficulté mediul.
    private final int[] HARD = { 35, 25 }; // On déclare la taille du labyrinthe pour la difficulté hard.

    public Configuration(int difficulty) {
        setDifficulty(difficulty);
    }

    // On crée une foonction setDifficulty qui va permettre de choisir la difficulté.
    public void setDifficulty(int difficulty) {
        switch (difficulty) {
            case 1 -> this.size = EASY;
            case 2 -> this.size = MEDIUM;
            case 3 -> this.size = HARD;
            case 4 -> this.size = Menu.customSize();
        }
    }

    public int[] getSize() {
        return size;
    }
}
