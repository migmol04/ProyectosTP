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
		return this.pos.equals(p);
		
	}
	
	public Position getPosition() {
		 return this.pos;
	}
	
	public void recieveDamage() {
		
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
		return "";	
	}
	
	public String getDescription() {
		return "";
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
		
	}
	
	public boolean shootLaser() {
		return true;
	}
	
	public void recieveAttack() {
		
	}
}