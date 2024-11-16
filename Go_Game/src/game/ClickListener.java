package game;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

public class ClickListener extends MouseAdapter {

    private Board panel; // able to alter the panel
    private Grid grid; // able to alter the grid
    private JLabel white; // make the label editable while detecting mouse clicks
    private JLabel black;
    private boolean justCaptured; // to check if a capture has just happened
    private int wCaptured; // # of white stones captured
    private int bCaptured; // # of black stones captured

    public ClickListener(Board panel, JLabel w, JLabel b) {
        this.panel = panel;
        grid = new Grid();
        black = b;
        white = w;
        justCaptured = false;
        wCaptured = 0;
        bCaptured = 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) { // detecting mouse clicks

        int x = e.getX(); // x-coord of mouseclick on Board
        int y = e.getY() - 4; // calibrating

        if (x < 585 && y < 585) { // making sure stones can't be placed outside the board

            Stone currentStone = grid.intoStone(x, y); // the  currentStone being altered
            justCaptured = false; // reset justCaptured to false each turn

            // most of the calculations are here
            if (grid.canPlace(currentStone)) {
                addStone(currentStone);

                ArrayList<Stone> diffNeighbours = grid.diffColorNeighbours(currentStone); // different color neighbors of the stone

                grid.updateLiberties(); // update liberties before calculating capture

                int countCaptured = 0; // counts captured for labels

                for (Stone i : diffNeighbours) { // for every one of these neighbors

                    if (grid.getPlacedStones().contains(i)) { // to see if the neighbour being check has been captured

                        Object captured = grid.checkCaptured(i); // returns an ArrayList of Stone or null

                        if (captured != null) { // if something is captured
                            justCaptured = true;
                            for (Stone c : (ArrayList<Stone>) captured) { // for every captured Stone
                                removeStone(c); // remove the stone
                                countCaptured++;  // add a capture

                            }

                        }
                    }

                }
                if (countCaptured == 0 && grid.checkCaptured(currentStone) != null) { // if a self-capture occurs
                    removeStone(currentStone); // remove the stone being placed
                    JOptionPane.showMessageDialog(panel, 
                                "Cannot self-capture" // inform player
                        );
                } else {
                    if (currentStone.getColor() == Board.PlayerColor.BLACK) { // the color captured is always different color than currentStone
                        wCaptured += countCaptured;
                        updateWhiteCaptured(wCaptured);
                    } else {
                        bCaptured += countCaptured;
                        updateBlackCaptured(bCaptured);
                    }

                    grid.updateLiberties(); // for good measure

                    grid.switchPlayer(); // switch to the other color
                }

            }
        }

    }

    /**
     * method that adds stone to both panel and grid
     * @param s 
     */
    public void addStone(Stone s) {
        panel.addStone(s);
        grid.addStone(s);
    }

    /**
     * method that removes stone from both panel and grid
     * @param s 
     */
    public void removeStone(Stone s) {
        grid.removeStone(s);
        panel.removeStone(s);

    }

    /**
     * removes all the stones from both board and grid
     */
    public void resetGame() {
        for (int i = panel.getVisibleStones().size() - 1; i >= 0; i--) { // iterate through the ArrayList
            removeStone(panel.getVisibleStones().get(i)); // using the ArrayList in panel to remove stones in both panel and grid
            grid.setTurn(Board.PlayerColor.BLACK); // reset turn to black
        }

        // reset captured stones to 0
        bCaptured = 0;
        wCaptured = 0;
        updateWhiteCaptured(0);
        updateBlackCaptured(0);

    }

    /**
     * undoes the previous move, returns false if the previous stone placed had
     * captured something
     *
     * @return
     */
    public boolean undoMove() {
        if (justCaptured == false) { 
            removeStone(panel.getVisibleStones().get(panel.getVisibleStones().size() - 1)); // remove last stone from list
            grid.switchPlayer(); // switch the turn so color changes
            return true;
        } else { // if a stone has just been captured, the stones that were captured won't return to the board
            // simple fix: don't allow undo after capturing
            return false;
        }
    }

    // methods to update the labels
    /**
     * updating white stones captured label
     * @param num 
     */
    public void updateWhiteCaptured(int num) {
        white.setText("white stones captured: " + num);
    }

    /**
     * update black stones captured label
     * @param num 
     */
    public void updateBlackCaptured(int num) {
        black.setText("black stones captured: " + num);
    }

    // getters
    /**
     * 
     * @return panel
     */
    public Board getPanel() {
        return panel;
    }

    /**
     * 
     * @return grid
     */
    public Grid getGrid() {
        return grid;
    }

    // setters
    
    /**
     * set panel
     * @param panel 
     */
    public void setPanel(Board panel) {
        this.panel = panel;
    }

    /**
     * set grid
     * @param grid 
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

}
