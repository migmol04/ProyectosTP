package tp1.logic;

/**
 * Immutable class to encapsulate and manipulate positions in the game board
 */
public class Position {
    private final int col;
    private final int row;

    public Position(int col, int row) {
        this.col = col;
        this.row = row;
    }
    
    public int getX() {
        return col;
    }

    public int getY() {
        return row;
    }
    
}
   
   
    
    
    
