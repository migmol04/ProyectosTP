package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 * Class representing a regular alien
 */
public class RegularAlien {

	public int armor = 2;
    public Position pos;
    public int life;
    public Game game;
    public Move dir;
    public int cyclesToMove;
    public int speed;
    public AlienManager alienManager;
    private boolean onBorder = false;
    
    
    public RegularAlien(Game game, AlienManager manager, Position pos) {
        this.life = armor;
        this.pos = pos;
        this.game = game;
        this.dir = Move.LEFT;
        this.cyclesToMove = 4;
        this.speed = 1;
        this.alienManager = manager;
    }

    public boolean isAlive() {
        return getLife() > 0;
    }

    public int getLife() {
        return life;
    }

    public void die() {
        life = 0;
    }

    public boolean isOnPosition(Position position) {
    	return (this.pos.getX() == position.getX()) && (this.pos.getY() == position.getY());
    }

    public Position getPosition() {
        return pos;
    }

    public void receiveDamage() {
        life -= getDamage();
    }

    public boolean isOut() {
        return pos.getY() < 0 /*se podria quitar?*/ || pos.getX() < 0 || pos.getX() >= Game.DIM_X; 
    }

    public boolean isinFinalRow() {
        return pos.getY() >= Game.DIM_Y; 
    }

    private void performMovement(Move dir) {
    	
        pos = new Position(pos.getX() + dir.getX(), pos.getY() + dir.getY());
    }

    public String getSymbol() {
    	return Messages.REGULAR_ALIEN_SYMBOL + "[" + life + "]";
    }

    public String toString() {
    	return getDescription()+ ":" + getInfo();
    }

    public String getInfo() {
        return Messages.ALIEN_DESCRIPTION;
    }

    public String getDescription() {
        return Messages.REGULAR_ALIEN_DESCRIPTION ; 
    }

    public int getDamage() {
        return 1; 
    }

    public void computerAction() {
      
    }

    public void onDelete() {
    	die();
    }
    
    public void automaticMove() {

        
        cyclesToMove--;
        

        if (isInBorder() && !onBorder) {
        	descent();
        	onBorder = true;

            if (dir == Move.RIGHT) {
                dir = Move.LEFT;
            } else {
                dir = Move.RIGHT;
            }
        }
    
        if (cyclesToMove == 0) {
            performMovement(dir);
            onBorder=false;
            cyclesToMove = 4; 
        }
    }
    
    public void descent() {
        performMovement(Move.DOWN);
  
    }

    public boolean isInBorder() {
        return pos.getX() == 0 || pos.getX() >= Game.DIM_X - 1;
    }

    public boolean receiveAttack(UCMLaser laser) {
        if (pos.equals(laser.pos)) {
            return true;
        }
        return false; 
    }
}

