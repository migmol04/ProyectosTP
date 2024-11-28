package tp1.logic;

import java.util.Objects;

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
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Position other = (Position) obj;
        return col == other.col && row == other.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.col, this.row);
    }
}
    