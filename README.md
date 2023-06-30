# Reto-Tecnico-Damavis

## Problem

The problem is to solve a labyrinth with the shortest path, where walls are represented as '#' and the "player" is a rectangle of 3 x 1, occupying three cells. The maze always starts at the top-left corner, and the exit is located at the bottom-right corner. The possible movements are to move right, left, up, and down, in addition to rotating.

## Solution
To solve it, Java has been used. An A* search algorithm has been applied, and to ensure the shortest path, it has been implemented with the Manhattan heuristic, which, as its name suggests, uses the Manhattan distance. $h=  |x_2 - x_1| + |{y_2 - y_1|$

The labyrinth should be entered through the console line by line, following these instructions:
- Use '.' for an empty cell.
- Use '#' for a wall.
- Press 'Enter' to move to the next row.
