package game;

import java.awt.*;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.*;

public class Board extends Panel {

    public enum PlayerColor { // there are two types of stones, black & white
        BLACK, WHITE;
    }

    private ArrayList<Stone> visibleStones; // stones visible on the board

    public Board() {

        visibleStones = new ArrayList();

        setBackground(new Color(222, 180, 109)); // board colour

        // setLayout();
        setPreferredSize(new Dimension(600, 600));
        //setMaximumSize(getPreferredSize());

    }

    @Override
    public void paint(Graphics g) {

        int rows = 18;

        int cols = 18;
        int width = 540;
        int height = 540;

        int rowHt = height / (rows);

        // draw the rows
        for (int i = 1; i <= rows + 1; i++) {

            g.drawLine(30, i * rowHt, width + 30, i * rowHt);

        }

        int rowWid = width / (cols);
        
        // draw the columns
        for (int i = 1; i <= cols + 1; i++) {

            g.drawLine(i * rowWid, 30, i * rowWid, height + 30);

        }

        // this could probably be done in an easier way, but a 2d array of important coordinates
        int[][] important = {{117, 117}, {117, 297}, {117, 477}, {297, 117}, {297, 297}, {297, 477}, {477, 117}, {477, 297}, {477, 477}};
        
        // draws the important points

        for (int[] i : important) {
            g.fillOval(i[0], i[1], 6, 6); // thanks Ms. Hideg
        }

        // draw all the visible stones, draw method below
        for (Stone s : visibleStones) {
            s.draw(g);
        }

        // highlighting last move
        if (visibleStones.isEmpty() == false) { // if there is a stone on the board
            Stone lastMove = visibleStones.get(visibleStones.size() - 1); // last value in the visible stones

            g.setColor(Color.CYAN);
            g.drawOval((int) lastMove.getP().getX(), (int) lastMove.getP().getY(), 30, 30);

        }

    }

    /**
     * use this method to add stones to the board
     *
     * @param s
     */
    public void addStone(Stone s) {
        visibleStones.add(s);
        this.repaint();
    }

    /**
     * use this method to capture stones on the board
     *
     * @param s
     */
    public void removeStone(Stone s) {
        visibleStones.remove(s);
        this.repaint();
        // could add a method call here to add to the count for captured stones
    }

    // getters
    /**
     * 
     * @return ArrayList visibleStones
     */
    public java.util.List<Stone> getVisibleStones() {
        return visibleStones;
    }

}
