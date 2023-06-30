/*
    Author: Alejandro Muñoz Navarro
 */
package reto.técnico.damavis.alejandro.muñoz.navarro;

import java.util.Scanner;

public class RetoTécnicoDamavisAlejandroMuñozNavarro {

    public static void main(String[] args) {
        boolean error = false;
        String message = "\nLabyrinth = \n";
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the number of rows of the labyrinth: ");
        int rows = scan.nextInt();
        // Until a numero between [3,1000] is entered, it isn't continued
        while (rows < 3 || rows > 1000) {
            System.out.print("The number isn't valid, it must be greater than or equal to 3 or less than or equal to 1000, please enter another one: ");
            rows = scan.nextInt();
        }
        System.out.print("Enter the number of columns of the labyrinth: ");
        int columns = scan.nextInt();
        // Until a numero between [3,1000] is entered, it isn't continued
        while (columns < 3 || columns > 1000) {
            System.out.print("The number isn't valid, it must be greater than or equal to 3 or less than or equal to 1000, please enter another one: ");
            columns = scan.nextInt();
        }
        System.out.println("\nEnter the maze (use '#' to represent walls and '.' to represent empty cells), each line represents a row. Press 'Enter' to move to the next row: ");
        char[][] lab = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            String linea = scan.next();
            // Validate the length of the entered line.
            if (linea.length() != columns) {
                System.out.println("\"The length of the line doesn't match the number of columns.");
                i = -1;  // Clear the maze and let's start again.
                continue;
            }
            // Save the characters in the matrix.
            for (int j = 0; j < columns; j++) {

                /*
                If an invalid character has been entered, it is considered an error. 
                This error will be displayed by printing the incorrect maze and marking the erroneous characters in red.
                 */
                if (linea.charAt(j) != '.' && linea.charAt(j) != '#' && !error) {
                    error = true;
                }

                //Check that the initial cells and the final cell are empty.
                if ((i == 0 && (j == 0 || j == 1 || j == 2) || (i == rows - 1 && j == columns - 1)) && linea.charAt(j) == '#') {
                    System.out.println("The first 3 cells and the last cell must be empty.");
                    i = -1; // Clear the maze and let's start again.
                    break;
                }
                lab[i][j] = linea.charAt(j);
            }
        }

        //If an error was made, change the final message.
        if (error) {
            message = "Invalid maze. Please enter a valid maze. Errors are marked in red.\n";
        }
        Labyrinth maze = new Labyrinth(lab, rows, columns); // Create the labyrinth
        System.out.println(message + maze.toString());
        //If an error was made, the labyrinth can't be resolved
        if (!error) {
            System.out.println("Result: " + maze.LabyrinthSolver()); // Resolve the labyrint and print the moves.
        }
    }

}
