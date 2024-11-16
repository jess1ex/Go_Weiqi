package game;

import game.Board.PlayerColor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author gaowe
 */
public class Stone {

    private int row;
    private int col;
    private PlayerColor color; // every stone is either black or white
    private final int originalLiberties; // never changes
    private int currentLiberties;
    private Point p; // a coordinate on the board

    public Stone(int row, int col, PlayerColor color) {
        this.row = row;
        this.col = col;
        this.color = color;
        p = new Point((col * 30) + 15,(row * 30) + 15); // point is the upper left corner of the box that stone is in

        
        // liberties depend on location of stone on the board (corner = 2, side = 3, other = 4)
        if((row == 0 && col == 0) || (row == 18 && col ==0) || (row == 0 && col == 18) || (row == 18 && col == 18)){ // corners
            originalLiberties = 2;
        }else if(row == 0 || col == 0 || row == 18 || col == 18){ // sides
            originalLiberties = 3;
        }else{ // everywhere else
            originalLiberties = 4;
        }
        
        currentLiberties = originalLiberties;
        
    }
    
    
    
    public void draw(Graphics g){
        // checks the enum PlayerColor to determine which color stone to place (draw)
        if(color == PlayerColor.BLACK){
            g.setColor(Color.DARK_GRAY);
        }else{
            g.setColor(Color.WHITE);
        }
        g.fillOval((int)p.getX(), (int)p.getY(), 30, 30); // the stone is drawn on the attribute <<Point p>>'s x and y
        
    }
    
    
    
    // setters

    
    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public void setCurrentLiberties(int currentLiberties) {
        this.currentLiberties = currentLiberties;
    }

    

    public void setP(Point p) {
        this.p = p;
    }
    
    
    
    // getters

    public int getOriginalLiberties() {
        return originalLiberties;
    }

    public int getCurrentLiberties() {
        return currentLiberties;
    }
    
    public Point getP() {
        return p;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public PlayerColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Stone{" + "row=" + row + ", col=" + col + ", color=" + color + ", originalLiberties=" + originalLiberties + ", currentLiberties=" + currentLiberties + ", p=" + p + '}';
    }

    

    
    
    
    
    

    
}