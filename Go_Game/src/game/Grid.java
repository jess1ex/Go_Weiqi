
package game;

import game.Board.PlayerColor;
import java.util.ArrayList;
import java.util.Arrays;

public class Grid {

    private Stone[][] stones; // a grid-like representation of the board
    private ArrayList<Stone> placedStones; 
    private PlayerColor turn; // to track colour of current player 

    public Grid() {
        //setMaximumSize(getPreferredSize());

        stones = new Stone[19][19]; // 19x19 board, 19x19 grid
        turn = PlayerColor.BLACK;  // black always goes first
        placedStones = new ArrayList();

    }

    /**
     * turn the coordinates given (x, y) into a stone with the color being
     * determined by 'turn' attribute
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return
     */
    public Stone intoStone(int x, int y) {

        int col = this.xCoordToCol(x);
        int row = this.yCoordToRow(y);

        int centeredX = this.colToXCoord(col);
        int centeredY = this.rowToYCoord(row);

        Stone s = new Stone(row, col, turn);

        return s;

        // check liberties oh god i dont wanna think ab this
        // check neighbours
    }

    /**
     * method to determine if the spot clicked can place a stone
     *
     * @param s
     * @return
     */
    public boolean canPlace(Stone s) {
        boolean canPlace = true;
        for (Stone i : placedStones) {

            if (i.getP().equals(s.getP())) { // method .equals() from the Point class ('==' does not work lol)
                canPlace = false;
            }
        }
        return canPlace;

    }

    /**
     * switch player color after each stone is placed
     */
    public void switchPlayer() {
        if (turn == PlayerColor.BLACK) {
            turn = PlayerColor.WHITE;
        } else {
            turn = PlayerColor.BLACK;
        }
    }

    /**
     * returns an ArrayList of stones neighboring the placed stone
     *
     * @param s
     * @return 
     */
    public ArrayList<Stone> getNeighbours(Stone s) {
        int row = s.getRow();
        int col = s.getCol();
        ArrayList<Stone> temp = new ArrayList(); // ArrayList with nulls
        ArrayList<Stone> neighbours = new ArrayList();
        
        if (row > 0) { // if the row is not the first row
            temp.add(stones[row - 1][col]); // UP
        }
        if (row < 18) { // if the row is not the last one
            temp.add(stones[row + 1][col]); // DOWN
        }
        if (col > 0) { // if the column is not the leftmost
            temp.add(stones[row][col - 1]); // LEFT
        }
        if (col < 18) { // column not the rightmost
            temp.add(stones[row][col + 1]); // RIGHT
        }
        
        // removing nulls
        for (Object n : temp) { 
            if (n != null) {
                neighbours.add((Stone) n);
            }
        }

        return neighbours;
    }

    /**
     * returns the neighbors with different color than the stone
     * @param s
     * @return 
     */
    public ArrayList<Stone> diffColorNeighbours(Stone s) {
        ArrayList<Stone> diff = new ArrayList();

        for (Stone d : getNeighbours(s)) { // for every neighbours
            if (d.getColor() != s.getColor()) { // check if it is the same color as the stone
                diff.add(d); // if it is the same color, add it to this ArrayList
            }
        }

        return diff;
    }

    /**
     * returns neighbors that are the same color
     *
     * @param s
     * @return
     */
    public ArrayList<Stone> sameColorNeighbours(Stone s) {

        ArrayList<Stone> sameColorNeighbours = new ArrayList();

        for (Stone d : getNeighbours(s)) { // for every neighbours
            if (d.getColor() == s.getColor()) { // check if it is the same color as the stone
                sameColorNeighbours.add(d); // if it is the same color, add it to this ArrayList
            }
        }

        return sameColorNeighbours;

    }

    /**
     * adds a new stone to the 2d array, and to the ArrayList of placedStones
     *
     * @param s
     */
    public void addStone(Stone s) {
        stones[s.getRow()][s.getCol()] = s; // add the stone in this 2d array
        placedStones.add(s); // add the stone in the ArrayList

    }

