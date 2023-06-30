/*
    Author: Alejandro Muñoz Navarro
 */
package reto.técnico.damavis.alejandro.muñoz.navarro;

/*
The Coordinate class represents a coordinate with x and y values. 
It provides methods to access and modify the x and y values, as well as comparison methods to check for equality with other coordinate 
or with separate x and y values.
 */
public class Coordinate {

    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean compareTo(Coordinate cor) {
        if (this.x != cor.x) {
            return false;
        }
        return this.y == cor.y;
    }

    public boolean compareTo(int X, int Y) {
        if (this.x != X) {
            return false;
        }
        return this.y == Y;
    }

    @Override
    public String toString() {
        return "Coordenate{" + "x=" + x + ", y=" + y + '}';
    }

}
