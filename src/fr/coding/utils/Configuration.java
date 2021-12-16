package fr.coding.utils;

import fr.coding.Menu;

public class Configuration {
    private int[] size = new int[2];

    private final int[] EASY = { 15, 5 };
    private final int[] MEDIUM = { 25, 15 };
    private final int[] HARD = { 35, 25 };

    public Configuration(int difficulty) {
        setDifficulty(difficulty);
    }

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