    /**
     * functions the same way as addStone, except removes stone instead
     * @param s 
     */
    public void removeStone(Stone s) {
        stones[s.getRow()][s.getCol()] = null; // reset to null
        placedStones.remove(s);
    }

    /**
     * checks if the stone is being captured, checks connected stones of the same color if they are also being captured
     *
     * @param s the stone being checked
     * @return the ArrayList of stone captured, returns null if the stone is not captured
     */
    public ArrayList<Stone> checkCaptured(Stone s) {

        int totalLibs = s.getCurrentLiberties(); // total liberties of the chain of stones
        ArrayList<Stone> connected = new ArrayList(); // the chain being created
        connected.add(s); // the chain contains the original stone

        // ListIterator<Stone> list = connected.listIterator();
        Stone temp;

        int index = -1;

        do { // do-while loop used because index cannot be -1
            

            index++; // index increases each time, to cycle through the arraylist
            temp = connected.get(index); // a temporary stone that is being checked

            totalLibs += temp.getCurrentLiberties(); // adds the liberties of the stone being checked to the total

            for (Stone l : sameColorNeighbours(temp)) { // for every neighbour of the stone with the same color
                if (connected.contains(l) == false) { // if the arraylist doesn't already contain the stone
                    connected.add(l); // add the stone to the list (otherwise it'd iterate infintely)
                    
                    
                }
                totalLibs += l.getCurrentLiberties(); // for good measure
            }

            // if we have not reached the ArrayList's last stone
        } while (temp != connected.get(connected.size()-1));
        

        if (totalLibs == 0) { // if a group is captured, return that group

            return connected;
        }

        return null; // otherwise return null

    }

    /**
     * updates the currentLiberties of every stone placed
     */
    public void updateLiberties() { // this method takes a lot of time and should be changed
        int num;

        for (Stone s : placedStones) {
            num = s.getOriginalLiberties(); // their liberties left needs to be calculated from their original
            for (Stone i : getNeighbours(s)) { // for every neighbour it has
                num--; // one less liberty
            }
            s.setCurrentLiberties(num); // set new liberties
        }
    }

    // conversion methods between board coordinates and grid column/rows
    // y becomes row numbers, x becomes column numbers
    /**
     * turns x coordinate into column number
     *
     * @param x
     * @return
     */
    public int xCoordToCol(int x) {
        return (x - 15) / 30;
    }

    /**
     * turns y coordinate into row number
     * @param y
     * @return 
     */
    public int yCoordToRow(int y) {
        return (y - 15) / 30;
    }

    /**
     * turns the row number of the grid into a y-coordinate on the board
     *
     * @param row the row number (0 starts at the top)
     * @return the Y-coordinate of the corner of stone to be placed
     */
    public int rowToYCoord(int row) {
        int YCoord = row * 30 + 15;
        return YCoord;
    }

    /**
     * turns the column number of the grid into a y-coordinate on the board
     *
     * @param col the column number (0 starts at the right)
     * @return the X-coordinate of the corner of stone to be placed
     */
    public int colToXCoord(int col) {
        int XCoord = col * 30 + 15;
        return XCoord;
    }

    
    //setters
    
    
    /**
     * set the Turn attribute
     * @param turn 
     */
    public void setTurn(PlayerColor turn) {
        this.turn = turn;
    }
    
    
    // getters

    /**
     * 
     * @return stones 2d array
     */
    public Stone[][] getStones() {
        return stones;
    }

    /**
     * 
     * @return placedStones ArrayList
     */
    public ArrayList<Stone> getPlacedStones() {
        return placedStones;
    }
    
    

    /**
     * 
     * @return the 19x19 grid as Stones
     */
    @Override
    public String toString() {
        return "Grid{" + "stones=" + Arrays.toString(stones) + '}';
    }

}
