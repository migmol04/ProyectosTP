package tp1.logic.gameobjects;
import tp1.logic.*;
import tp1.logic.*;
import tp1.view.*;

public class UCMShip {


	private boolean canShoot;
	public static final int ARMOR = 10;
	public static final int DMG = 10;
	private Game game;
	private int life;
	private Position pos;
	private UCMShip player;
	private int points;
	
	public UCMShip(Game game) {
		this.life = ARMOR;
		this.game = game;
		this.pos = new Position(4, 7);
		this.points = 0;
		this.canShoot = false;
	}
	
	public boolean isAlive() {
		return life > 0;
	}
	
	public int getLife() {
		return life;
	}
	
	public void die() {

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
	
	public void isOut() {
		
	}
	
	public void performMovement() {
		
	}
	
	public String getSymbol() {
		return Messages.UCMSHIP_SYMBOL;
	}
	
	public String toString() {
		return "";
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
		
	}
	
	public void automaticMove() {
		
	}
	
	public void ComputerAction() {
		
	}
	
	public void enableLaser() {
		this.canShoot = true;
		
	}
	
	public boolean shootLaser() {
		return canShoot;
	}
	
	public void recieveAttack() {
		
	}
	
	public boolean move(Move move) {
	    int newX = pos.getX() + move.getX();
	    int newY = pos.getY() + move.getY();
	    boolean exito = false;

	    if (isValidPosition(newX, newY)) {
	        pos = new Position(newX, newY);
	       exito = true; // El movimiento se realizó con éxito
	    }

	   return exito; 
	}
	
	private boolean isValidPosition(int x, int y) {         //funcion agregada por nosotros
		 int gameWidth = Game.DIM_X;
		 int gameHeight = Game.DIM_Y;
		 return x >= 0 && x < gameWidth && y >= 0 && y < gameHeight;
	}
	
	

}