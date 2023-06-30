/*
    Author: Alejandro Muñoz Navarro
 */
package reto.técnico.damavis.alejandro.muñoz.navarro;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Labyrinth {

    private final char[][] maze;
    private PriorityQueue<Cell> open; //Priority queue that allows us to store cells according to their f-value.
    private final Coordinate ExitCoordinate; // Coordinates of the exit.
    private final int rows;
    private final int columns;
    private int g; // Step value
    private ArrayList<Cell> visited; //Allow us to store the visited cells.
    private final Coordinate Origin; // Coordinates of the initial cell.

    public Labyrinth(char[][] maze, int filas, int columnas) {
        visited = new ArrayList<Cell>();
        g = 0;
        this.rows = filas;
        this.columns = columnas;
        ExitCoordinate = new Coordinate(filas - 1, columnas - 1);
        this.maze = maze;
        this.Origin = new Coordinate(0, 1);
    }

    /*To solve the problem we will use an A* search algorithm using the Manhattan heuristic.
      We will return the moves of the shortest path.
     */
    public int LabyrinthSolver() {
        Coordinate currentCoordinates;
        open = new PriorityQueue<>();
        //Calculate the distance to the end using the Manhattan distance. distance = |x_2-x_1| + |y_2-y_1|
        double originDistance = Math.abs(ExitCoordinate.getX() - Origin.getX()) + Math.abs(ExitCoordinate.getY() - Origin.getY());
        open.add(new Cell(Origin, g, originDistance));

        while (!open.isEmpty()) {
            Cell currentCell = open.remove(); //Get the current position and delete it from the queue.
            currentCoordinates = currentCell.getCoordinate();

            // Check if we have reached the exit of the labyrinth.
            if (ExitCoordinate.compareTo(currentCoordinates.getX(), currentCoordinates.getY() + 1) || ExitCoordinate.compareTo(currentCoordinates.getX() + 1, currentCoordinates.getY())) {
                int moves = 0;
                // Count the moves of the shortest path
                while (currentCell.getPrevious() != null) {
                    currentCell = currentCell.getPrevious();
                    moves++;
                }
                return moves;
            }
            // If we haven't reached the exit, we calculate to which neighbouring cells we can move and add them in the queue.
            checkMoves(currentCell);
            // Add the current cell to the list of visited cells.
            visited.add(currentCell);
            g++; //Increase the step count
        }
        return -1; // No solution found
    }

    /*
    Check which cells can be reached, considering the orientation.
    A cell is available when it's not visited or marked for visiting, and when the cell is empty and doesn't exit the maze.
    Regardless of the orientation, the following order is always followed: Right - Down - Left - Up - Rotate. 
    If any movement can be made, calculate the distances to the origin and the target, and add the new cell to the queue in the correct position.
     */
    private void checkMoves(Cell p) {

        Coordinate coor = p.getCoordinate();
        Cell nextCell;

        if (p.isHorizontal()) {
            //Right
            nextCell = new Cell(new Coordinate(coor.getX(), coor.getY() + 1), p); //Create the new cell
            // Check if the right cell is available
            if (!isValid(nextCell) && coor.getY() + 2 < columns && maze[coor.getX()][coor.getY() + 2] != '#') {
                calculateDistances(nextCell);
            }
            //Down
            nextCell = new Cell(new Coordinate(coor.getX() + 1, coor.getY()), p); //Create the new cell
            // Check if the three cells below are available
            if (!isValid(nextCell) && coor.getX() + 1 < rows && maze[coor.getX() + 1][coor.getY()] != '#' && maze[coor.getX() + 1][coor.getY() + 1] != '#' && maze[coor.getX() + 1][coor.getY() - 1] != '#') {

                calculateDistances(nextCell);
            }
            //Left
            nextCell = new Cell(new Coordinate(coor.getX(), coor.getY() - 1), p); //Create the new cell
            // Check if the left cell is available
            if (!isValid(nextCell) && coor.getY() - 2 >= 0 && maze[coor.getX()][coor.getY() - 2] != '#') {
                calculateDistances(nextCell);
            }

            //Up
            nextCell = new Cell(new Coordinate(coor.getX() - 1, coor.getY()), p); //Create the new cell
            // Check if the three cells upper are available
            if (!isValid(nextCell) && coor.getX() - 1 >= 0 && maze[coor.getX() - 1][coor.getY()] != '#' && maze[coor.getX() - 1][coor.getY() + 1] != '#' && maze[coor.getX() - 1][coor.getY() - 1] != '#') {
                calculateDistances(nextCell);
            }

            //Rotate
            nextCell = new Cell(p.getCoordinate(), p); //Create the new cell
            // Check if all surrounding cells are available
            if (!isValid(nextCell) && coor.getX() - 1 >= 0 && maze[coor.getX() - 1][coor.getY()] != '#' && maze[coor.getX() - 1][coor.getY() + 1] != '#' && maze[coor.getX() - 1][coor.getY() - 1] != '#'
                    && coor.getX() + 1 < rows && maze[coor.getX() + 1][coor.getY()] != '#' && maze[coor.getX() + 1][coor.getY() + 1] != '#' && maze[coor.getX() + 1][coor.getY() - 1] != '#') {
                nextCell.setHorizontal(false);

                calculateDistances(nextCell);
            }

        } else {
            //Right
            nextCell = new Cell(new Coordinate(coor.getX(), coor.getY() + 1), p); //Create the new cell
            nextCell.setHorizontal(false);
            //Check if all three right cells are available
            if (!isValid(nextCell) && coor.getY() + 1 < columns && maze[coor.getX()][coor.getY() + 1] != '#' && maze[coor.getX() + 1][coor.getY() + 1] != '#' && maze[coor.getX() - 1][coor.getY() + 1] != '#') {
                calculateDistances(nextCell);
            }
            //Down
            nextCell = new Cell(new Coordinate(coor.getX() + 1, coor.getY()), p); //Create the new cell
            nextCell.setHorizontal(false);
            // Check if the cell below is available
            if (!isValid(nextCell) && coor.getX() + 2 < rows && maze[coor.getX() + 2][coor.getY()] != '#') {
                calculateDistances(nextCell);
            }
            //Left
            nextCell = new Cell(new Coordinate(coor.getX(), coor.getY() - 1), p); //Create the new cell
            nextCell.setHorizontal(false);
            //Check if all three left cells are available
            if (!isValid(nextCell) && coor.getY() - 1 >= 0 && maze[coor.getX()][coor.getY() - 1] != '#' && maze[coor.getX() + 1][coor.getY() - 1] != '#' && maze[coor.getX() - 1][coor.getY() - 1] != '#') {
                calculateDistances(nextCell);
            }
            //Up
            nextCell = new Cell(new Coordinate(coor.getX() - 1, coor.getY()), p); //Create the new cell
            nextCell.setHorizontal(false);
            // Check if the cell above is available
            if (!isValid(nextCell) && coor.getX() - 2 >= 0 && maze[coor.getX() - 2][coor.getY()] != '#') {
                calculateDistances(nextCell);
            }
            //Rotate
            nextCell = new Cell(p.getCoordinate(), p); //Create the new cell
            nextCell.setHorizontal(false);
            // Check if all surrounding cells are available
            if (!isValid(nextCell) && coor.getY() + 1 < columns && maze[coor.getX()][coor.getY() + 1] != '#' && maze[coor.getX() + 1][coor.getY() + 1] != '#' && maze[coor.getX() - 1][coor.getY() + 1] != '#'
                    && coor.getY() - 1 >= 0 && maze[coor.getX()][coor.getY() - 1] != '#' && maze[coor.getX() + 1][coor.getY() - 1] != '#' && maze[coor.getX() - 1][coor.getY() - 1] != '#') {
                nextCell.setHorizontal(true);
                calculateDistances(nextCell);
            }

        }

    }

    /*
    Calculate the distance to the exit using Manhattan distance
    Calculate the distance to the start by increasing g
    Add the cell to the queue
     */
    private void calculateDistances(Cell p) {
        double originDistance = Math.abs(ExitCoordinate.getX() - p.getCoordinate().getX()) + Math.abs(ExitCoordinate.getY() - p.getCoordinate().getY());
        p.setDistanceToEnd(originDistance);
        p.setDistanceToStart(g + 1);
        open.offer(p);
    }

    //Check that it has not been visited or is going to be visited, that is, it is neither in the open list nor in the visited list.
    private boolean isValid(Cell p) {

        for (Cell p_aux : open) {
            if (p.equals(p_aux)) {
                return true;
            }
        }

        for (int i = visited.size() - 1; i >= 0; i--) {
            if (p.equals((visited.get(i)))) {
                return true;
            }

        }
        return false;
    }

    //Convert the maze into a string, if the characters are not valid, they are displayed in red.
    @Override
    public String toString() {
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String printMaze = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (maze[i][j] != '.' && maze[i][j] != '#') {
                    printMaze += RED + maze[i][j] + RESET;
                } else {
                    printMaze += maze[i][j];
                }

            }
            printMaze += '\n';
        }
        return printMaze;
    }

}
