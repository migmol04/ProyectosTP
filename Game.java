package tp1.logic;
import java.util.Scanner;
import java.util.Random;
import tp1.control.*;

import tp1.logic.gameobjects.*;
import tp1.logic.lists.*;

// TODO implementarlo
public class Game {

	public static final int DIM_X = 9;
	public static final int DIM_Y = 8;
	private Level level;
	private long seed;
	private UCMShip ucmShip;
	private UCMLaser laser;
	private RegularAlien alien;
	private RegularAlien regularAlien;
	private Move dir;
	private AlienManager manager;
	private RegularAlienList list;
	private Position pos;
	private Controller c;
	
	
	private Game game;

	//TODO fill your code

	public Game(Level level, long seed) {
		this.level = level;
		this.seed = seed;
		game = this;
		ucmShip = new UCMShip(this);
		laser = new UCMLaser(dir, this);
		manager = new AlienManager(this, level);
		list = new RegularAlienList(5);
		alien = new RegularAlien(5, pos, 5, this, dir, 1, 100, manager);
		
}
	

	public String stateToString() {
		return "Hola";
	}


	public int getCycle() {
		//TODO fill your code
		return 0;
	}

	public int getRemainingAliens() {
		//TODO fill your code
		return 0;
	}

	public String positionToString(int col, int row) {
	    Position pos = new Position(col, row);
	    if (ucmShip.isOnPosition(pos)) {
	        return ucmShip.getSymbol();
	    } else if (laser.isOnPosition(pos) && ucmShip.shootLaser()) {
	        return laser.getSymbol();
	    } else if (alien.isOnPosition(pos)) {
	        return alien.getSymbol();
	    } else {
	        return "";
	    }
	}


	public boolean playerWin() {
		//TODO fill your code
		return false;
	}

	public boolean aliensWin() {
		//TODO fill your code
		return false;
	}

	public void enableLaser() {
		//TODO fill your code		
	}

	public Random getRandom() {
		//TODO fill your code
		return null;
	}

	public Level getLevel() {
		return this.level;
	}
    public UCMShip getUCMShip() {
    	return this.ucmShip;
    }
    
    public UCMLaser getLaser() {
    	return this.laser;
    }

}
