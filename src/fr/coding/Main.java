package fr.coding;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        final JFrame parent = new JFrame();
        parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parent.setVisible(true);
        JOptionPane.showMessageDialog(parent, "You finished well done");
        

    }

}
