//Israel Alcantara

package edu.njit.cs114;
import java.awt.*;

/**
 * Class that solves maze problems with backtracking.
 *
 * @author Koffman and Wolfgang
 * Modified by Ravi Varadarajan for CS114
 **/

public class Maze
{
    public static Color PATH = Color.green;
    public static Color BACKGROUND = Color.white;
    public static Color NON_BACKGROUND = Color.red;
    public static Color TEMPORARY = Color.black;

    /** The maze */
    private TwoDimGrid maze;

    private int cnt;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    /** Wrapper method. */
    public boolean findMazePath(int startCol, int startRow, int destCol, int destRow)
    {
        cnt = 0; // keeps track of order of cell visits
        return findMazePathAux(startCol, startRow, destCol, destRow);
    }

    /**
     * PROBLEM 1
     * Attempts to find a maze path recursively through point (x, y) to destination cell (destCol,destRow)
     *
     * @pre Possible path cells are initially in NON_BACKGROUND color
     * @post If a path is found, all cells on it are set to the PATH color; all
     *       cells that were visited but are not on the path are in the TEMPORARY
     *       color.
     * @param x
     *            The x-coordinate of current point
     * @param y
     *            The y-coordinate of current point
     * @param destCol
     *            The x-coordinate of destination cell
     * @param destRow
     *            The y-coordinate of destination cell
     * @return If a path through (x, y) is found, true; otherwise, false
     *         If a path is found, color the cells in the path with PATH color
     *         Also number cells in the order in which they are visited
     */
    public boolean findMazePathAux(int x, int y, int destCol, int destRow)
    {
        if (y >= maze.getNRows() || x >= maze.getNCols() || x < 0 || y < 0)
        {
            // no path from (x,y) to destination
            return false;
        }

        if (x==destCol && y==destRow)
        {
            maze.recolor(x,y,PATH);
            return true;
        }

        if (maze.getColor(x,y)!=NON_BACKGROUND)
        {
            return false;
        }

         /**
         * TO BE COMPLETED (recursion case)
         */
        boolean found = false;
        maze.recolor(x, y, TEMPORARY);
        maze.setLabel(x, y, ++cnt+"");

        if (findMazePathAux(x,y + 1,destCol, destRow))
        {
            maze.recolor(x, y, PATH);
            return true;
        }

        if (findMazePathAux(x + 1,y,destCol, destRow))
        {
            maze.recolor(x, y, PATH);
            return true;
        }

        if (findMazePathAux(x - 1,y,destCol, destRow))
        {
            maze.recolor(x, y, PATH);
            return true;
        }

        if (findMazePathAux(x,y - 1,destCol, destRow))
        {
            maze.recolor(x, y, PATH);
            return true;
        }
        return found;
    }
    public void resetTemp()
    {
        maze.recolor(TEMPORARY, BACKGROUND);
    }

    public void resetMaze()
    {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
        maze.clearLabels();
    }

    public void restoreMaze()
    {
        maze.recolor(PATH, NON_BACKGROUND);
        maze.recolor(TEMPORARY, NON_BACKGROUND);
        maze.clearLabels();
    }
}
