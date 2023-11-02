package tp1.logic.gameobjects;
import tp1.logic.*;
import tp1.logic.*;
import tp1.view.*;

public class UCMShip {


	
	public static final int ARMOR = 10;
	public static final int DMG = 10;
	private Position pos;
	private int life;
	private Game game;
	private boolean canShoot;
	
	
	public UCMShip(Game game) {
		this.life = ARMOR;
		this.game = game;
		this.pos = new Position(4, 7);
		this.canShoot = false;
	}
	
	public boolean isAlive() {
		return life > 0;
	}
	
	public int getLife() {
		return life;
	}
	
	public void die() {
		life = 0;

	}
	
	public boolean isOnPosition(Position p) {
		
		return (this.pos.getX() == p.getX()) && (this.pos.getY() == p.getY());
		
		
	}
	
	public Position getPosition() {
		 return this.pos;
	}
	
	public void recieveDamage() {
		this.life -= DMG;
	}
	
	public boolean isOut() {
	    int gameWidth = Game.DIM_X;
	    int gameHeight = Game.DIM_Y;
	    int shipX = pos.getX();
	    int shipY = pos.getY();

	    return shipX < 0 || shipX >= gameWidth || shipY < 0 || shipY >= gameHeight;
	}
	
	public void performMovement() {
		
	}
	
	public String getSymbol() {
		return Messages.UCMSHIP_SYMBOL;
	}
	
	public String toString() {
		return getSymbol();
	}
	
	public String stateToString() {
	return "";
	}
	
	public String getInfo() {
	   
	    return Messages.UCMSHIP_DESCRIPTION;
	}
	
	
	public String getDescription() {
		return Messages.UCM_DESCRIPTION;
	}
	
	public int getDamage() {
		return DMG;
	}
	
	public void onDelete() {
      die();
 	}
	
	public void automaticMove() {
		
	}
	
	public void ComputerAction() {
		
	}
	
	 
	
	public boolean move(Move move) {
	    int newX = pos.getX() + move.getX();
	    int newY = pos.getY() + move.getY();
	    boolean exito = false;

	    if (!isOut()) {
	        pos = new Position(newX, newY);
	       exito = true; 
	    }

	   return exito; 
	}
	
	public void enableLaser() {
        this.canShoot = !this.canShoot;
    }
	
    
    public boolean shootLaser() {
        boolean shooted = false;
        
        if (!canShoot) {
            enableLaser();
            shooted = true;
        }
        
        return shooted;
    }
	
	public void recieveAttack() {
		life -= DMG;
		
	}
}