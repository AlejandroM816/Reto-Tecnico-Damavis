/*
    Author: Alejandro Muñoz Navarro
 */
package reto.técnico.damavis.alejandro.muñoz.navarro;

//Implements "Comparable", so that we can tell the priority queue to sort according to our own "compareTo()" method.
public class Cell implements Comparable<Cell> {

    private Coordinate coordinate; // Coordinates of the cell
    private int DistanceToStart; // Distance from the cell to the start cell, we consider the start cell to be the cell in the centre of the bar.
    private double DistanceToEnd; // Distance from the cell to the end cell, the end cell is the lower right cell.
    private boolean horizontal = true; //Indicates the orientation, as this can only be horizontal or vertical, i.e. a binary value, we encode it as a boolean, if true it'ss horizontal, if false it's vertical.
    private Cell previous; // It tells us which cell is the predecessor of this one, it helps us to determine the shortest path and therefore to count the movements of this path.

    public Cell(Coordinate coordenate, Cell p) {
        this.coordinate = coordenate;
        this.previous = p;
    }

    public Cell(Coordinate coordenate, int DistanciaAlIncio, double DistanciaAlFinal) {
        this.coordinate = coordenate;
        this.DistanceToStart = DistanciaAlIncio;
        this.DistanceToEnd = DistanciaAlFinal;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordenate) {
        this.coordinate = coordenate;
    }

    public int getDistanceToStart() {
        return DistanceToStart;
    }

    public void setDistanceToStart(int DistanceToStart) {
        this.DistanceToStart = DistanceToStart;
    }

    public double getDistanceToEnd() {
        return DistanceToEnd;
    }

    public void setDistanceToEnd(double DistanceToEnd) {
        this.DistanceToEnd = DistanceToEnd;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public Cell getPrevious() {
        return previous;
    }

    public void setPrevious(Cell previous) {
        this.previous = previous;
    }

    //Two cells are equal if they have the same coordinates and are in the same orientation.
    public boolean equals(Cell p) {
        return (this.coordinate.compareTo(p.coordinate) && this.horizontal == p.horizontal);
    }

    /*
    To compare two cells, we will first calculate their f-value (f = h + g). 
    This value indicates the priority of a cell, with lower values indicating higher priority. 
    If the f-values are equal, we then compare the distances to the end. 
    The cell with a shorter distance to the end will have higher priority. 
    This allows us to order the priority queue correctly..
     */
    @Override
    public int compareTo(Cell p) {
        double f_p_value = p.DistanceToEnd + p.DistanceToStart; //calculate f_value
        double f_this_value = this.DistanceToEnd + this.DistanceToStart; //calculate f_value
        if (f_this_value < f_p_value) {
            return -1; // The cell of the class has more priority
        } else {
            if (f_this_value == f_p_value && this.DistanceToEnd < p.DistanceToEnd) {
                return -1; // The cell of the class has more priority
            } else {
                if (this.DistanceToEnd > p.DistanceToEnd) {
                    return 1; // The parameter cell has more priority
                }
            }
        }
        return 0; // Are equal

    }

}
